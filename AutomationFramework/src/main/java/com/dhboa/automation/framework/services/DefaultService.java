package com.dhboa.automation.framework.services;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.MethodParams;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.StepDetails;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestCaseResults;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestStepResults;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.TestSuiteResults;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.entities.Variable;

@Service
public class DefaultService extends AutowiredUtilObjects {

	private static WebDriver driver = null;
	Logger logger = LoggerFactory.getLogger(getClass());
	
	String currentClass = getClass().getCanonicalName();
	
	private static final String IN_PROGRESS_STATUS = "INPROGRESS";
	private static final String SUCCESS_STATUS = "SUCCESS";
	private static final String FAILED_STATUS = "FAILED";
	Class<?> commonsClass = null;

	/**
	 * 
	 * @throws ClassNotFoundException
	 */
	@Autowired
	public void onAutowiredDefault() throws ClassNotFoundException {
		commonsClass = Class.forName("com.dhboa.automation.framework.components.CommonActions");
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void runTestSuite(String testSuiteId, String projectCode, String userName, String browser) throws Exception {
		Project prj = projRep.findOne(projectCode);
		User user = userRep.findOne(userName);
		TestSuite tSuite = getSuiteByReference(tSuiteRep.findOne(testSuiteId));
		runTestSuiteById(tSuite, prj, user, browser);
	}

	/**
	 * 
	 * 
	 * @param testSuiteId
	 * @param testCaseId
	 * @param projectCode
	 * @param userName
	 * @throws Exception
	 */
	public void runTestCase(String testSuiteId, String testCaseId, String projectCode, String userName, String browser)
			throws Exception {
		Project prj = projRep.findOne(projectCode);
		User user = userRep.findOne(userName);
		TestSuite tSuite = getSuiteByReference(tSuiteRep.findOne(testSuiteId));
		TestCase tCase = getCaseByReference(tcRep.findOne(testCaseId));
		runTestCaseById(tSuite, tCase.getOrder(), tCase, prj, user, null, browser);
	}

	/**
	 * If there is a circular reference then this will give you the details of
	 * the final step id which has step details and not step reference.
	 * 
	 * @param testStep
	 * @return
	 */
	private TestStep getStepByReference(TestStep testStep) {
		String methodName = "getStepByReference";
		try {
			if (testStep == null) {
				alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
						"Application cannot be proceeded as the test case is empty ", null);
				throw new Exception("Cannot proceed with null or empty test step");
			}
			while (true) {
				if (null == testStep.getTestStepRef() || "".equals(testStep.getTestStepRef())) {
					break;
				} else {
					/**
					 * This means that the record still holds a link to another
					 * test step reference. Hence it has to iterate yet another
					 * time to find if this is the last chain of the test step
					 */
					testStep = tsRep.findOne(testStep.getTestStepRef());
				}
			}
		} catch (Exception e) {
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Getting the step by reference failed due to the reason " + e.getMessage(), e);
			testStep = null;
		} finally {

		}
		return testStep;
	}

