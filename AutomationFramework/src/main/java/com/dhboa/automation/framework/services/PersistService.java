package com.dhboa.automation.framework.services;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.StepDetails;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;


/**
 * @author Vyshnavi Mudumby
 *
 */
@Service
public class PersistService extends AutowiredUtilObjects{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	String className = getClass().getCanonicalName();
	
	private final String IN_PROGRESS_STATUS = "INPROGRESS";
	private final String SUCCESS_STATUS = "SUCCESS";
	private final String FAILED_STATUS = "FAILED";
	
	
	/**
	 * @param object
	 * @param testSuiteId
	 * @param projectCode
	 * @param testCaseId
	 */
	public void saveTestObjects(TestSuiteObj object, String testSuiteId, String projectCode, String testCaseId){
		
		String methodName = "saveTestObjects";
		List<TestSuite> testSuites=null;
		User user = commonUtil.getLoggedInUser();;
		Project project = projRep.findOne(projectCode);
		
		alServ.log("INFO", logger, className, methodName, IN_PROGRESS_STATUS,
				"Inside saveTestObjects.. Attemping to save Test Objects");	
		if(null!=object && null!=object.getTestSuites()){
		
			 testSuites = object.getTestSuites();
			 saveTestSuites(testSuites,testSuiteId, project, user);
		
		}
		if(null!=object && null!=object.getTestCases())
		{
			 List<TestCase> testCases = object.getTestCases();
			 TestSuite testSuite = tSuiteRep.findOne(testSuiteId);
			saveTestCases(testSuite, testCases, project, user);		
	}
		if(null!=object && null!=object.getTestSteps())
		{
			TestCase testCase = tcRep.findOne(testCaseId);
			List<TestStep> testSteps = object.getTestSteps();
			saveTestSteps(testCase, testSteps, project, user);
		}
	}
	
	/**
	 * @param testSuites
	 * @param testSuiteId
	 * @param project
	 * @param user
	 */
	private void saveTestSuites(List<TestSuite> testSuites, String testSuiteId, Project project, User user) {
		String methodName = "saveTestSuites";
		List<TestCase> testCases=null;
		try
		{
		for(TestSuite testSuite : testSuites){		
			
			if(null == testSuiteId || "".equalsIgnoreCase(testSuiteId))
				testSuiteId = UUID.randomUUID().toString();
				//testSuite.setCreatedOn(new Date());
				testSuite.setCreatedBy(user.getUserName());
				testSuite.setActive(true);
				testSuite.setId(testSuiteId);
				testSuite.setProject(project);
				testSuite.setUser(user);
				
				tSuiteRep.save(testSuite);
				alServ.log("INFO", logger, className, methodName, SUCCESS_STATUS,
						" TestSuite with name "+testSuite.getSuiteName()+" saved successfully");				
			if(null!=testSuite.getTestCases())
			{
				testCases = testSuite.getTestCases();
				saveTestCases(testSuite, testCases, project, user);
			}
		}
		}
		catch(Exception e){
			alServ.logError(logger, className, methodName, FAILED_STATUS, "Exception occured while saving the TestSuite", e);
		}
	}

	/**
	 * @param testSuite
	 * @param testCases
	 * @param project
	 * @param user
	 */
	public void saveTestCases(TestSuite testSuite, List<TestCase> testCases, Project project, User user){
		String methodName = "saveTestCases";
		Integer order = null;
		try{
		for(TestCase testCase : testCases){
			testCase.setActive(true);
			if(null == testCase.getId() || "".equalsIgnoreCase(testCase.getId()))
			testCase.setId(UUID.randomUUID().toString());
			//testCase.setCreatedOn(new Date());
			testCase.setCreatedBy(user.getUserName());
			testCase.setProject(project);
			testCase.setUser(user);
			order = tcRep.findHighestOrderTestCaseInTestSuite(testSuite.getId(), project.getProjectCode(), user.getUserName());
			if(order == null)
				order = 1;
			testCase.setOrder(order+1);
			testCase.setTestSuite(testSuite);
			tcRep.save(testCase); 
			alServ.log("INFO", logger, className, methodName, SUCCESS_STATUS,
					" TestCase with name "+testCase.getTestCaseName()+" saved successfully");		
			if(testCase.getTestSteps() !=null)
			{
				List<TestStep> testSteps = testCase.getTestSteps();
				saveTestSteps(testCase, testSteps, project, user);
			}
		}
		}
		catch(Exception e){
			alServ.logError(logger, className, methodName, FAILED_STATUS, "Application encountered exception while saving TestCase objects", e);
		}
	}
	
