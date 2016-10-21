package com.dhboa.automation.framework.cloning;

import java.lang.reflect.Method;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.services.CommonUtility;


@Service
public class CloneService extends AutowiredUtilObjects{

	@Autowired
	DataSource dataSource;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	String className = getClass().getCanonicalName();


	
	public ClonedObject clone(String testSuiteId, String testCaseId, String testStepId, boolean isEditable, String userName, String objectToBeCloned) {
		String methodName = "clone";
		Class<?> classtoBecalled = null;
		Method method = null;
		User user = null;
		Object instance = null;
		ClonedObject clonedObject = null;
		User loggedInUser = null;
		user = userRep.findOne(userName);
		try {
			 loggedInUser = commonUtil.getLoggedInUser();
		if(null == userName || "".equalsIgnoreCase(userName))
			user = loggedInUser;
		else
			user = userRep.findOne(userName);
		
		Parameters params = new Parameters();
		params.setTestStepId(testStepId);
		params.setTestCaseId(testCaseId);
		params.setTestSuiteId(testSuiteId);
		params.setUser(user);
		params.setDataSource(dataSource);
		
		switch (objectToBeCloned.toLowerCase()) {
		case "testsuite":
				classtoBecalled = Class.forName("com.dhboa.automation.framework.cloning.CloneTestSuite");
				instance = cloneTestSuite;
			break;
		case "testcase":
			 classtoBecalled = Class.forName("com.dhboa.automation.framework.cloning.CloneTestCase");
			break;
		case "teststep":
			 classtoBecalled = Class.forName("com.dhboa.automation.framework.cloning.CloneTestStep");
			break;
		default:
			break;
		}
		if(!isEditable)
		 method = classtoBecalled.getMethod("cloneObject", Parameters.class);
		else
		method = classtoBecalled.getMethod("cloneAndEditObject", Parameters.class);
		 clonedObject = (ClonedObject) method.invoke(instance,params);

	} catch(Exception e){
	alServ.logError(logger, methodName, className, CommonUtility.FAILED_STATUS,
			"Error occured while cloning.", e);	
	}
		return clonedObject;
	}	
	
	

public static class Parameters{
	
	private String testSuiteId;
	
	private String testCaseId;
	
	private String testStepId;
	
	private User user;
	
	private DataSource dataSource;
	
	public String getTestSuiteId() {
		return testSuiteId;
	}
	public void setTestSuiteId(String testSuiteId) {
		this.testSuiteId = testSuiteId;
	}
	public String getTestCaseId() {
		return testCaseId;
	}
	public void setTestCaseId(String testCaseId) {
		this.testCaseId = testCaseId;
	}
	public String getTestStepId() {
		return testStepId;
	}
	public void setTestStepId(String testStepId) {
		this.testStepId = testStepId;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public DataSource getDataSource() {
		return dataSource;
	}
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	
	
}
	public static class ClonedObject{
		
		public TestSuite testSuite;
		
		public TestCase testCase;
		
		public TestStep testStep;
		
		public TestSuite getTestSuite() {
			return testSuite;
		}
		public void setTestSuite(TestSuite testSuite) {
			this.testSuite = testSuite;
		}
		public TestCase getTestCase() {
			return testCase;
		}
		public void setTestCase(TestCase testCase) {
			this.testCase = testCase;
		}
		public TestStep getTestStep() {
			return testStep;
		}
		public void setTestStep(TestStep testStep) {
			this.testStep = testStep;
		}
		
		
	}
}
