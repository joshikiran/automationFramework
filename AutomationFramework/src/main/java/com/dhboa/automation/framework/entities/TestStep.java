package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name="af_test_step")
public class TestStep extends AbstractEntity{
	
	@ManyToOne
	@JoinColumn(name = "test_case_id", nullable = true, updatable = true, insertable = true)
	private TestCase testCase;

	@ManyToOne
	@JoinColumn(name = "project_code", nullable = true, updatable = true, insertable = true)
	private Project project;

	@ManyToOne
	@JoinColumn(name = "username", nullable = true, updatable = true, insertable = true)
	private User user;

	@Column(name = "enabled")
	private boolean isActive;
	
	@Column(name = "field_order")
	private int order;
	
	@Column(name="test_case_name", length = 500)
	private String testCaseName;
	
	@Column(name="test_case_description", length = 500)
	private String testCaseDescription;
	
	@Column(name = "test_step_reference")
	private String testStepRef;

	public TestCase getTestCase() {
		return testCase;
	}

	public String getTestStepRef() {
		return testStepRef;
	}

	public void setTestStepRef(String testStepRef) {
		this.testStepRef = testStepRef;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getTestCaseName() {
		return testCaseName;
	}

	public void setTestCaseName(String testCaseName) {
		this.testCaseName = testCaseName;
	}

	public String getTestCaseDescription() {
		return testCaseDescription;
	}

	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}
		
}
