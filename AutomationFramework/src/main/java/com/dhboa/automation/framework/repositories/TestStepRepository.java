package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.User;

@Repository
public interface TestStepRepository extends JpaRepository<TestStep, String> {

	List<TestStep> findByTestCaseAndProjectAndUserAndIsActiveOrderByOrderAsc(TestCase testCaseId, Project project, User user,
			boolean isActive);
}
