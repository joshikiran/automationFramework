package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "af_configuration")
public class Configuration extends AbstractEntity {

	@Column(name = "param_code")
	private String paramCode;

	@Column(name = "param_value", length = 500)
	private String paramValue;

	@JsonProperty
	@Column(name = "active")
	private boolean isActive;

	@Column(name = "param_group", length = 500)
	private String paramGroup;

	public String getParamCode() {
		return paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamValue() {
		return paramValue;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getParamGroup() {
		return paramGroup;
	}

	public void setParamGroup(String paramGroup) {
		this.paramGroup = paramGroup;
	}	
}
