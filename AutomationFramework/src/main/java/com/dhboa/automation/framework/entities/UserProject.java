package com.dhboa.automation.framework.entities;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="af_user_projects")
public class UserProject extends AbstractEntity{
	
	
	@ManyToOne
	@JoinColumn(name = "username", nullable = false, updatable = true, insertable = true)
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "project_code", nullable = false, updatable = true, insertable = true)
	private Project project;

	@JsonIgnore
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@JsonIgnore
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}	
}
