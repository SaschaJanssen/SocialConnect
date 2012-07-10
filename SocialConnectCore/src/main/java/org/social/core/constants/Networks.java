package org.social.core.constants;

import java.sql.Timestamp;

import org.social.core.util.UtilDateTime;

public enum Networks {
	FACEBOOK {
		@Override
		public String convertTimestampToNetworkTime(Timestamp timestamp) {
			return UtilDateTime.connvertTimestampToFacebookTime(timestamp);
		}
	}, TWITTER {
		@Override
		public String convertTimestampToNetworkTime(Timestamp timestamp) {
			return UtilDateTime.connvertTimestampToTwitterTime(timestamp);
		}
	};

	public String getName() {
		return this.name();
	}

	public boolean isNetwork(String networkName) {
		return this.name().equals(networkName);
	}

	public abstract String convertTimestampToNetworkTime(Timestamp timestamp);
}
