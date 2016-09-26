package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity(name="af_variables")
public class Variable extends AbstractEntity{

	@Column
	private String variableName;
	
	@Column
	private String variableValue;
	
	@ManyToOne
	private TestSuite testSuite;
	
	@ManyToOne
	private TestCase testCase;
	
	@ManyToOne
	private User user;
	
	@ManyToOne
	private Project project;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public TestSuite getTestSuite() {
		return testSuite;
	}

	public void setTestSuite(TestSuite testSuite) {
		this.testSuite = testSuite;
	}

	public TestCase getTestCase() {
		return testCase;
	}

	public void setTestCase(TestCase testCase) {
		this.testCase = testCase;
	}

	public String getVariableName() {
		return variableName;
	}

	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}

	public String getVariableValue() {
		return variableValue;
	}

	public void setVariableValue(String variableValue) {
		this.variableValue = variableValue;
	}
		
}
