package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.TestCase;
import com.dhboa.automation.framework.entities.TestStep;
import com.dhboa.automation.framework.entities.User;

@Repository
public interface TestStepRepository extends JpaRepository<TestStep, String> {

	List<TestStep> findByTestCaseAndProjectAndUserAndIsActiveOrderByOrderAsc(TestCase testCaseId, Project project, User user,
			boolean isActive);

	Page<TestStep> findDistinctTestStepByStepDetails_fieldValueContainingAndTestCase_IdLikeAndAndProjectAndUserAndIsActive(Pageable page,String stepDetails, String testCaseId, 
			Project project, User user, boolean isActive);

	@Query(value ="select field_order from af_test_step  where test_case_id = ?1 and project_code = ?2 and username= ?3 order by field_order desc limit 1", nativeQuery=true)
	Integer findHighestOrderTestStepInTestCase(String id, String projectCode, String userName);
}
