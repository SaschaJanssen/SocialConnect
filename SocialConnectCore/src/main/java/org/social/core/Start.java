package org.social.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.social.core.util.UtilProperties;

public class Start {

	private static Logger logger = LoggerFactory.getLogger(Start.class);

	public static void main(String[] args) {

		long HOUR_IN_MILLISEC = UtilProperties.getPropertyValueAsLong("conf/social.properties", "schedule.period",
				3600000);
		ApplicationScheduler appScheduler = new ApplicationScheduler(HOUR_IN_MILLISEC);

		appScheduler.schedule();

		if (logger.isDebugEnabled()) {
			logger.debug("Application scheduled.");
		}

	}

}