	/**
	 * 
	 * @param testCase
	 * @return
	 */
	private TestCase getCaseByReference(TestCase testCase) {
		String methodName = "getCaseByReference";
		try {
			if (testCase == null) {
				alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
						"Application cannot be proceeded as the test case is empty ", null);
				throw new Exception("Cannot proceed with null or empty test case");
			}
			while (true) {
				// Check if it is referring to any other step
				if (null == testCase.getTestCaseReference() || "".equals(testCase.getTestCaseReference())) {
					break;
				} else {
					/**
					 * This means that the record still holds a link to another
					 * test case reference. Hence it has to iterate yet another
					 * time to find if this is the last chain of the test step
					 */
					testCase = tcRep.findOne(testCase.getTestCaseReference());
				}
			}
		} catch (Exception e) {
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Getting the case by reference failed due to the reason " + e.getMessage(), e);
			testCase = null;
		}
		return testCase;
	}

	/**
	 * 
	 * @param testSuite
	 * @return
	 */
	private TestSuite getSuiteByReference(TestSuite testSuite) {
		String methodName = "getSuiteByReference";
		try {
			if (testSuite == null) {
				alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
						"Cannot proceed for empty test suite ", null);
				throw new Exception("Cannot proceed with null or empty test suite");
			}
			while (true) {
				if (null == testSuite.getTestSuiteReference() || "".equals(testSuite.getTestSuiteReference())) {
					break;
				} else {
					/**
					 * This means that the record still holds a link to another
					 * test suite reference. Hence it has to iterate yet another
					 * time to find if this is the last chain of the test step
					 */
					testSuite = tSuiteRep.findOne(testSuite.getTestSuiteReference());
				}
			}
		} catch (Exception e) {
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Getting the suite by reference failed due to the reason " + e.getMessage(), e);
			testSuite = null;
		}
		return testSuite;
	}

	/**
	 * 
	 * @param testSuite
	 * @param proj
	 * @param user
	 * @throws Exception
	 */
	public void runTestSuiteById(TestSuite testSuite, Project proj, User user, String browser) throws Exception {
		TestSuiteResults testSuiteResultRef = null;
		List<TestCase> tCases = null;
		String methodName = "runTestSuiteById";
		try {
			/**
			 * Validation block. Do not continue further if these validations
			 * fail
			 */
			if (null == testSuite || "".equals(testSuite)) {
				alServ.log("INFO", logger, currentClass, methodName, "",
						" DefaultService : runTestSuiteById : TestSuite cannot be proceeded because it is null or empty");
				return;
			}

			/**
			 * Test suite can be executed
			 */
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestSuiteById : Fetching records for the testSuite : " + testSuite.getId());
			testSuiteResultRef = utilServ.logTestSuiteStatus(logger, IN_PROGRESS_STATUS, "Started the test suite ",
					testSuite, proj, user, null);
			/**
			 * Get all the test cases based on the criteria mentioned below
			 */
			tCases = tcRep.findByTestSuiteAndProjectAndUserAndIsActiveOrderByOrderAsc(testSuite, proj, user, true);
			if (null != tCases && !tCases.isEmpty()) {
				for (TestCase tCase : tCases) {
					// Run this logic for each and every test case.
					tCase = getCaseByReference(tCase);
					// Run this test case using the id fetched from above.
					runTestCaseById(testSuite, tCase.getOrder(), tCase, proj, user, testSuiteResultRef, browser);
				}
			} else {
				// Log properly
				alServ.log("INFO", logger, currentClass, methodName, "",
						" DefaultService : runTestSuiteById : No test cases found for the testSuite : "
								+ testSuite.getId());
				utilServ.logTestSuiteStatus(logger, SUCCESS_STATUS,
						"Execution of the test suite completed but no records found for the testSuite ", testSuite,
						proj, user, testSuiteResultRef);
			}
		} catch (Exception e) {
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Execution of test suite failed due to the reasons " + e.getMessage(), e);
			utilServ.logTestSuiteStatus(logger, FAILED_STATUS,
					"Execution of the test suite failed. Please check the logs or TestSuite log for details.",
					testSuite, proj, user, testSuiteResultRef);
			throw e;
		} finally {

		}
	}

	/**
	 * 
	 * 
	 * @param testSuite
	 * @param order
	 * @param testCaseRef
	 * @param project
	 * @param user
	 * @param testSuiteResultRef
	 * @throws Exception
	 */
	public void runTestCaseById(TestSuite testSuite, int order, TestCase testCaseRef, Project project, User user,
			TestSuiteResults testSuiteResultRef, String browser) throws Exception {
		List<TestStep> tSteps = null;
		List<StepDetails> stepDetails = null;
		List<Object[]> testCase = null;
		TestStep testStepRef = null;
		String methodName = "runTestCaseById";
		TestCaseResults testCaseResultRef = null;
		try {
			/**
			 * Validation block. Do not continue further if these validations.
			 * fail
			 */
			if (null == testCaseRef || "".equals(testCaseRef)) {
				alServ.log("INFO", logger, currentClass, methodName, "",
						" DefaultService : runTestCaseById : TestCase cannot be proceeded because it is null or empty");
				throw new Exception("Null or empty test case");
			}
			/**
			 * A test case can be executed
			 */
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestCaseById : Fetching records for the testCase : " + testCaseRef.getId());
			testCaseResultRef = utilServ.logTestCaseStatus(logger, IN_PROGRESS_STATUS, "Started the test case ",
					testSuiteResultRef, testCaseRef, order, null);
			tSteps = tsRep.findByTestCaseAndProjectAndUserAndIsActiveOrderByOrderAsc(testCaseRef, project, user, true);
			if (!tSteps.isEmpty()) {
				// Initialize the test case list
				testCase = new ArrayList<Object[]>();
			}
			for (TestStep ts : tSteps) {
				/**
				 * Getting the actual reference of the test step. This is
				 * because this test step may be final or it holds link to
				 * another test step which might hold link to other. This chain
				 * will be eliminated and the actual test step would be found
				 * out by using the below function.
				 */

				testStepRef = getStepByReference(ts);

				/**
				 * Fetching the step details using the reference of the test
				 * step which is found above.
				 */
				stepDetails = tsDetailsRep.findByTestStepAndIsActiveOrderByOrderAsc(testStepRef, true);
				if (!stepDetails.isEmpty()) {
					Object[] steps = new Object[stepDetails.size()];
					for (int i = 0; i < stepDetails.size(); i++) {
						steps[i] = stepDetails.get(i).getFieldValue();
					}
					testCase.add(steps);
					steps = null;
				}
			}
			if (!testCase.isEmpty()) {
				// Getting the web driver
				getWebDriver(browser);
				// Run the test case which is formed. This is a combination of
				// various test steps.
				runTestCase(tSteps, testCase, testSuite, testCaseResultRef,user);
				utilServ.logTestCaseStatus(logger, SUCCESS_STATUS, "Completed the test case", testSuiteResultRef,
						testCaseRef, order, testCaseResultRef);
			} else {
				utilServ.logTestCaseStatus(logger, SUCCESS_STATUS,
						"Completed the test case but test case does not have any test steps for it", testSuiteResultRef,
						testCaseRef, order, testCaseResultRef);
			}
		} catch (Exception e) {
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Execution of test case failed due to the reasons " + e.getMessage(), e);
			utilServ.logTestCaseStatus(logger, FAILED_STATUS,
					"Execution of the test case failed. Please check the logs or TestStep log for details.",
					testSuiteResultRef, testCaseRef, order, testCaseResultRef);
			throw e;
		} finally {
			tSteps = null;
			stepDetails = null;
			testCase = null;
			testStepRef = null;
		}
	}

	/**
	 * 
	 * 
	 */
	public void getWebDriver(String browser) {
		alServ.log("INFO", logger, currentClass, "getWebDriver", "",
				" DefaultService : getWebDriver : Getting the web driver");
		driver = comm.getWebDriver(browser);
		comm.maximizeWindow(driver);
		alServ.log("INFO", logger, currentClass, "getWebDriver", "",
				" DefaultService : getWebDriver : Completed with getting web driver");
	}

	/**
	 * 
	 * @param tSteps
	 * @param testCase
	 * @param testSuiteId
	 * @param testCaseResultRef
	 * @throws Exception
	 */
	public void runTestCase(List<TestStep> tSteps, List<Object[]> testCase, TestSuite testSuiteId,
			TestCaseResults testCaseResultRef,User user) throws Exception {
		String methodName = "runTestCase";
		TestStep testStepRef = null;
		Object[] testStep = null;
		int order = 0;
		TestStepResults tStepResultRef = null;
		Variable var = null;
		try {
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestCase : Inside running a test Case");
			for (int i = 0; i < testCase.size(); i++) {
				testStep = testCase.get(i);
				testStepRef = tSteps.get(i);
				order = tSteps.get(i).getOrder();
				
				if(null!=testStepRef && null!=testStepRef.getAdditional1() && null!=testStepRef.getAdditional2())
				{//check if variable exists		
					TestCase testCaseRef=null;
					 if("case".equalsIgnoreCase(testStepRef.getAdditional1()))
					{

						testCaseRef=testStepRef.getTestCase();
					}
					Variable variable =varRep.findByVariableNameAndProjectAndUserAndTestSuiteAndTestCase(testStepRef.getAdditional2(), testSuiteId.getProject(), user, testSuiteId, testCaseRef);
					if(variable==null)
						 var =createVariable(testStepRef, testSuiteId, user);
					else
						var=variable;
				}
				
				tStepResultRef = utilServ.logTestStepStatus(logger, IN_PROGRESS_STATUS, "Started the test case ",
						testCaseResultRef, testStepRef, order, null);
				runTestStep(commonsClass, testStep, var, testStepRef, testSuiteId, user);
				utilServ.logTestStepStatus(logger, SUCCESS_STATUS, "Completed the test case ", testCaseResultRef,
						testStepRef, order, tStepResultRef);
			}
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestCase : Completion of running all the steps for the provided test case");
		} catch (NoSuchMethodException e) {
			utilServ.logTestStepStatus(logger, FAILED_STATUS, "Failure while executing the test case : No Such Method",
					testCaseResultRef, testStepRef, order, tStepResultRef);
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Possible reason might be configured method might not be applicable for the parameters defined."
							+ e.getMessage(),
					e);
			throw e;
		} catch (Exception e) {
			utilServ.logTestStepStatus(logger, FAILED_STATUS, "Failure while executing the test step ",
					testCaseResultRef, testStepRef, order, tStepResultRef);
			alServ.LogError(logger, e);
			alServ.logError(logger, this.getClass().toString(), methodName, FAILED_STATUS,
					"Execution of test step failed due to the reasons " + e.getMessage(), e);
			throw e;
		} finally {

		}
	}

	/**
	 * 
	 * @param parameters
	 * @throws Exception
	 */
	private void runTestStep(Class<?> runTimeClass, Object[] parameters, Variable var, TestStep testStep, TestSuite testSuite, User user) throws Exception {
		int totalParams = 0;
		String[] paramValues = null;
		String methodName = "runTestStep";
		alServ.log("INFO", logger, currentClass, methodName, "",
				" DefaultService : runTestStep : Inside running a test step");
		if (parameters != null && parameters.length > 0) {
			totalParams = parameters.length - 1;
		}
		if (totalParams == 0) {
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestStep : Running the test step without any parameters for the method "
							+ parameters[0]);
			runTestStep(runTimeClass, String.valueOf(parameters[0]), var, testStep, testSuite, user);
		} else {
			alServ.log("INFO", logger, currentClass, methodName, "",
					" DefaultService : runTestStep : Running the test step with " + totalParams
							+ " parameters for the method " + parameters[0]);
			paramValues = new String[totalParams];
			for (int i = 0; i < parameters.length - 1; i++) {
				paramValues[i] = String.valueOf(parameters[i + 1]);
			}
			runTestStep(runTimeClass, String.valueOf(parameters[0]),var, testStep, testSuite, user, paramValues);
		}

		alServ.log("INFO", logger, currentClass, methodName, "",
				" DefaultService : runTestStep : Successfully executed this step");
	}

	/**
	 * 
	 * @param runTimeClass
	 * @param methodName
	 * @param parameters
	 * @throws Exception
	 */
	private void runTestStep(Class<?> runTimeClass, String methodName, Variable var, TestStep testStep, TestSuite testSuite, User user, String... parameters) throws Exception {
		Method methodToBeCalled = null;
		Class<?> params[] = null;
		int totalParams = 1;
		Object[] paramValues = null;
		String variableValue=null;
		String returnValue=null;
		if (parameters != null && parameters.length > 0) {
			totalParams = totalParams + parameters.length;
		}
		params = new Class<?>[totalParams];
		paramValues = new Object[totalParams];
		params[0] = WebDriver.class;
		paramValues[0] = driver;
		for (int i = 0; i < parameters.length; i++) {
			if(parameters[i].contains("{{") && parameters[i].contains("}}"))
			{//variable exists
				variableValue =replaceParamValue(testStep, testSuite, user, parameters[i]);
				parameters[i]=variableValue;
			}
			paramValues[i + 1] = parameters[i];
			params[i + 1] = String.class;
			
		}
		methodToBeCalled = runTimeClass.getDeclaredMethod(methodName, params);
		Object newObj = runTimeClass.newInstance();
			if(methodToBeCalled.getReturnType()!=void.class)
			{	
			returnValue=(String) methodToBeCalled.invoke(newObj, paramValues);
			updateVariable(var,returnValue);	
		}else{
		methodToBeCalled.invoke(newObj, paramValues);
		}
	}
	
	/**
	 * @param variable
	 * @param variableValue
	 */
	public void updateVariable(Variable variable,String variableValue){
		String methodName = "updateVariable";
		variable.setVariableValue(variableValue);
		varRep.save(variable);
		alServ.log("INFO", logger, currentClass, methodName, "", "DefualtService : updateVariable : Variablevalue for variable "+ variable.getVariableName()+ " updated successfully.");
	}
	
	/**
	 * @param testStep
	 * @param testSuite
	 * @param user
	 * @return
	 */
	
	public Variable createVariable(TestStep testStep, TestSuite testSuite, User user){
		String methodName="createVariable";
		alServ.log("INFO", logger, currentClass, methodName, "",
				" DefaultService : createVariable : Creating a new variable object");
		Variable var = new Variable();
		String variableName= testStep.getAdditional2();
		var.setId(UUID.randomUUID().toString());
		var.setVariableName(variableName);
		var.setProject(testStep.getProject());
		var.setUser(user);
		if("suite".equalsIgnoreCase(testStep.getAdditional1()))
			var.setTestSuite(testSuite);
		if("case".equalsIgnoreCase(testStep.getAdditional1()))
		{
			var.setTestSuite(testSuite);
			var.setTestCase(testStep.getTestCase());
		}
		
		varRep.save(var);	
		alServ.log("INFO", logger, currentClass, methodName, "",
				" DefaultService : createVariable : Variable object persisted successfully");
		return var;
	}
	
	/**
	 * @param testStep
	 * @param testSuite
	 * @param user
	 * @param parameter
	 * @return
	 */
	public String replaceParamValue(TestStep testStep, TestSuite testSuite, User user, String parameter){
		TestCase testCaseRef=null;
		Variable variable =null;
		String variableValue=null;
		String methodName="replaceParamValue";
		parameter = parameter.replace("{{","");
		parameter = parameter.replace("}}","");
		
		if(parameter.contains("@suite"))
			parameter= parameter.replace("@suite","");
		else
			testCaseRef= testStep.getTestCase();
	alServ.log("INFO", logger, currentClass, methodName, "", "DefaultServie : replaceParamValue : Trying to replace parameter value from database");		
		 variable = varRep.findByVariableNameAndProjectAndUserAndTestSuiteAndTestCase(parameter, testSuite.getProject(), user, testSuite, testCaseRef);
		 variableValue=variable.getVariableValue();
		 return variableValue;
	}

	/**Gets Methods from {@link com.dhboa.automation.framework.components.CommonActions}
	 * and persists to Method and MethodParam repositories.
	 * @throws SecurityException
	 * @throws ClassNotFoundException
	 */
	public void getMethods() throws SecurityException, ClassNotFoundException{
		Method[] methods = Class.forName("com.dhboa.automation.framework.components.CommonActions").getDeclaredMethods();
		Project project = new Project();
		project.setProjectCode("Test");
		String descpString = null;
		
		try
		{
		for(Method method: methods)
		{
			com.dhboa.automation.framework.entities.Method methodObj = new com.dhboa.automation.framework.entities.Method();
			methodObj.setId(UUID.randomUUID().toString());
			methodObj.setMethodName(method.getName());
			methodObj.setActive(true);
			methodObj.setMethodDescription("no parameters");
			methodObj.setProject(project);
			if(method.getParameters()!=null)
			{
			Parameter[] params = method.getParameters();
			List<String> paramNames = new ArrayList<>();
			List<MethodParams> paramList = new ArrayList<>();
			for(Parameter param: params){
				MethodParams paramObj = new MethodParams();
				paramObj.setId(UUID.randomUUID().toString());
				paramObj.setActive(true);
				paramObj.setParamDescription(param.getType().getSimpleName());
				paramObj.setParamName(param.getName());
				paramObj.setMethod(methodObj);

				paramNames.add(param.getName());
				descpString = String.join(",", paramNames);
				methodObj.setMethodDescription(descpString);
				paramList.add(paramObj);
			}
			mRep.save(methodObj);
			mpRep.save(paramList);
			}
		}
	}catch(Exception e)
		{
		e.printStackTrace();
		}
	}
	
	public void addBatch(){
		
	}
	
}
