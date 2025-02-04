package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.authorization.annotation.CurrentUserId;
import com.greyu.ysj.authorization.manager.TokenManager;
import com.greyu.ysj.authorization.model.TokenModel;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.User;
import com.greyu.ysj.mapper.UserMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by greyu on 2018/2/2.
 */
@RestController
@RequestMapping("/user/v1/tokens")
public class UserTokenController {
    @Autowired
    private UserService userService;

    @Autowired
    private TokenManager tokenManager;
    
    @Autowired
    private UserMapper userMapper;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<ResultModel> login(String userName, String passWord) {
    	
        if (null == userName || null == passWord) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        User user = this.userMapper.findByName(userName);

        if (null == user || // 未注册
                !user.getPassWord().equals(passWord)) { //密码错误
            // 提示用户名或者密码错误
            return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
        }
        // 生成一个token，保存用户登录状态
        TokenModel model = this.tokenManager.createToken(user.getUserId());
        model.setRole(user.getRole());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> logout(@CurrentUserId Integer userId) {
        
        this.tokenManager.deleteToken(userId);
        return new ResponseEntity<>(ResultModel.ok(), HttpStatus.OK);
    }
}
