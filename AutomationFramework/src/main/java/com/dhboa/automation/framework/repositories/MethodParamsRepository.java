package com.dhboa.automation.framework.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.MethodParams;

@Repository
public interface MethodParamsRepository extends JpaRepository<MethodParams, String> {
	
}
