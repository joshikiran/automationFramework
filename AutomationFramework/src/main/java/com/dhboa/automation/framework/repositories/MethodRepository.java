package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dhboa.automation.framework.entities.Method;

@Repository
public interface MethodRepository extends JpaRepository<Method, String> {

	List<Method>findByMethodNameContaining(String methodName);
}
