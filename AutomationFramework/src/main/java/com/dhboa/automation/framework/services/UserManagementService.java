package com.dhboa.automation.framework.services;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.User;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserManagementService extends AutowiredUtilObjects{

	public void changePassword(User user, String newPassword){
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		User userDetails = userRep.findOne(user.getUserName());
		
	if(user.getUserName() == commonUtil.getLoggedInUser().getUserName()){
		if(passwordEncoder.matches(user.getPassword(), userDetails.getPassword())){
		userDetails.setPassword(passwordEncoder.encode(newPassword));
		userRep.save(userDetails);
		}
	}
	}
	
	public List<Project> getProjects(){
List<Project> projects = userProjRep.findProjects(commonUtil.getLoggedInUser());
return projects;
	}
}
