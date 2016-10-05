package com.dhboa.automation.framework.components;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public class AbstractEntity {
	@Id
	@Column(name = "id", nullable = false, length = 200)
	private String id;

	@Column(name = "created_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createdOn;

	@Column(name = "created_by", length = 200)
	private String createdBy;

	@Column(name = "modified_on")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modifiedOn;

	@Column(name = "modified_by", length = 200)
	private String modifiedBy;

	@Column(name = "additional1", length = 500)
	private String additional1;

	@Column(name = "additional2", length = 500)
	private String additional2;

	@Column(name = "additional3", length = 500)
	private String additional3;

	@Column(name = "additional4", length = 500)
	private String additional4;

	@Column(name = "additional5", length = 500)
	private String additional5;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(Date createdOn) {
		this.createdOn = createdOn;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(Date modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getModifiedBy() {
		return modifiedBy;
	}

	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}

	public String getAdditional1() {
		return additional1;
	}

	public void setAdditional1(String additional1) {
		this.additional1 = additional1;
	}

	public String getAdditional2() {
		return additional2;
	}

	public void setAdditional2(String additional2) {
		this.additional2 = additional2;
	}

	public String getAdditional3() {
		return additional3;
	}

	public void setAdditional3(String additional3) {
		this.additional3 = additional3;
	}

	public String getAdditional4() {
		return additional4;
	}

	public void setAdditional4(String additional4) {
		this.additional4 = additional4;
	}

	public String getAdditional5() {
		return additional5;
	}

	public void setAdditional5(String additional5) {
		this.additional5 = additional5;
	}

	@PreUpdate
	public void onBeforeUpdate() {
		this.setModifiedOn(new Date());
	}
	
	@PrePersist
	public void onBeforePersist(){
		this.setCreatedOn(new Date());
		if(this.id == null && "".equalsIgnoreCase(this.id))
		{
			this.setId(UUID.randomUUID().toString());
		}
	}
}
