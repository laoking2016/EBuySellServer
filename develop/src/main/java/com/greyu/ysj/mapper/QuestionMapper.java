package com.greyu.ysj.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.greyu.ysj.entity.Question;

public interface QuestionMapper {
	
	
	List<Question> selectByGoodId(@Param("goodId") Integer goodId);
	
	/********************************************************/
	List<Question> findByGoodId(Integer goodId);
	void insert(Question question);
	void update(Question question);
}
