package org.social.entity.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "MESSAGES")
public class Messages {

	private Long messageId;
	private String message;
	private Long fromUserId;
	private String language;
	private String network;
	private Timestamp networkMessageDate;
	private Timestamp messageReceivedDate;
	private Long customerId;
	private Long craftedStateId;

	@Id
	@GeneratedValue(generator = "increment")
	@GenericGenerator(name = "increment", strategy = "increment")
	@Column(name = "MESSAGE_ID")
	public Long getMessageId() {
		return messageId;
	}

	public void setMessageId(Long messageId) {
		this.messageId = messageId;
	}

	@Column(name = "MESSAGE")
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "FROM_USER_ID")
	public Long getFromUserId() {
		return fromUserId;
	}

	public void setFromUserId(Long fromUserId) {
		this.fromUserId = fromUserId;
	}

	@Column(name = "LANGUAGE")
	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	@Column(name = "NETWORK")
	public String getNetwork() {
		return network;
	}

	public void setNetwork(String network) {
		this.network = network;
	}

	@Column(name = "NETWORK_MESSAGE_DATE")
	public Timestamp getNetworkMessageDate() {
		return networkMessageDate;
	}

	public void setNetworkMessageDate(Timestamp networkMessageDate) {
		this.networkMessageDate = networkMessageDate;
	}

	@Column(name = "MESSAGE_RECEIVED_DATE")
	public Timestamp getMessageReceivedDate() {
		return messageReceivedDate;
	}

	public void setMessageReceivedDate(Timestamp messageReceivedDate) {
		this.messageReceivedDate = messageReceivedDate;
	}

	@Column(name = "CUSTOMER_ID")
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	@Column(name = "CRAFTED_STATE_ID")
	public Long getCraftedStateId() {
		return craftedStateId;
	}

	public void setCraftedStateId(Long craftedStateId) {
		this.craftedStateId = craftedStateId;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[");
		builder.append("messageId=");
		builder.append(messageId);
		builder.append(",message=");
		builder.append(message);
		builder.append("fromUserId=");
		builder.append(fromUserId);
		builder.append(",language=");
		builder.append(language);
		builder.append("network=");
		builder.append(network);
		builder.append(",networkMessageDate=");
		builder.append(networkMessageDate);
		builder.append("messageReceivedDate=");
		builder.append(messageReceivedDate);
		builder.append(",customerId=");
		builder.append(customerId);
		builder.append(",craftedState=");
		builder.append(craftedStateId);
		builder.append("]");

		return builder.toString();
	}
}
