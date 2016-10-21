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
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.services.CommonUtility;

@Service
public class CloneTestCase extends AutowiredUtilObjects{

	Logger logger = LoggerFactory.getLogger(getClass());
	String className = getClass().getCanonicalName();
	private String cloneTestCaseProcedure = "clone_test_case";

	
	public ClonedObject cloneObject(Parameters params) {
		String methodName = "cloneObject";
		TestCase CaseToBeCloned = null;
		TestCase newTestCase = null;
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
		TestSuite testSuite = null;
		User user = null;
		String testSuiteId = null;
		String testCaseId = null;
		ClonedObject clonedObject = null;
		String newCaseId = null;
		TestCase clonedTestCase = null;
		try{
		
			
			if(null == params.getTestSuiteId() || "".equalsIgnoreCase(params.getTestSuiteId()))
				{
					alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
							"Application cannot procced to cloning the TestSuite as TestSuiteId is null.");
					throw new Exception("Cannot proceed further as TestSuite ID is null.");
				}else
					testSuiteId = params.getTestSuiteId();
			if(null == params.getTestCaseId() || "".equalsIgnoreCase(params.getTestCaseId()))
				{
					alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
							"Application cannot procced to cloning the TestCase as TestCaseId is null.");
					throw new Exception("Cannot proceed further as TestCase ID is null.");
				}else
					testCaseId = params.getTestCaseId();

					
					if(null != params.getUser())
						user = params.getUser();
			
			CaseToBeCloned = tcRep.findOne(testCaseId);
			newTestCase = new TestCase();
			createdBy = user.getUserName();
			isActive = CaseToBeCloned.isActive();
			
			if(null!= CaseToBeCloned.getAdditional1() || !("".equalsIgnoreCase(CaseToBeCloned.getAdditional1())))
				additional1 = CaseToBeCloned.getAdditional1();
			if(null!= CaseToBeCloned.getAdditional2() || !("".equalsIgnoreCase(CaseToBeCloned.getAdditional2())))
				additional2 = CaseToBeCloned.getAdditional2();
			if(null!= CaseToBeCloned.getAdditional3() || !("".equalsIgnoreCase(CaseToBeCloned.getAdditional3())))
				additional3 = CaseToBeCloned.getAdditional3();
			if(null!= CaseToBeCloned.getAdditional4() || !("".equalsIgnoreCase(CaseToBeCloned.getAdditional4())))
				additional4 = CaseToBeCloned.getAdditional4();
			if(null!= CaseToBeCloned.getAdditional5() || !("".equalsIgnoreCase(CaseToBeCloned.getAdditional5())))
				additional5 = CaseToBeCloned.getAdditional5();
			if(null!= CaseToBeCloned.getProject())
				project = CaseToBeCloned.getProject();
			if(null!= CaseToBeCloned.getTestCaseDescription() || !("".equalsIgnoreCase(CaseToBeCloned.getTestCaseDescription())))
				testCaseDescription = CaseToBeCloned.getTestCaseDescription();
			if(null != CaseToBeCloned.getTestCaseName() || !("".equalsIgnoreCase(CaseToBeCloned.getTestCaseName())))
				testCaseName = CaseToBeCloned.getTestCaseName();
			if(null!= (Integer)CaseToBeCloned.getOrder())
				order = CaseToBeCloned.getOrder();
			if(null != testSuiteId || !("".equalsIgnoreCase(testSuiteId)))
			{
				testSuite = tSuiteRep.findOne(testSuiteId);
			}
			else if(null != CaseToBeCloned.getTestSuite())
			{
				testSuite = CaseToBeCloned.getTestSuite();
			}
			
			alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS, 
					"Cloning TestCase with ID "+testCaseId);
			newCaseId = UUID.randomUUID().toString();
			newTestCase.setId(newCaseId);
			newTestCase.setActive(isActive);
			newTestCase.setAdditional1(additional1);
			newTestCase.setAdditional2(additional2);
			newTestCase.setAdditional3(additional3);
			newTestCase.setAdditional4(additional4);
			newTestCase.setAdditional5(additional5);
			newTestCase.setCreatedBy(createdBy);
			newTestCase.setCreatedOn(new Date());
			newTestCase.setProject(project);
			newTestCase.setTestCaseDescription(testCaseDescription);
			newTestCase.setTestCaseName(testCaseName);
			newTestCase.setOrder(order);
			newTestCase.setTestSuite(testSuite);
			newTestCase.setTestCaseReference(testCaseId);
			newTestCase.setUser(user);
			
			tcRep.save(newTestCase);
			alServ.log("INFO", logger, className, methodName, CommonUtility.SUCCESS_STATUS, "TestCase with ID "+testCaseId+" cloned successfully.");
			clonedTestCase = tcRep.findOne(newCaseId);
			clonedObject = new ClonedObject();
			clonedObject.setTestCase(clonedTestCase);
		}
			catch(Exception e)
			{
				alServ.LogError(logger, e);
			alServ.logError(logger, className, methodName,
					CommonUtility.FAILED_STATUS, "Application encountered exception while cloning a TestCase with id "+testCaseId, e );	
			}
		return clonedObject;
	}
	
	public ClonedObject cloneAndEditObject(Parameters params){
		String methodName = "cloneAndEditObject";
		Map<String, Object> result = new HashMap<String, Object>();
		TestCase testCase = new TestCase();
		String newCaseId = null;
		DataSource dataSource = null;
		String testSuiteId = null;
		String testCaseId = null;
		User user = null;
		ClonedObject clonedObject = null;
		try{
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS, 
			"Attempting to call Stored Procedure for cloning a TestCase "+cloneTestCaseProcedure);
	if(null == params.getTestSuiteId() || "".equalsIgnoreCase(params.getTestSuiteId()))
	{
		alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
				"Application cannot procced to cloning the TestSuite as TestSuiteId is null.");
		throw new Exception("Cannot proceed further as TestSuite ID is null.");
	}else
		testSuiteId = params.getTestSuiteId();
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
		AfStoredProcedure storedProcedure = new AfStoredProcedure(dataSource, cloneTestCaseProcedure);
		storedProcedure.declareParameter(new SqlParameter("case_id", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("suite_id", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("user_name", Types.VARCHAR));
		storedProcedure.declareParameter( new SqlOutParameter("new_test_case_id", Types.VARCHAR));
		storedProcedure.compile();
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
			"Compliled procedure "+cloneTestCaseProcedure+" successfully.");	
		
		result = storedProcedure.execute(testCaseId, testSuiteId, user.getUserName());
		alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
				"Executed procedure "+cloneTestCaseProcedure+" successfully.");
		newCaseId = (String) result.get("new_test_case_id");
		testCase = tcRep.findOne(newCaseId);
		clonedObject = new ClonedObject();
		clonedObject.setTestCase(testCase);
		}
		catch(Exception e){
alServ.logError(logger, className, methodName, CommonUtility.FAILED_STATUS, 
		"Exception occured while executing the procedure "+cloneTestCaseProcedure+" for Test Case with Id "+testCaseId, e);
		}
		return clonedObject;
		
	}
}
