package com.dhboa.automation.framework.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ScriptGenerationUtils {

	private static String insertTestSuite = "INSERT INTO `automationtesting`.`af_test_suite`(`id`,`additional1`,`additional2`,`additional3`,`additional4`,`additional5`,`created_by`,`created_on`,`modified_by`,`modified_on`,`enabled`,`suite_description`,`suite_name`,`project_name`,`username`,`test_suite_reference`) VALUES( SUITE_RANDOM_ID,null,null,null,null,null,null,null,null,null,1,SUITE_NAME,SUITE_NAME,PROJECT_CODE,USER_NAME,null);";
	private static String insertTestCase = "INSERT INTO `automationtesting`.`af_test_case`(`id`,`additional1`,`additional2`,`additional3`,`additional4`,`additional5`,`created_by`,`created_on`,`modified_by`,`modified_on`,`enabled`,`is_template`,`field_order`,`test_case_description`,`test_case_name`,`project_code`,`test_suite_id`,`username`,`test_case_reference`) VALUES (RANDOM_ID,null,null,null,null,null,null,null,null,null,1,0,FIELD_ORDER,TEST_CASE_NAME,TEST_CASE_NAME,PROJECT_CODE,SUITE_ID,USER_NAME,null);";
	private static String insertTestStep = "INSERT INTO `automationtesting`.`af_test_step`(`id`,`additional1`,`additional2`,`additional3`,`additional4`,`additional5`,`created_by`,`created_on`,`modified_by`,`modified_on`,`enabled`,`field_order`,`test_case_description`,`test_case_name`,`project_code`,`test_case_id`,`username`,`test_step_reference`) VALUES (STEP_RANDOM_ID,null,null,null,null,null,null,null,null,null,1,FIELD_ORDER,STEP_DESCRIPTION,STEP_DESCRIPTION,PROJECT_CODE,TEST_CASE_ID,USER_NAME,null);";
	private static String insertStepDetails = "INSERT INTO `automationtesting`.`af_step_details`(`id`,`additional1`,`additional2`,`additional3`,`additional4`,`additional5`,`created_by`,`created_on`,`modified_by`,`modified_on`,`class_name`,`field_value`,`enabled`,`field_order`,`param_description`,`param_name`,`test_step_id`) VALUES (STEP_DETAILS_ID,null,null,null,null,null,null,null,null,null,null,FIELD_VALUE,1,FIELD_ORDER,PARAM_DESC,PARAM_DESC,TEST_STEP_ID);";

	private static List<String> testSuite = new ArrayList<>();
	private static List<String> testCase = new ArrayList<>();
	private static List<String> testStep = new ArrayList<>();
	private static List<String> testStepDetails = new ArrayList<>();
	private static String testSuiteId = "6";
	private static String testCaseId = null;
	private static String testSuiteDescription = "Default Test Suite";
	private static String projectCode = "TnE";
	private static String userName = "default";
	private static String testCaseOrder = "1";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String fileName = "D:\\Kiran\\OpenGmail.txt";
		generateScripts(fileName);
	}

	public static void generateTestSuite() {
		String testSuiteStr = insertTestSuite;
		if (null == testSuiteId)
			testSuiteId = UUID.randomUUID().toString();
		testSuiteStr = testSuiteStr.replace("SUITE_RANDOM_ID", "'" + testSuiteId + "'");
		testSuiteStr = testSuiteStr.replace("SUITE_NAME", "'" + testSuiteDescription + "'");
		testSuiteStr = testSuiteStr.replace("PROJECT_CODE", "'" + projectCode + "'");
		testSuiteStr = testSuiteStr.replace("USER_NAME", "'" + userName + "'");
		testSuite.add(testSuiteStr);
	}

	public static void generateTestCase() {
		String testCaseStr = insertTestCase;
		if (null == testCaseId)
			testCaseId = UUID.randomUUID().toString();
		testCaseStr = testCaseStr.replace("RANDOM_ID", "'" + testCaseId + "'");
		testCaseStr = testCaseStr.replace("FIELD_ORDER", "'" + testCaseOrder + "'");
		testCaseStr = testCaseStr.replace("TEST_CASE_NAME", "'ScriptGenerated'");
		testCaseStr = testCaseStr.replace("SUITE_ID", "'" + testSuiteId + "'");
		testCaseStr = testCaseStr.replace("PROJECT_CODE", "'" + projectCode + "'");
		testCaseStr = testCaseStr.replace("USER_NAME", "'" + userName + "'");
		testCase.add(testCaseStr);
	}

	public static void generateScripts(String fileName) {
		generateTestSuite();
		generateTestCase();
		BufferedReader br = null;
		int lineNumber = 1;
		try {
			String sCurrentLine;
			br = new BufferedReader(new FileReader(fileName));
			while ((sCurrentLine = br.readLine()) != null) {
				readLine(sCurrentLine, lineNumber++);
			}
			printScript();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void readLine(String testStepLine, int lineNumber) {
		// Generate a Test Step and iterate and generate step details
		String[] lines = testStepLine.split(",");
		boolean toGenerate = true;
		if ("0".equals(lines[0]))
			toGenerate = false;
		if (toGenerate) {
			String testStepStr = insertTestStep;
			String testStepId = UUID.randomUUID().toString();
			testStepStr = testStepStr.replace("STEP_RANDOM_ID", "'" + testStepId + "'");
			testStepStr = testStepStr.replace("FIELD_ORDER", "'" + String.valueOf(lineNumber) + "'");
			testStepStr = testStepStr.replace("STEP_DESCRIPTION", "'ScriptGenerated'");
			testStepStr = testStepStr.replace("TEST_CASE_ID", "'" + testCaseId + "'");
			testStepStr = testStepStr.replace("PROJECT_CODE", "'" + projectCode + "'");
			testStepStr = testStepStr.replace("USER_NAME", "'" + userName + "'");
			testStep.add(testStepStr);

			// Split by , separated and write another script
			String[] fieldVals = testStepLine.split(",");
			for (int i = 1; i < fieldVals.length; i++) {
				String stepDetailsStr = insertStepDetails;
				String stepDetailsId = UUID.randomUUID().toString();
				stepDetailsStr = stepDetailsStr.replace("STEP_DETAILS_ID", "'" + stepDetailsId + "'");
				stepDetailsStr = stepDetailsStr.replace("FIELD_ORDER", "'" + String.valueOf(i + 1) + "'");
				stepDetailsStr = stepDetailsStr.replace("PARAM_DESC", "'ScriptGenerated'");
				stepDetailsStr = stepDetailsStr.replace("TEST_STEP_ID", "'" + testStepId + "'");
				stepDetailsStr = stepDetailsStr.replace("FIELD_VALUE", "'" + fieldVals[i] + "'");
				testStepDetails.add(stepDetailsStr);
			}
		}
	}

	public static void printScript() {
		for (String suite : testSuite) {
			System.out.println(suite);
		}
		System.out.println("/*--------------------------------------------------*/");
		for (String caseStr : testCase) {
			System.out.println(caseStr);
		}
		System.out.println("/*--------------------------------------------------*/");
		for (String stepStr : testStep) {
			System.out.println(stepStr);
		}
		System.out.println("/*--------------------------------------------------*/");
		for (String stepDetailsStr : testStepDetails) {
			System.out.println(stepDetailsStr);
		}
		System.out.println("/*--------------------------------------------------*/");
	}
}
