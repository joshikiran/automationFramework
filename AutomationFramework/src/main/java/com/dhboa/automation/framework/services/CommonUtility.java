package com.dhboa.automation.framework.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.User;

@Service
public class CommonUtility extends AutowiredUtilObjects{

	public static final String IN_PROGRESS_STATUS = "INPROGRESS";
	public static final String SUCCESS_STATUS = "SUCCESS";
	public static final String FAILED_STATUS = "FAILED";
	
	/**
	 * @return
	 */
	public User getLoggedInUser(){
		String user = SecurityContextHolder.getContext().getAuthentication().getName();
		return userRep.findOne(user);
	}
}
