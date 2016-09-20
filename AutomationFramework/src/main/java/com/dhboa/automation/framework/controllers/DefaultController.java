package com.dhboa.automation.framework.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhboa.automation.framework.services.DefaultService;

@RestController
public class DefaultController {

	@Autowired
	DefaultService defServ;

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

}
