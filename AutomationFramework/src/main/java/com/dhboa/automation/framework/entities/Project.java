package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "af_projects")
public class Project {

	@Id
	@Column(name = "project_code", length = 200)
	private String projectCode;

	@Column(name = "project_description", length = 500)
	private String projectDescription;

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public String getProjectDescription() {
		return projectDescription;
	}

	public void setProjectDescription(String projectDescription) {
		this.projectDescription = projectDescription;
	}
}
