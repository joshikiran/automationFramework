package com.dhboa.automation.framework.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "af_method_params")
public class MethodParams extends AbstractEntity {

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "method_name", nullable = false, updatable = true, insertable = true)
	private Method method;

	@Column(name = "param_name", length = 500)
	private String paramName;

	@Column(name = "param_description", length = 500)
	private String paramDescription;

	@Column(name = "enabled")
	private boolean isActive;

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
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

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
}
