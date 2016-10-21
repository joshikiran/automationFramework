package com.dhboa.automation.framework.cloning;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.cloning.CloneService.ClonedObject;
import com.dhboa.automation.framework.cloning.CloneService.Parameters;
import com.dhboa.automation.framework.components.AfStoredProcedure;
import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.services.CommonUtility;

@Service
public class CloneTestStep extends AutowiredUtilObjects {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	String className = getClass().getCanonicalName();
	private String cloneTestStepProcedure = "clone_test_step";
	
	public void cloneObject(Parameters params) {
		String methodName = "cloneObject";
		TestStep stepToBeCloned = null;
		TestStep newTestStep = null;
		Boolean isActive= true;
		String additional1 = null;
		String additional2 = null;
		String additional3 = null;
		String additional4 = null;
		String additional5 = null;
		String createdBy = null;
		Project project = null;
		String testCaseDescription = null;
		String testCaseName = null;
		int order = 0;
		TestCase testCase = null;
		User user = null;
		String testCaseId = null;
		String testStepId = null;
		ClonedObject clonedObject = null;
		String newStepId = null;
		TestStep clonedTestStep= null;
		try{
			
			if(null == params.getTestStepId() || "".equalsIgnoreCase(params.getTestStepId()))
				{
					alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
							"Application cannot procced to cloning the TestStep as TestStepId is null.");
					throw new Exception("Cannot proceed further as TestStep ID is null.");
				}else
					testStepId = params.getTestStepId();
			if(null == params.getTestCaseId() || "".equalsIgnoreCase(params.getTestCaseId()))
				{
					alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
							"Application cannot procced to cloning the TestCase as TestCaseId is null.");
					throw new Exception("Cannot proceed further as TestCase ID is null.");
				}else
					testCaseId = params.getTestCaseId();
					
					if(null != params.getUser())
						user = params.getUser();
			
			
			stepToBeCloned = tsRep.findOne(testStepId);
			newTestStep = new TestStep();
			createdBy = user.getUserName();
			isActive = stepToBeCloned.isActive();

			if(null!= stepToBeCloned.getAdditional1() || !("".equalsIgnoreCase(stepToBeCloned.getAdditional1())))
				additional1 = stepToBeCloned.getAdditional1();
			if(null!= stepToBeCloned.getAdditional2() || !("".equalsIgnoreCase(stepToBeCloned.getAdditional2())))
				additional2 = stepToBeCloned.getAdditional2();
			if(null!= stepToBeCloned.getAdditional3() || !("".equalsIgnoreCase(stepToBeCloned.getAdditional3())))
				additional3 = stepToBeCloned.getAdditional3();
			if(null!= stepToBeCloned.getAdditional4() || !("".equalsIgnoreCase(stepToBeCloned.getAdditional4())))
				additional4 = stepToBeCloned.getAdditional4();
			if(null!= stepToBeCloned.getAdditional5() || !("".equalsIgnoreCase(stepToBeCloned.getAdditional5())))
				additional5 = stepToBeCloned.getAdditional5();
			if(null!= stepToBeCloned.getProject())
				project = stepToBeCloned.getProject();
			if(null!= stepToBeCloned.getTestCaseDescription() || !("".equalsIgnoreCase(stepToBeCloned.getTestCaseDescription())))
				testCaseDescription = stepToBeCloned.getTestCaseDescription();
			if(null != stepToBeCloned.getTestCaseName() || !("".equalsIgnoreCase(stepToBeCloned.getTestCaseName())))
				testCaseName = stepToBeCloned.getTestCaseName();
			if(null!= (Integer)stepToBeCloned.getOrder())
				order = stepToBeCloned.getOrder();
			if(null != testCaseId || !("".equalsIgnoreCase(testCaseId)))
			{
				testCase = tcRep.findOne(testCaseId);
			}
			else if(null != stepToBeCloned.getTestCase())
			{
				testCase = stepToBeCloned.getTestCase();
			}
	
			alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS, 
					"Cloning TestStep with ID "+testStepId);
			newStepId = UUID.randomUUID().toString();
			newTestStep.setId(newStepId);
			newTestStep.setActive(isActive);
			newTestStep.setAdditional1(additional1);
			newTestStep.setAdditional2(additional2);
			newTestStep.setAdditional3(additional3);
			newTestStep.setAdditional4(additional4);
			newTestStep.setAdditional5(additional5);
			newTestStep.setCreatedBy(createdBy);
			newTestStep.setCreatedOn(new Date());
			newTestStep.setProject(project);
			newTestStep.setTestCaseDescription(testCaseDescription);
			newTestStep.setTestCaseName(testCaseName);
			newTestStep.setOrder(order);
			newTestStep.setTestCase(testCase);
			newTestStep.setTestStepRef(testStepId);
			newTestStep.setUser(user);
			tsRep.save(newTestStep);
			clonedTestStep = tsRep.findOne(newStepId);			
			clonedObject = new ClonedObject();
			clonedObject.setTestStep(clonedTestStep);
			alServ.log("INFO", logger, className, methodName, CommonUtility.SUCCESS_STATUS, "TestStep with ID "+testStepId+" cloned successfully.");
			}
			catch(Exception e)
			{
				alServ.LogError(logger, e);
			alServ.logError(logger, className, methodName,
					CommonUtility.FAILED_STATUS, "Application encountered exception while cloning a TestStep with id "+testStepId, e );	
			}
		
	}


	
	public ClonedObject cloneAndEditObject(Parameters params){
		String methodName = "cloneAndEditObject";
		Map<String, Object> result = new HashMap<String, Object>();
		TestStep testStep = new TestStep();
		String newStepId = null;
		String testCaseId = null;
		String testStepId = null;
		User user = null;
		DataSource dataSource = null;
		ClonedObject clonedObject = null;
		try{
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS, 
			"Attempting to call Stored Procedure for cloning a TestStep "+cloneTestStepProcedure);
	if(null == params.getTestStepId() || "".equalsIgnoreCase(params.getTestStepId()))
	{
		alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
				"Application cannot procced to cloning the TestStep as TestStepId is null.");
		throw new Exception("Cannot proceed further as TestStep ID is null.");
	}else
		testStepId = params.getTestStepId();
if(null == params.getTestCaseId() || "".equalsIgnoreCase(params.getTestCaseId()))
	{
		alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
				"Application cannot procced to cloning the TestCase as TestCaseId is null.");
		throw new Exception("Cannot proceed further as TestCase ID is null.");
	}else
		testCaseId = params.getTestCaseId();

		if(null != params.getUser())
				user = params.getUser();
		dataSource = params.getDataSource();
		AfStoredProcedure storedProcedure = new AfStoredProcedure(dataSource, cloneTestStepProcedure);
		storedProcedure.declareParameter(new SqlParameter("step_id", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("case_id", Types.VARCHAR));		
		storedProcedure.declareParameter(new SqlParameter("user_name", Types.VARCHAR));
		storedProcedure.declareParameter( new SqlOutParameter("new_test_step_id", Types.VARCHAR));
		storedProcedure.compile();
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
			"Compliled procedure "+cloneTestStepProcedure+" successfully.");	
		
		result = storedProcedure.execute(testStepId, testCaseId, user.getUserName());
		alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
				"Executed procedure "+cloneTestStepProcedure+" successfully.");
		newStepId = (String) result.get("new_test_step_id");
		testStep = tsRep.findOne(newStepId);
		clonedObject = new ClonedObject();
		clonedObject.setTestStep(testStep);
		}
		catch(Exception e){
alServ.logError(logger, className, methodName, CommonUtility.FAILED_STATUS, 
		"Exception occured while executing the procedure "+cloneTestStepProcedure+" for Test Step with Id "+testStepId, e);
		}
		return clonedObject;
		
	}
}
