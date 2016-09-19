package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name = "af_test_case_results")
public class TestCaseResults extends AbstractEntity {

	@ManyToOne
	@JoinColumn(name = "test_case_id", nullable = true, updatable = true, insertable = true)
	private TestCase testCaseId;

	@ManyToOne
	@JoinColumn(name = "test_suite_result_id", nullable = true, updatable = true, insertable = true)
	private TestSuiteResults suiteResultId;

	@Column(name = "status", length = 500)
	private String status;

	@Column(name = "status_description", length = 500)
	private String statusDescription;

	@Column(name = "field_order")
	private int order;

	public TestCase getTestCaseId() {
		return testCaseId;
	}

	public void setTestCaseId(TestCase testCaseId) {
		this.testCaseId = testCaseId;
	}

	public TestSuiteResults getSuiteResultId() {
		return suiteResultId;
	}

	public void setSuiteResultId(TestSuiteResults suiteResultId) {
		this.suiteResultId = suiteResultId;
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
