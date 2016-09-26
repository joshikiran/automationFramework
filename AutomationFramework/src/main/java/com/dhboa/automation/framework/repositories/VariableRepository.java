package com.dhboa.automation.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.entities.Variable;

@Repository
public interface VariableRepository extends JpaRepository<Variable, String> {

	Variable findByVariableName(String variableName);

	public Variable findByVariableNameAndProjectAndUserAndTestSuiteAndTestCase(String variableName,Project projectCode,User userName,TestSuite testSuiteId,TestCase testCaseId );
}
