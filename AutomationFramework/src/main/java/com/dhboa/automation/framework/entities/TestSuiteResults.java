package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name = "af_test_suite_results")
public class TestSuiteResults extends AbstractEntity{
	
	@ManyToOne
	@JoinColumn(name = "test_suite_id", nullable = true, updatable = true, insertable = true)
	private TestSuite testSuiteId;

	@ManyToOne
	@JoinColumn(name = "project_code", nullable = true, updatable = true, insertable = true)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "username", nullable = true, updatable = true, insertable = true)
	private User user;
	
	@Column(name = "status", length = 500)
	private String status;
	
	@Column(name= "status_description", length = 500)
	private String statusDescription;

	public TestSuite getTestSuiteId() {
		return testSuiteId;
	}

	public void setTestSuiteId(TestSuite testSuiteId) {
		this.testSuiteId = testSuiteId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDescription() {
		return statusDescription;
	}

	public void setStatusDescription(String statusDescription) {
		this.statusDescription = statusDescription;
	}		
}
