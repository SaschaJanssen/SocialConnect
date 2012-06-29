package org.social.data;

import net.sf.json.JSON;
import net.sf.json.JSONSerializer;

public class MessageData {
	private String id;
	private String message;
	private String fromUser;
	private String fromUserId;
	private String language;
	private String geoLocation;
	private String platform;
	private String plattformMessageDate;
	private String messageReceiveDate;

	public MessageData(String platform) {
		this.platform = platform;
	}

	public String getPlatform() {
		return platform;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getFromUser() {
		return fromUser;
	}

	public String getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}

	public void setFromUser(String fromUser) {
		this.fromUser = fromUser;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getGeoLocation() {
		return geoLocation;
	}

	public void setGeoLocation(String locale) {
		this.geoLocation = locale;
	}

	public String getPlattformMessageDate() {
		return plattformMessageDate;
	}

	public void setPlattformMessageDate(String messageSendDate) {
		this.plattformMessageDate = messageSendDate;
	}

	public String getMessageReceiveDate() {
		return messageReceiveDate;
	}

	public void setMessageReceiveDate(String messageReceiveDate) {
		this.messageReceiveDate = messageReceiveDate;
	}

	public JSON toJson() {
		return JSONSerializer.toJSON(this);
	}
}
