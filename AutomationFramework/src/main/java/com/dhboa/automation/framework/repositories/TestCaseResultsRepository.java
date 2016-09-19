package com.dhboa.automation.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.TestCaseResults;

@Repository
public interface TestCaseResultsRepository extends JpaRepository<TestCaseResults, String> {

}
