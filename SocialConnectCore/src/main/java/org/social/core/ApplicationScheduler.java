package org.social.core;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationScheduler {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private final long period;

	private AtomicBoolean shutdownHook;

	ApplicationScheduler(Long period) {
		this.period = period;
		this.shutdownHook = new AtomicBoolean(true);
		this.attachShutdownHook();
	}

	public void schedule() throws Exception {

		final SocialConnect socialConnect = new SocialConnect();

		TimerTask timerTask = new TimerTask() {

			@Override
			public void run() {
				if (logger.isInfoEnabled()) {
					logger.info("Schedule Social Connect Run");
				}
				socialConnect.start();
			}
		};

		Timer tt = new Timer();
		tt.scheduleAtFixedRate(timerTask, new Date(), period);

		while(shutdownHook.get()){
			Thread.sleep(2000);
		}
	}

	private void attachShutdownHook() {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				ApplicationScheduler.this.shutdownHook .set(false);
			}
		});
	}

}
