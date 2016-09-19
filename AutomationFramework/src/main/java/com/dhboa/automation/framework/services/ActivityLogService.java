package com.dhboa.automation.framework.services;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

/**
 * This class will take care of all the services which deals with logging to the
 * application. This will be needed for the application to have a track of all
 * the activity. Logging will be done based on the configuration present in the
 * application. Levels can also be defined in the configuration and the
 * application should log only based on the level and setting present in the
 * configuration.
 * 
 * @author KiranJoshi
 *
 */
@Service
public class ActivityLogService {

	/**
	 * 
	 * @param moduleName
	 * @param activityName
	 * @param status
	 * @param statusDescription
	 */
	public void log(String logLevel, Logger logger, String moduleName, String activityName, String status,
			String statusDescription) {

	}

	/**
	 * 
	 * 
	 * @param moduleName
	 * @param activityName
	 * @param status
	 * @param statusDescription
	 * @param additional1
	 * @param additional2
	 * @param additional3
	 * @param additional4
	 * @param additional5
	 */
	public void log(String logLevel, Logger logger, String moduleName, String activityName, String status,
			String statusDescription, String additional1, String additional2, String additional3, String additional4,
			String additional5) {

	}

	/**
	 * 
	 * 
	 * @param logger
	 * @param moduleName
	 * @param activityName
	 * @param status
	 * @param statusDescription
	 * @param additional1
	 * @param additional2
	 * @param additional3
	 * @param additional4
	 * @param additional5
	 */
	public void logError(Logger logger, String moduleName, String activityName, String status, String statusDescription,
			String additional1, String additional2, String additional3, String additional4, String additional5) {

	}

	/**
	 * 
	 * @param logger
	 * @param moduleName
	 * @param activityName
	 * @param status
	 * @param statusDescription
	 */
	public void logError(Logger logger, String moduleName, String activityName, String status, String statusDescription,
			Exception e) {

		logger.error("Application encountered exception with details ", e);
	}

	/**
	 * 
	 * @param logger
	 * @param e
	 */
	public void LogError(Logger logger, Exception e) {
		logger.error("Application encountered exception with details ", e);
	}
}
