package com.dhboa.automation.framework.controllers;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestSuite;

@RestController
public class TestCaseController extends AutowiredUtilObjects{

	@RequestMapping(value="/getTestSuites")
	public Page<TestSuite> getTestSuitesByUser(@RequestParam(value="pNo") int pNo, @RequestParam(value="pSize") int pSize, @RequestParam(value="SuiteName") String suiteName
			,@RequestParam(value="projectCode") String projectCode){
		return defServ.getTestSuites(pNo, pSize, suiteName, projectCode);
		
	}
	
	@RequestMapping(value="/getTestCases")
	public Page<TestCase> getTestCasesByUser(@RequestParam(value="pNo") int pNo, @RequestParam(value="pSize") int pSize, @RequestParam(value="caseName") String caseName
			 ,@RequestParam(value="testSuiteId") String testSuiteId,@RequestParam(value="projectCode") String projectCode){
		return defServ.getTestCases(pNo, pSize, testSuiteId, caseName, projectCode);
		
	}
	
	@RequestMapping(value="/getTestSteps")
	public Page<TestStep> getTestStepsByUser(@RequestParam(value="pNo") int pNo, @RequestParam(value="pSize") int pSize
			 ,@RequestParam(value="testCaseId") String testCaseId,@RequestParam(value="projectCode") String projectCode, @RequestParam(value="searchParam")String stepDetails){
		return defServ.getTestSteps(pNo, pSize, testCaseId, stepDetails, projectCode);
		
	}
}
