package com.greyu.ysj.entity;

import java.util.Date;

public class Question {
	
	private Integer id;
	private String question;
	private String answer;
	private Integer goodId;
	private Integer questionUser;
	private Integer answerUser;
	private Date questionDate;
	private Date answerDate;
	private String nickName;
	private String avatar;
	
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getAvatar() {
		return avatar;
	}
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getQuestion() {
		return question;
	}
	public void setQuestion(String question) {
		this.question = question;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public Integer getGoodId() {
		return goodId;
	}
	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}
	public Integer getQuestionUser() {
		return questionUser;
	}
	public void setQuestionUser(Integer questionUser) {
		this.questionUser = questionUser;
	}
	public Integer getAnswerUser() {
		return answerUser;
	}
	public void setAnswerUser(Integer answerUser) {
		this.answerUser = answerUser;
	}
	public Date getQuestionDate() {
		return questionDate;
	}
	public void setQuestionDate(Date questionDate) {
		this.questionDate = questionDate;
	}
	public Date getAnswerDate() {
		return answerDate;
	}
	public void setAnswerDate(Date answerDate) {
		this.answerDate = answerDate;
	}
	
}
