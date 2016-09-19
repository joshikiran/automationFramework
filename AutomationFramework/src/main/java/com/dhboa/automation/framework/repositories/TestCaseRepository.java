package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {

	List<TestCase> findByTestSuiteAndProjectAndUserAndIsActiveOrderByOrderAsc(TestSuite id,
			Project projectCode, User userName, boolean isActive);
}
