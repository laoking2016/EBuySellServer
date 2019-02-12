package com.greyu.ysj.entity;

import java.sql.Timestamp;

public class Message {
	
	private Integer id;
	private Integer sender;
	private Integer receiver;
	private Timestamp messageTime;
	private String title;
	private String message;
	private Boolean readFlag;
	private Integer replyId;
	private Boolean replyFlag;
	private String senderName;
	private String receiverName;
	
	public Boolean getReplyFlag() {
		return replyFlag;
	}
	public void setReplyFlag(Boolean replyFlag) {
		this.replyFlag = replyFlag;
	}
	
	public String getSenderName() {
		return senderName;
	}
	public void setSenderName(String senderName) {
		this.senderName = senderName;
	}
	public String getReceiverName() {
		return receiverName;
	}
	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getReplyId() {
		return replyId;
	}
	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}
	public Boolean getReadFlag() {
		return readFlag;
	}
	public void setReadFlag(Boolean readFlag) {
		this.readFlag = readFlag;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getSender() {
		return sender;
	}
	public void setSender(Integer sender) {
		this.sender = sender;
	}
	public Integer getReceiver() {
		return receiver;
	}
	public void setReceiver(Integer receiver) {
		this.receiver = receiver;
	}
	public Timestamp getMessageTime() {
		return messageTime;
	}
	public void setMessageTime(Timestamp messageTime) {
		this.messageTime = messageTime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
}
