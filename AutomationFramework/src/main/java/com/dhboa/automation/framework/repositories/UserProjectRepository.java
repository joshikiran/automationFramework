package com.dhboa.automation.framework.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dhboa.automation.framework.entities.Project;
import com.dhboa.automation.framework.entities.User;
import com.dhboa.automation.framework.entities.UserProject;

public interface UserProjectRepository extends JpaRepository<UserProject, String>{
	@Query("select u.project from UserProject u where u.user= :user")
	public List<Project> findProjects(@Param("user") User user);
}
