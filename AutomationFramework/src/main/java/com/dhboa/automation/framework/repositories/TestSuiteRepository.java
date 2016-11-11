package com.dhboa.automation.framework.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;

@Repository
public interface TestSuiteRepository extends JpaRepository<TestSuite, String>{

	Page<TestSuite> findBySuiteNameContainingAndProjectAndUserAndIsActive(Pageable page, String suiteName,
			Project project, User loggedInUser, boolean b);

}
