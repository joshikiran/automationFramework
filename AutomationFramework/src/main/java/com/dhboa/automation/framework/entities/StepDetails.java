package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "af_step_details")
public class StepDetails extends AbstractEntity {

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "test_step_id", nullable = false, updatable = true, insertable = true)
	private TestStep testStep;

	@Column(name = "param_name", length = 500)
	private String paramName;

	@Column(name = "param_description", length = 500)
	private String paramDescription;

	@Column(name = "field_value", length = 500)
	private String fieldValue;

	@Column(name = "field_order")
	private int order;

	@Column(name = "class_name", length = 500)
	private String className;

	@Column(name = "enabled")
	private boolean isActive;

	public TestStep getTestStep() {
		return testStep;
	}

	public void setTestStep(TestStep testStep) {
		this.testStep = testStep;
	}

	public String getParamName() {
		return paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamDescription() {
		return paramDescription;
	}

	public void setParamDescription(String paramDescription) {
		this.paramDescription = paramDescription;
	}
	
	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
