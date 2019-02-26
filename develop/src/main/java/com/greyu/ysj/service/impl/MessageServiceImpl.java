package com.greyu.ysj.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.entity.Message;
import com.greyu.ysj.mapper.MessageMapper;
import com.greyu.ysj.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
	
	@Autowired
	private MessageMapper messageMapper;
	
	public List<Message> findByUserId(Integer userId, Integer page){
		if(page != null) {
			PageHelper.startPage(page, Constants.PAGE_SIZE);
		}
		return messageMapper.findByUserId(userId);
	}
}
