package com.dhboa.automation.framework.cloning;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.cloning.CloneService.ClonedObject;
import com.dhboa.automation.framework.cloning.CloneService.Parameters;
import com.dhboa.automation.framework.components.AfStoredProcedure;
import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.services.CommonUtility;

@Service
public class CloneTestSuite extends AutowiredUtilObjects{
	
	Logger logger = LoggerFactory.getLogger(getClass());
	String className = getClass().getCanonicalName();
	private String cloneTestSuiteProcedure = "clone_test_suite";
	
	public ClonedObject cloneObject(Parameters params) {
		String methodName = "cloneObject";
		String testSuiteId = null;
		User user = null;
		ClonedObject clonedObject = null;
		TestSuite suiteToBeCloned = null;
		TestSuite newTestSuite = null;
		Boolean isActive= true;
		String additional1 = null;
		String additional2 = null;
		String additional3 = null;
		String additional4 = null;
		String additional5 = null;
		String createdBy = null;
		Project project = null;
		String suiteDescription = null;
		String suiteName = null;
		String newSuiteId = null;
		TestSuite clonedTestSuite = null;
		try{
		
		if(null == params.getTestSuiteId() || "".equalsIgnoreCase(params.getTestSuiteId()))
		{
				alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
						"Application cannot procced to cloning the TestSuite as TestSuiteId is null.");
				throw new Exception("Cannot proceed further as TestSuite ID is null.");
			}else
				testSuiteId = params.getTestSuiteId();
				
				if(null != params.getUser())
					user = params.getUser();
				
		suiteToBeCloned = tSuiteRep.findOne(testSuiteId);
		newTestSuite = new TestSuite();	
		createdBy = user.getUserName();
		isActive = suiteToBeCloned.isActive();
		
		if(null!= suiteToBeCloned.getAdditional1() || !("".equalsIgnoreCase(suiteToBeCloned.getAdditional1())))
			additional1 = suiteToBeCloned.getAdditional1();
		if(null!= suiteToBeCloned.getAdditional2() || !("".equalsIgnoreCase(suiteToBeCloned.getAdditional2())))
			additional2 = suiteToBeCloned.getAdditional2();
		if(null!= suiteToBeCloned.getAdditional3() || !("".equalsIgnoreCase(suiteToBeCloned.getAdditional3())))
			additional3 = suiteToBeCloned.getAdditional3();
		if(null!= suiteToBeCloned.getAdditional4() || !("".equalsIgnoreCase(suiteToBeCloned.getAdditional4())))
			additional4 = suiteToBeCloned.getAdditional4();
		if(null!= suiteToBeCloned.getAdditional5() || !("".equalsIgnoreCase(suiteToBeCloned.getAdditional5())))
			additional5 = suiteToBeCloned.getAdditional5();
		if(null!= suiteToBeCloned.getProject())
			project = suiteToBeCloned.getProject();
		if(null!= suiteToBeCloned.getSuiteDescription() || !("".equalsIgnoreCase(suiteToBeCloned.getSuiteDescription())))
			suiteDescription = suiteToBeCloned.getSuiteDescription();
		if(null != suiteToBeCloned.getSuiteName() || !("".equalsIgnoreCase(suiteToBeCloned.getSuiteName())))
			suiteName = suiteToBeCloned.getSuiteName();
		
		alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
				"Cloning TestSuite with ID "+testSuiteId);
		newSuiteId = UUID.randomUUID().toString();
		newTestSuite.setId(newSuiteId);
		newTestSuite.setActive(isActive);
		newTestSuite.setAdditional1(additional1);
		newTestSuite.setAdditional2(additional2);
		newTestSuite.setAdditional3(additional3);
		newTestSuite.setAdditional4(additional4);
		newTestSuite.setAdditional5(additional5);
		newTestSuite.setCreatedBy(createdBy);
		newTestSuite.setCreatedOn(new Date());
		newTestSuite.setProject(project);
		newTestSuite.setSuiteDescription(suiteDescription);
		newTestSuite.setSuiteName(suiteName);
		newTestSuite.setTestSuiteReference(testSuiteId);
		newTestSuite.setUser(user);
		tSuiteRep.save(newTestSuite);
		alServ.log("INFO", logger, className, methodName, CommonUtility.SUCCESS_STATUS, "TestSuite with ID "+testSuiteId+" cloned successfully.");
		clonedTestSuite = tSuiteRep.findOne(newSuiteId);
		clonedObject = new ClonedObject();
		clonedObject.setTestSuite(clonedTestSuite);
		
		}
		catch(Exception e)
		{
			alServ.LogError(logger, e);
		alServ.logError(logger, className, methodName,
				CommonUtility.FAILED_STATUS, "Application encountered exception while cloning a TestSuite with id "+testSuiteId, e );	
		}
		return clonedObject;
	}
	
	public ClonedObject cloneAndEditObject(Parameters params){
		String methodName = "cloneAndEditObject";
		Map<String, Object> result = new HashMap<String, Object>();
		TestSuite testSuite = new TestSuite();
		String newSuiteId = null;
		DataSource dataSource = null;
		String testSuiteId = null;
		User user = null;
		ClonedObject clonedObject = null;
		try{
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS, 
			"Attempting to call Stored Procedure for cloning a TestSuite "+cloneTestSuiteProcedure);

	if(null == params.getTestSuiteId() || "".equalsIgnoreCase(params.getTestSuiteId()))
	{
			alServ.log("INFO", logger, className, methodName, CommonUtility.FAILED_STATUS,
					"Application cannot procced to cloning the TestSuite as TestSuiteId is null.");
			throw new Exception("Cannot proceed further as TestSuite ID is null.");
		}else
			testSuiteId = params.getTestSuiteId();
			
		if(null != params.getUser())
				user = params.getUser();
		dataSource = params.getDataSource();	
		AfStoredProcedure storedProcedure = new AfStoredProcedure(dataSource, cloneTestSuiteProcedure);
		storedProcedure.declareParameter(new SqlParameter("suite_id", Types.VARCHAR));
		storedProcedure.declareParameter(new SqlParameter("user_name", Types.VARCHAR));
		storedProcedure.declareParameter( new SqlOutParameter("new_suite_id", Types.VARCHAR));
		storedProcedure.compile();
	alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
			"Compliled procedure "+cloneTestSuiteProcedure+" successfully.");	
		
		result = storedProcedure.execute(testSuiteId, user.getUserName());
		alServ.log("INFO", logger, className, methodName, CommonUtility.IN_PROGRESS_STATUS,
				"Executed procedure "+cloneTestSuiteProcedure+" successfully.");
		newSuiteId = (String) result.get("new_suite_id");
		testSuite = tSuiteRep.findOne(newSuiteId);
		clonedObject = new ClonedObject();
		clonedObject.setTestSuite(testSuite);
		
		}
		catch(Exception e){
alServ.logError(logger, className, methodName, CommonUtility.FAILED_STATUS, 
		"Exception occured while executing the procedure "+cloneTestSuiteProcedure+" for Test Suite with Id "+testSuiteId, e);
		}
		return clonedObject;
		
	}
}
