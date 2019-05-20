package com.greyu.ysj.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.greyu.ysj.entity.Configuration;
import com.greyu.ysj.mapper.ConfigurationMapper;
import com.greyu.ysj.model.ResultModel;

@RestController
public class ConfigurationController {
	
	@Autowired
	private ConfigurationMapper configurationMapper;
	
	 @RequestMapping(value = "/user/v2/configuration", method = RequestMethod.GET)
	public ResponseEntity<ResultModel> getConfiguration() {
		Configuration configuration = this.configurationMapper.find();
		
		return new ResponseEntity<ResultModel>(ResultModel.ok(configuration), HttpStatus.OK);
	}
}
