package com.dhboa.automation.framework.services;

import java.util.UUID;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestCaseResults;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.TestStepResults;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.TestSuiteResults;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.repositories.TestCaseResultsRepository;
import com.dhboa.automation.framework.repositories.TestStepResultsRepository;
import com.dhboa.automation.framework.repositories.TestSuiteRepository;
import com.dhboa.automation.framework.repositories.TestSuiteResultsRepository;

/**
 * This service will be having all the utilities which are necessary for the
 * application. Exceptions in this block should not hamper the actual logic.
 * Hence proper exception handling must be in place.
 * 
 * @author KiranJoshi
 *
 */
@Service
public class UtilityService {

	@Autowired
	TestSuiteRepository tSuiteRep;
	@Autowired
	TestSuiteResultsRepository tSuiteResRep;
	@Autowired
	TestCaseResultsRepository tCaseResRep;
	@Autowired
	TestStepResultsRepository tStepResRep;

	public TestSuiteResults logTestSuiteStatus(Logger logger, String status, String statusDescription,
			TestSuite testSuiteId, Project project, User user, TestSuiteResults tSuiteResultRef) {
		String id = null;
		try {
			if (null == tSuiteResultRef) {
				// This means this is a fresh insert. Insert with above details
				tSuiteResultRef = new TestSuiteResults();
				id = UUID.randomUUID().toString();
				tSuiteResultRef.setId(id);
				tSuiteResultRef.setTestSuiteId(testSuiteId);
				tSuiteResultRef.setProject(project);
				tSuiteResultRef.setUser(user);
			}
			// This means that you just have to update the record with proper
			// status
			tSuiteResultRef.setStatus(status);
			tSuiteResultRef.setStatusDescription(statusDescription);
			tSuiteResRep.save(tSuiteResultRef);
		} catch (Exception e) {
			// Do not do anything here
			return null;
		} finally {

		}
		return tSuiteResultRef;
	}

	public TestCaseResults logTestCaseStatus(Logger logger, String status, String statusDescription,
			TestSuiteResults testSuiteResultRef, TestCase testCase, int order, TestCaseResults tCaseResultRef) {
		try {
			if (null == tCaseResultRef) {
				tCaseResultRef = new TestCaseResults();
				tCaseResultRef.setId(UUID.randomUUID().toString());
				tCaseResultRef.setTestCaseId(testCase);
				tCaseResultRef.setSuiteResultId(testSuiteResultRef);
				tCaseResultRef.setOrder(order);
			}

			tCaseResultRef.setStatus(status);
			tCaseResultRef.setStatusDescription(statusDescription);
			tCaseResRep.save(tCaseResultRef);

		} catch (Exception e) {
			// Do not do anything here
			return null;
		} finally {

		}
		return tCaseResultRef;
	}

	public TestStepResults logTestStepStatus(Logger logger, String status, String statusDescription,
			TestCaseResults testCaseResultRef, TestStep testStepId, int order, TestStepResults testStepResultRef) {
		try {
			if (null == testStepResultRef) {
				testStepResultRef = new TestStepResults();
				testStepResultRef.setId(UUID.randomUUID().toString());
				testStepResultRef.setStepId(testStepId);
				testStepResultRef.setOrder(order);
				testStepResultRef.setTestCaseResultsId(testCaseResultRef);
			}

			testStepResultRef.setStatus(status);
			testStepResultRef.setStatusDescription(statusDescription);
			tStepResRep.save(testStepResultRef);
		} catch (Exception e) {
			// Do not do anything here
			return null;
		} finally {

		}
		return testStepResultRef;
	}
}
