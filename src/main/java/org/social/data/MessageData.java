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
	private String network;
	private String networkMessageDate;
	private String messageReceivedDate;
	private String customerId;

	public MessageData(String network) {
		this.network = network;
	}

	public String getNetwork() {
		return network;
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

	public String getNetworkMessageDate() {
		return networkMessageDate;
	}

	public void setNetworkMessageDate(String networkMessageDate) {
		this.networkMessageDate = networkMessageDate;
	}

	public String getMessageReceivedDate() {
		return messageReceivedDate;
	}

	public void setMessageReceivedDate(String messageReceiveDate) {
		this.messageReceivedDate = messageReceiveDate;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public JSON toJson() {
		return JSONSerializer.toJSON(this);
	}
}
