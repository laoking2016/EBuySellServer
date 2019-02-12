package com.greyu.ysj.mapper;

import java.util.List;

import com.greyu.ysj.entity.Message;

public interface MessageMapper {
	
	Message findById(Integer id);
	List<Message> findByUserId(Integer userId);
	void insert(Message message);
	void update(Message message);
}
