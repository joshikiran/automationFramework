package com.dhboa.automation.framework.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;


import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "af_methods")
public class Method extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "project_code", nullable = false, updatable = true, insertable = true)
	private Project project;

	@Column(name = "method_name", length = 500)
	private String methodName;

	@Column(name = "method_description", length = 500)
	private String methodDescription;

	@Column(name = "enabled")
	private boolean isActive;

	@OneToMany(mappedBy="method", fetch=FetchType.EAGER)
	@OrderBy("order")
	List<MethodParams> params;
	
	@JsonIgnore
	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodDescription() {
		return methodDescription;
	}

	public void setMethodDescription(String methodDescription) {
		this.methodDescription = methodDescription;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public List<MethodParams> getParams() {
		return params;
	}

	public void setParams(List<MethodParams> params) {
		this.params = params;
	}
	
}
