package com.dhboa.automation.framework.entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
public class Batch extends AbstractEntity{
	
	private String status;
	
	@OneToMany(mappedBy="batch")
	List<BatchDetails> batchDetails;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public List<BatchDetails> getBatchDetails() {
		return batchDetails;
	}

	public void setBatchDetails(List<BatchDetails> batchDetails) {
		this.batchDetails = batchDetails;
	}

	
	
}
