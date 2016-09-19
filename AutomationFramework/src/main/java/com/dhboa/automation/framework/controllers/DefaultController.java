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

	@RequestMapping(name = "/executeTestSuite")
	public String executeTestSuite(@RequestParam(name = "suiteId") String suiteId,
			@RequestParam(name = "projectCode") String projectCode, @RequestParam(name = "userName") String userName)
			throws Exception {
		defServ.runDefaultService(suiteId, projectCode, userName);
		return "success";
	}
}
