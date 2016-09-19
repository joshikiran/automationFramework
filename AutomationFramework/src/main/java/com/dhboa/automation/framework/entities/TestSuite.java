package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name = "af_test_suite")
public class TestSuite extends AbstractEntity {

	@Column(name = "suite_name", length = 500)
	private String suiteName;

	@Column(name = "suite_description", length = 500)
	private String suiteDescription;

	@ManyToOne
	@JoinColumn(name = "project_name", nullable = false, updatable = true, insertable = true)
	private Project project;
	
	@ManyToOne
	@JoinColumn(name = "username", nullable = false, updatable = true, insertable = true)
	private User user;
	
	@Column(name = "enabled")
	private boolean isActive;
	
	@Column(name = "test_suite_reference")
	private String testSuiteReference;

	public String getSuiteName() {
		return suiteName;
	}

	public void setSuiteName(String suiteName) {
		this.suiteName = suiteName;
	}

	public String getSuiteDescription() {
		return suiteDescription;
	}

	public void setSuiteDescription(String suiteDescription) {
		this.suiteDescription = suiteDescription;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getTestSuiteReference() {
		return testSuiteReference;
	}

	public void setTestSuiteReference(String testSuiteReference) {
		this.testSuiteReference = testSuiteReference;
	}	
}
