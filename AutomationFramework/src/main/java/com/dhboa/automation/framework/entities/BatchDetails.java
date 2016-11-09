package com.dhboa.automation.framework.entities;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import com.dhboa.automation.framework.components.AbstractEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class BatchDetails extends AbstractEntity{

	enum EntityType{
		TESTSUITE, TESTCASE
	}
	
	@Enumerated(EnumType.STRING)
	private EntityType entityType;
	
	@ManyToOne
	@JsonIgnore
	Batch batch;

	public EntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(EntityType entityType) {
		this.entityType = entityType;
	}
 

 
}