	/**
	 * @param testCase
	 * @param testSteps
	 * @param project
	 * @param user
	 */
	public void saveTestSteps(TestCase testCase, List<TestStep> testSteps, Project project, User user) {
		String methodName = "saveTestSteps";
		Integer order = 1;
		try{
		for(TestStep testStep : testSteps){
			order = testSteps.indexOf(testStep);
			if(null == testStep.getId() || "".equalsIgnoreCase(testStep.getId()))
			testStep.setId(UUID.randomUUID().toString());
			testStep.setActive(true);
			//testStep.setCreatedOn(new Date());
			testStep.setCreatedBy(user.getUserName());
			testStep.setProject(project);
			testStep.setUser(user);
			testStep.setTestCase(testCase);
			order = tsRep.findHighestOrderTestStepInTestCase(testCase.getId(), project.getProjectCode(), user.getUserName());
			if(order == null)
				order = 1;
			testStep.setOrder(order+1);
			if(testStep.getTestCaseName()==null || testStep.getTestCaseDescription()==null)
			{
				testStep.setTestCaseName(testCase.getTestCaseName());
				testStep.setTestCaseDescription(testCase.getTestCaseDescription());
			}
			tsRep.save(testStep);
			alServ.log("INFO", logger, className, methodName, SUCCESS_STATUS,
					" TestStep "+testStep.getOrder()+" for "+testStep.getTestCaseName()+" saved successfully");	
			if(testStep.getStepDetails() != null){
				List<StepDetails> stepDetails = testStep.getStepDetails();
				saveStepDetails(testStep, stepDetails, user);
			}				
		}
		}
		catch(Exception e)
		{
			alServ.logError(logger, className, methodName, FAILED_STATUS, "Application encountered exception while saving the TestStep Objects with details", e);
		}
	}

	/**
	 * @param testStep
	 * @param stepDetails
	 * @param user
	 */
	private void saveStepDetails(TestStep testStep, List<StepDetails> stepDetails, User user) {
		String methodName = "saveStepDetails";
		StepDetails stepDetail = null;
	try{
		for(int i = 0; i<stepDetails.size(); i++)
	{
		stepDetail = stepDetails.get(i);
		stepDetail.setId(UUID.randomUUID().toString());
		stepDetail.setActive(true);
		stepDetail.setCreatedBy(user.getUserName());
		stepDetail.setOrder(i+2);
		stepDetail.setTestStep(testStep);
		tsDetailsRep.save(stepDetail);
		alServ.log("INFO", logger, className, methodName, SUCCESS_STATUS,
				" StepDetail- "+stepDetail.getOrder()+" for TestStep- "+testStep.getOrder()+" saved successfully.");
	}
	}catch(Exception e){
		alServ.logError(logger, className, methodName, FAILED_STATUS, "Application encountered exception with details", e);
	}

	}

	

public static class TestSuiteObj{
	
		
		private List<TestSuite> testSuites;
		
		private List<TestCase> testCases;
		
		private List<TestStep> testSteps;

		public List<TestSuite> getTestSuites() {
			return testSuites;
		}

		public void setTestSuites(List<TestSuite> testSuites) {
			this.testSuites = testSuites;
		}

		public List<TestCase> getTestCases() {
			return testCases;
		}

		public void setTestCases(List<TestCase> testCases) {
			this.testCases = testCases;
		}

		public List<TestStep> getTestSteps() {
			return testSteps;
		}

		public void setTestSteps(List<TestStep> testSteps) {
			this.testSteps = testSteps;
		}
		
	}

}
