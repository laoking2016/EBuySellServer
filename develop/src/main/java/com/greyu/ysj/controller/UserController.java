package com.greyu.ysj.controller;

import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.authorization.manager.TokenManager;
import com.greyu.ysj.authorization.model.TokenModel;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.User;
import com.greyu.ysj.mapper.UserMapper;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.UserService;
import com.greyu.ysj.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 21:50 2018/2/8.
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/admin/v1/user", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> getUsers(Integer page, Integer rows, String orderBy, User user) {

        List<User> users = this.userService.getAllUsers(page, rows, user);
        return new ResponseEntity<>(ResultModel.ok(users), HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> getUser(@PathVariable Integer userId) {
        User user = this.userService.selectUserById(userId);
        user.setPassWord(null);
        if (null == user) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(ResultModel.ok(user), HttpStatus.OK);
    }

    
    @RequestMapping(value = "/admin/v1/user/{userId}", method = RequestMethod.DELETE)
    @Authorization
    public ResponseEntity<ResultModel> deleteUser(@PathVariable Integer userId) {
        ResultModel resultModel = this.userService.deleteUser(userId);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> updateUser(@PathVariable Integer userId, User user) {
        ResultModel resultModel = this.userService.updateUser(userId, user);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/user/v1/user/{userId}/avatar", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> uploadAvatar(@PathVariable Integer userId, MultipartFile avatar) {
        if (null == avatar) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.IMAGE_NOT_EMPTY), HttpStatus.BAD_REQUEST);
        }

        String fileName = "";
        try {
            fileName = FileUtil.upload(avatar, Constants.IMAGE_SAVE_PATH) ;
        } catch (Exception err) {
            err.printStackTrace();
        }

        ResultModel resultModel = this.userService.uploadAvatar(userId, fileName);

        if (resultModel.getCode() < 0) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }
    
    
    /*********************************************************/
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Autowired
    private TokenManager tokenManager;
    
    @RequestMapping(value = "/user/v2/user/wechat/login", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> wechatLogin(@RequestParam("openId") String openId, @RequestParam("accessToken") String accessToken){
    	
    	/*WechatApiResult wechatResult = 
    			restTemplate.getForObject(String.format("https://api.weixin.qq.com/sns/auth?access_token=%s&openid=%s", accessToken, openId), WechatApiResult.class);
    	
    	if(wechatResult.getErrcode() != 0) {
    		return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_OR_PASSWORD_ERROR), HttpStatus.NOT_FOUND);
    	}*/
    	
    	User user = this.userMapper.findByOpenId(openId);
    	
    	if(user == null) {
    		return new ResponseEntity<>(ResultModel.error(ResultStatus.USER_NOT_FOUND), HttpStatus.NOT_FOUND);
    	}
    	
    	// 生成一个token，保存用户登录状态
        TokenModel model = this.tokenManager.createToken(user.getUserId());
        model.setRole(user.getRole());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.OK);
    }
    
    @RequestMapping(value= "/user/v2/wechat/user", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> addWechatUser(@RequestBody User user) {
    	
    	User userDb = this.userMapper.findByOpenId(user.getOpenId());
    	
    	if(userDb == null) {
    		this.userMapper.insert(user);
    		user = this.userMapper.findByOpenId(user.getOpenId());
    	}else {
    		user = userDb;
    	}
    	        
        TokenModel model = this.tokenManager.createToken(user.getUserId());
        model.setRole(user.getRole());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.CREATED);
    }
    
    @RequestMapping(value= "/user/v2/user", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> addUser(@RequestBody User user) {
    	
    	if(user.getOpenId() != null && this.userMapper.findByName(user.getUserName()) != null){
    		return new ResponseEntity<>(ResultModel.error(ResultStatus.USERNAME_HAS_EXISTS), HttpStatus.CREATED);
    	}
    	
        this.userMapper.insert(user);
        
        if(user.getOpenId() != null) {
        	user = this.userMapper.findByOpenId(user.getOpenId());
        }else {
        	user = this.userMapper.findByName(user.getUserName());
        }
        
        TokenModel model = this.tokenManager.createToken(user.getUserId());
        model.setRole(user.getRole());
        return new ResponseEntity<>(ResultModel.ok(model), HttpStatus.CREATED);
    }
    
    @RequestMapping(value= "/user/v2/user", method = RequestMethod.PUT)
    public ResponseEntity<ResultModel> updateUser(@RequestBody User user) {
    	
    	User userDb = this.userMapper.findById(user.getUserId());
    	
    	if(userDb != null) {
    		userDb.setPhone(user.getPhone());
    		userDb.setSex(user.getSex());
    		userDb.setNickName(user.getNickName());
    		userDb.setBirth(user.getBirth());
    		userDb.setEmail(user.getEmail());
    		userDb.setAddress(user.getAddress());
    		this.userMapper.update(userDb);
    	}
        
    	return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/user/{userId}", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> findById(@PathVariable Integer userId) {
        
    	User user = userMapper.findById(userId);

    	return new ResponseEntity<ResultModel>(ResultModel.ok(user), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/users/supplier", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> findBySupplier(HttpServletRequest request) {
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	List<User> users = userMapper.findBySupplier(currentUserId);

    	return new ResponseEntity<ResultModel>(ResultModel.ok(users), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/users", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> findAll(HttpServletRequest request) {

    	List<User> users = userMapper.findAll();
    	return new ResponseEntity<ResultModel>(ResultModel.ok(users), HttpStatus.OK);
    }
    
    
}
