package com.dhboa.automation.framework.components;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.object.StoredProcedure;

public class AfStoredProcedure extends StoredProcedure {

	
	
	public AfStoredProcedure(DataSource dataSource, String nameOfTheProcedure){
		super(dataSource, nameOfTheProcedure);
	}
}
