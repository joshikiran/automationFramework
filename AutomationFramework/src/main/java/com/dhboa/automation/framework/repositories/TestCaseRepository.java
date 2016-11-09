package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestSuite;
import com.dhboa.automation.framework.entities.User;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, String> {

	List<TestCase> findByTestSuiteAndProjectAndUserAndIsActiveOrderByOrderAsc(TestSuite id,
			Project projectCode, User userName, boolean isActive);
	
	Page<TestCase> findByTestCaseNameContainingAndTestSuite_IdLikeAndProjectAndUserAndIsActive(Pageable page, String testCaseName, String testSuiteId,
			Project projectCode, User userName, boolean isActive);
	
	@Query(value ="select field_order from af_test_case  where test_suite_id = ?1 and project_code = ?2 and username= ?3 order by field_order desc limit 1", nativeQuery=true)
	public Integer findHighestOrderTestCaseInTestSuite(String id,
			String projectCode, String userName);


	@Override
	public Page<TestCase> findAll(Pageable  page);
}
