package com.dhboa.automation.framework.controllers;


import java.lang.reflect.InvocationTargetException;
import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhboa.automation.framework.cloning.CloneService;
import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Method;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.services.PersistService.TestSuiteObj;

@RestController
public class DefaultController extends AutowiredUtilObjects {
	
	@RequestMapping(value = "/executeTestSuite")
	public String executeTestSuite(@RequestParam(name = "suiteId") String suiteId,
			@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "userName") String userName,
			@RequestParam(name = "browser", defaultValue = "chrome", required = false) String browser)
			throws Exception {
		defServ.runTestSuite(suiteId, projectCode, userName, browser);
		return "success";
	}

	@RequestMapping(value = "/executeTestCase")
	public String executeTestCase(@RequestParam(name = "suiteId") String suiteId,
			@RequestParam(name = "caseId") String caseId, @RequestParam(name = "projectCode") String projectCode,
			@RequestParam(name = "userName") String userName,
			@RequestParam(name = "browser", defaultValue = "chrome", required = false) String browser)
			throws Exception {
		defServ.runTestCase(suiteId, caseId, projectCode, userName, browser);
		return "success";
	}

	
	@RequestMapping(value="/default/getMethods")
	public List<Method> getMethods(){
		return mRep.findAll();
	}
	
	@RequestMapping(value="default/save", method= RequestMethod.POST)
	public void saveAny(@RequestBody TestSuiteObj object, @RequestParam(name="testSuiteId")String testSuiteId,
			@RequestParam(name="projectCode")String projectCode, @RequestParam(name="testCaseId")String testCaseId){
		persistServ.saveTestObjects(object, testSuiteId, projectCode, testCaseId);		
	}
	
	@RequestMapping(value="default/getTestCase")
	public List<TestCase> getTestCase(){
		return tcRep.findAll();
	}
	
	@RequestMapping(value="/default/clone")
	public void clonetemplate(@RequestParam(name="testCaseId" , required=false) String testCaseId,
			@RequestParam(name="testSuiteId", required= false) String testSuiteId, @RequestParam(name="testStepId", required= false) String testStepId, 
			@RequestParam(value="edit", defaultValue= "false" )boolean isEditable, @RequestParam(name="user")String userName, @RequestParam(value="objectToBeCloned")String objectToBeCloned) throws NoSuchMethodException, SecurityException, ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException{
		cloneServ.clone(testSuiteId, testCaseId, testStepId, isEditable, userName, objectToBeCloned);
	}
	
	
	
}
