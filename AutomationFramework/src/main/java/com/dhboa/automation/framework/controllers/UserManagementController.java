package com.dhboa.automation.framework.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dhboa.automation.framework.components.AutowiredUtilObjects;
import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.User;

@RestController
@RequestMapping("/usermanagement")
public class UserManagementController extends AutowiredUtilObjects{

	@RequestMapping(value="/changePassword")
	public void changePassword(@RequestBody User user, @RequestParam(value="newPassword")String newPassword){
		userServ.changePassword(user, newPassword);
	}
	
	@RequestMapping(value="/getProjects")
	public List<Project> getProjects(){
		return userServ.getProjects();
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public User getUserInfo(Principal user) {
		return userRep.findOne(user.getName());
	}
	
}
