package com.dhboa.automation.framework.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.dhboa.automation.framework.cloning.CloneService;
import com.dhboa.automation.framework.cloning.CloneTestCase;
import com.dhboa.automation.framework.cloning.CloneTestStep;
import com.dhboa.automation.framework.cloning.CloneTestSuite;
import com.dhboa.automation.framework.repositories.MethodParamsRepository;
import com.dhboa.automation.framework.repositories.MethodRepository;
import com.dhboa.automation.framework.repositories.ProjectRepository;
import com.dhboa.automation.framework.repositories.TestCaseRepository;
import com.dhboa.automation.framework.repositories.TestStepDetailsRepository;
import com.dhboa.automation.framework.repositories.TestStepRepository;
import com.dhboa.automation.framework.repositories.TestSuiteRepository;
import com.dhboa.automation.framework.repositories.UserProjectRepository;
import com.dhboa.automation.framework.repositories.UserRepository;
import com.dhboa.automation.framework.repositories.VariableRepository;
import com.dhboa.automation.framework.services.ActivityLogService;
import com.dhboa.automation.framework.services.CommonUtility;
import com.dhboa.automation.framework.services.PersistService;
import com.dhboa.automation.framework.services.UserManagementService;
import com.dhboa.automation.framework.services.DefaultService;
import com.dhboa.automation.framework.services.UtilityService;


public abstract class AutowiredUtilObjects {

	 
	@Autowired
	protected CommonActions comm;
	@Autowired
	protected MethodRepository mRep;
	@Autowired
	protected MethodParamsRepository mpRep;
	@Autowired
	protected TestStepRepository tsRep;
	@Autowired
	protected TestSuiteRepository tSuiteRep;
	@Autowired
	protected TestCaseRepository tcRep;
	@Autowired
	protected TestStepDetailsRepository tsDetailsRep;
	@Autowired
	protected ProjectRepository projRep;
	@Autowired
	protected UserRepository userRep;
	@Autowired
	protected VariableRepository varRep;
	@Autowired 
	protected UserProjectRepository userProjRep;
	
	@Autowired
	protected ActivityLogService alServ;
	@Autowired
	protected UtilityService utilServ;
	@Autowired
	protected DefaultService defServ;
	@Autowired
	protected PersistService persistServ;
	@Autowired
	protected CloneService cloneServ;
	@Autowired
	protected UserManagementService userServ;
	@Autowired
	protected CommonUtility commonUtil;
	@Autowired
	protected CloneTestSuite cloneTestSuite;
	@Autowired
	protected CloneTestCase cloneTestCase;
	@Autowired
	protected CloneTestStep cloneTestStep;

}
