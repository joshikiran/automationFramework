package com.dhboa.automation.framework.entities;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;

@Entity
@Table(name = "af_activity_log")
public class ActivityLog extends AbstractEntity{
		
	@Column(name = "module_name", length = 500)
	private String moduleName;

	@Column(name = "activity_name", length = 500)
	private String activityName;

	@Column(name = "status", length = 500)
	private String status;

	@Column(name = "status_message", length = 500)
	private String StatusMessage;		

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusMessage() {
		return StatusMessage;
	}

	public void setStatusMessage(String statusMessage) {
		StatusMessage = statusMessage;
	}

	@PrePersist
	public void onBeforeInsert() {
		if (this.getId() == null || "".equals(this.getId()))
			this.setId(UUID.randomUUID().toString());
	}
}
