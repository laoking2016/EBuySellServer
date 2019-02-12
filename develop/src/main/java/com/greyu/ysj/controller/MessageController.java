package com.greyu.ysj.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.entity.Message;
import com.greyu.ysj.entity.Question;
import com.greyu.ysj.mapper.MessageMapper;
import com.greyu.ysj.model.ResultModel;

@RestController
public class MessageController {
	
	@Autowired
	private MessageMapper messageMapper;
	
	@RequestMapping(value = "/user/v2/message/{id}", method = RequestMethod.GET)
    @Authorization
	public ResponseEntity<ResultModel> findById(@PathVariable Integer id, HttpServletRequest request){
		
		int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	Message message = messageMapper.findById(id);
    	
    	if(currentUserId == message.getReceiver()) {
    		message.setReadFlag(true);
        	messageMapper.update(message);
    	}
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(message), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/v2/user/messages", method = RequestMethod.GET)
    @Authorization
	public ResponseEntity<ResultModel> findByUserId(HttpServletRequest request){
		
		int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	List<Message> messages = messageMapper.findByUserId(currentUserId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(messages), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/user/v2/message", method = RequestMethod.POST)
    @Authorization
	public ResponseEntity<ResultModel> submitMessage(@RequestBody Message message, HttpServletRequest request){
		int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	Date now = new Date();
    	
    	message.setSender(currentUserId);
    	message.setMessageTime(new Timestamp(now.getTime()));
    	message.setReadFlag(false);
    	message.setReplyFlag(false);
    	messageMapper.insert(message);
    	
    	Message replyMessage = messageMapper.findById(message.getReplyId());
    	if(replyMessage != null) {
    		replyMessage.setReplyFlag(true);
    		messageMapper.update(replyMessage);
    	}
    	   	
    	return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
	}
}
