package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name = "af_test_step_results")
public class TestStepResults extends AbstractEntity {
	
	@ManyToOne
	@JoinColumn(name = "test_case_result_id", nullable = true, updatable = true, insertable = true)
	private TestCaseResults testCaseResultsId;

	@ManyToOne
	@JoinColumn(name = "test_step_id", nullable = true, updatable = true, insertable = true)
	private TestStep stepId;
	
	@Column(name = "status", length = 500)
	private String status;

	@Column(name = "status_description", length = 500)
	private String statusDescription;

	@Column(name = "field_order")
	private int order;

	public TestCaseResults getTestCaseResultsId() {
		return testCaseResultsId;
	}

	public void setTestCaseResultsId(TestCaseResults testCaseResultsId) {
		this.testCaseResultsId = testCaseResultsId;
	}

	public TestStep getStepId() {
		return stepId;
	}

	public void setStepId(TestStep stepId) {
		this.stepId = stepId;
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

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}	
}
