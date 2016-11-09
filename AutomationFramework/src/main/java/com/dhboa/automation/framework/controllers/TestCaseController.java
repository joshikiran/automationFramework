package com.dhboa.automation.framework.controllers;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestSuite;

@RestController
public class TestCaseController extends AutowiredUtilObjects{

	@RequestMapping(value="/getTestCases")
	public Page<TestCase> getTestCasesByUser(@RequestParam(value="pNo") int pNo, @RequestParam(value="pSize") int pSize, @RequestParam(value="caseName") String caseName
			 ,@RequestParam(value="testSuiteId") String testSuiteId){
		PageRequest page = new PageRequest(pNo, pSize, Direction.ASC,"order");
		return defServ.getTestCases(pNo, pSize, testSuiteId, caseName);
		
	}
	
	@RequestMapping(value="/getTestSteps")
	public Page<TestStep> getTestStepsByUser(@RequestParam(value="pNo") int pNo, @RequestParam(value="pSize") int pSize
			 ,@RequestParam(value="testCaseId") String testCaseId){
		PageRequest page = new PageRequest(pNo, pSize, Direction.ASC,"order");
		return defServ.getTestSteps(pNo, pSize, testCaseId);
		
	}
}
