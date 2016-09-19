package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.StepDetails;
import com.dhboa.automation.framework.entities.TestStep;

@Repository
public interface TestStepDetailsRepository extends JpaRepository<StepDetails, String> {
	
	List<StepDetails> findByTestStepAndIsActiveOrderByOrderAsc(TestStep testStepId, boolean isActive);
}
