package com.greyu.ysj.service;

import java.util.List;

import com.greyu.ysj.entity.Message;

public interface MessageService {
	List<Message> findByUserId(Integer userId, Integer page);
}
