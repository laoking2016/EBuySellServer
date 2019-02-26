package com.greyu.ysj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greyu.ysj.entity.Message;

public interface MessageMapper {
	
	Message findById(Integer id);
	List<Message> findByUserId(Integer userId);
	List<Message> findByUserIdPaged(@Param("userId") Integer userId, @Param("offset") Integer offset, @Param("pageSize") Integer pageSize);
	void insert(Message message);
	void update(Message message);
}
