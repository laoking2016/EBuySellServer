package com.greyu.ysj.controller;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.PageInfo;
import com.greyu.ysj.authorization.annotation.Authorization;
import com.greyu.ysj.config.Constants;
import com.greyu.ysj.config.ResultStatus;
import com.greyu.ysj.entity.Favorite;
import com.greyu.ysj.entity.Good;
import com.greyu.ysj.entity.Order;
import com.greyu.ysj.entity.Question;
import com.greyu.ysj.mapper.FavoriteMapper;
import com.greyu.ysj.mapper.GoodMapper;
import com.greyu.ysj.mapper.OrderMapper;
import com.greyu.ysj.mapper.QuestionMapper;
import com.greyu.ysj.model.GoodModel;
import com.greyu.ysj.model.ResultModel;
import com.greyu.ysj.service.GoodService;

/**
 * @Description:
 * @Author: gre_yu@163.com
 * @Date: Created in 16:33 2018/3/9.
 */
@RestController
public class GoodController {
    @Autowired
    private GoodService goodService;

    @RequestMapping(value = "/admin/v1/goods", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> getAll(Integer page, Integer rows, Good good, Boolean goodStatus) {
        PageInfo<Good> goodList = this.goodService.getAll(page, rows, good, goodStatus);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }


    /**
     * 用户获取在售的 商品 列表， 只能获取 库存 > 0 的商品
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/goods", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getAllGoodsOnShell(Integer page, Integer rows, String orderBy, Good good) {
        System.out.println(page);
        System.out.println(rows);
        System.out.println(orderBy);
        System.out.println(good);
        List<Good> goodList = this.goodService.getAllGoodsOnShell(page, rows, orderBy, good);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }

    /**
     * 获取一条指定 goodId 的商品信息
     * @param goodId
     * @return
     */
    @RequestMapping(value = "/user/v1/goods/{goodId}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getOne(@PathVariable Integer goodId, HttpServletRequest request) {
        Good good = this.goodService.getOne(goodId);

        // 查不到商品
        if (null == good) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.GOOD_NOT_FOUND), HttpStatus.NOT_FOUND);
        }
        
        int currentUserId = -1;
        
        if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
        	currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
        }
        
        GoodModel goodModel = new GoodModel(good, currentUserId);
        return new ResponseEntity<ResultModel>(ResultModel.ok(goodModel), HttpStatus.OK);
    }

    /**
     * 添加商品
     * @param good
     * @return
     */
    @RequestMapping(value = "/user/v1/supplier/goods", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> save(@RequestBody Good good, HttpServletRequest request) {
        
    	int currentUserId = -1;
        
        if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
        	currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
        }
    	
    	good.setType("拍卖");
    	good.setInventory(1);
    	good.setStatus("拍卖中");
        ResultModel resultModel = this.goodService.save(good);

        if (resultModel.getCode() == -1005 || resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.CREATED);
    }

    /**
     * 获取分类的商品信息
     * @param page
     * @param rows
     * @return
     */
    @RequestMapping(value = "/user/v1/categories/{categoryId}/goods", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getCategoryGoods(@PathVariable Integer categoryId, Integer page, Integer rows, Good good) {
        System.out.println(good);

        List<Good> goodList = this.goodService.getCategoryGoods(page, rows, good);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }

    /**
     * 更新商品信息
     * @param goodId
     * @param good
     * @return
     */
    @RequestMapping(value = "/admin/v1/goods/{goodId}", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> updateCategory(@PathVariable Integer goodId, Good good) {
        System.out.println(good);
        ResultModel resultModel = this.goodService.update(goodId, good);

        if (resultModel.getCode() == -1002) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }

    @RequestMapping(value = "/admin/v1/goods/{goodId}/inventory", method = RequestMethod.PATCH)
    @Authorization
    public ResponseEntity<ResultModel> increaseInventory(@PathVariable Integer goodId, Integer inventory) {
        if (null == inventory) {
            return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.DATA_NOT_NULL), HttpStatus.BAD_REQUEST);
        }

        ResultModel resultModel = this.goodService.increaseInventory(goodId, inventory);
        if (resultModel.getCode() == -1002 || resultModel.getCode() == -1004) {
            return new ResponseEntity<ResultModel>(resultModel, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ResultModel>(resultModel, HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v1/goods/{categoryId}/{name}", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> getGoodsByCateogryAndName(@PathVariable Integer categoryId, @PathVariable String name){
    	
    	List<Good> list = 
    			this.goodService.getGoodsByCategoryAndName(name, categoryId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(list), HttpStatus.OK);
    }
    
    /*@RequestMapping(value = "/user/v1/supplier/goods/{status}", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> selectSupplierGoodsByStatus(@PathVariable String status, HttpServletRequest request) {

    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
        List<Good> goods = this.goodService.selectSupplierGoodsByStatus(status, currentUserId);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v1/supplier/paid/goods", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> selectSupplierPaidGoods(HttpServletRequest request) {

    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
        List<Good> goods = this.goodService.selectSupplierPaidGoods(currentUserId);

        return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }*/
    
    
    
    
    
    @RequestMapping(value = "/user/v1/goods/type/{goodType}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getGoodsByType(@PathVariable String goodType){
    	List<Good> goodList = this.goodService.selectGoodsByType(goodType);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goodList), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v1/goods/{goodId}/questions", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getOrdersByGoodId(@PathVariable Integer goodId){
    	List<Question> questionList = this.goodService.getQuestionsByGoodId(goodId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(questionList), HttpStatus.OK);
    }
    

    
    
    
    @RequestMapping(value = "/user/v1/buyer/goods", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> getBuyerGoods(HttpServletRequest request){
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	List<Good> goods = this.goodService.getBuyerGoods(currentUserId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }
    
    
    /************************************************************************/
    
    @Autowired
    private GoodMapper goodMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private QuestionMapper questionMapper;
    
    @Autowired
    private FavoriteMapper favoriteMapper;
    
    @RequestMapping(value = "/user/v2/goods", method = RequestMethod.GET)
    //@Authorization
    public ResponseEntity<ResultModel> findGoods(){
    	
    	List<Good> goods = 
    			this.goodMapper.findGoods();
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    	
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}", method = RequestMethod.GET)
    @Authorization(publicFlag = true)
    public ResponseEntity<ResultModel> getById(@PathVariable Integer goodId, HttpServletRequest request){
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	Good good = this.goodMapper.findById(goodId);
    	Order order = good.getOrder();
    	
    	if(order != null) {
    		order.setCurrentBuyer(currentUserId);
    	}
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(good), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/top/order", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> findTopByPrice(@PathVariable Integer goodId, HttpServletRequest request){
    	
    	Order order = 
    			this.orderMapper.findTopByPrice(goodId);
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	if(order != null) {
    		order.setCurrentBuyer(currentUserId);
    	}
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(order), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/questions", method = RequestMethod.GET)
    @Authorization(publicFlag = true)
    public ResponseEntity<ResultModel> getQuestions(@PathVariable Integer goodId){
    	List<Question> questions =
    			this.questionMapper.findByGoodId(goodId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(questions), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/question", method = RequestMethod.POST)
    @Authorization()
    public ResponseEntity<ResultModel> submitQuestion(@PathVariable Integer goodId, @RequestBody Question question, HttpServletRequest request){
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}

    	question.setQuestionUser(currentUserId);
    	question.setQuestionDate(new Date());
    	this.questionMapper.insert(question);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/order", method = RequestMethod.POST)
    @Authorization
    @Transactional
    public synchronized ResponseEntity<ResultModel> submitOrder(@RequestParam("type") String type, @PathVariable Integer goodId, @RequestBody Order order, HttpServletRequest request){
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	if("拍卖".equals(type)) {
    		order.setBuyCount(1);
    	}
    	
    	Date now = new Date();
    	
    	order.setStatus("待支付");
    	order.setType(type);
    	order.setBuyer(currentUserId);
    	order.setBuyDate(new Timestamp(now.getTime()));
    	
    	if("拍卖".equals(order.getType())) {
    		Order searchedOrder = 
    			this.orderMapper.findByGoodIdAndBuyer(goodId, currentUserId);
        	
    		Good good = this.goodMapper.findById(goodId);
    		if(good != null) {
    			if(searchedOrder == null) {
    				if(order.getBuyPrice() <= good.getNextBid()) {
    					return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PRICE_NOT_VALID), HttpStatus.BAD_REQUEST);
    				}
            		this.orderMapper.insert(order);
            	}else {
            		if(order.getBuyPrice() <= good.getNextBid() ||
        					order.getBuyPrice() <= searchedOrder.getBuyPrice())
            		{
            			return new ResponseEntity<ResultModel>(ResultModel.error(ResultStatus.PRICE_NOT_VALID), HttpStatus.BAD_REQUEST);
            		}
            		searchedOrder.setBuyer(currentUserId);
            		searchedOrder.setBuyDate(new Timestamp(now.getTime()));
            		searchedOrder.setBuyPrice(order.getBuyPrice());
            		searchedOrder.setBuyCount(order.getBuyCount());
            		this.orderMapper.update(searchedOrder);
            	}
    			good = this.goodMapper.findById(goodId);
            	good.CalcNextBid();
        		this.goodMapper.update(good);
    		}    		
    	}else {
    		this.orderMapper.insert(order);
    	}
    	
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> submitGood(@RequestParam("type") String type, @RequestBody Good good, HttpServletRequest request){
    	
    	int currentUserId = -1;
        
        if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
        	currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
        }
    	
    	if("拍卖".equals(type)) {
    		good.setStockCount(1);
    	}
    	
    	Date now = new Date();
    	
    	good.setStatus("拍卖中");
    	
    	good.setType(type);
    	good.setSupplier(currentUserId);
    	good.setPublishDate(now);
    	good.InitAgentBid();
    	this.goodMapper.insert(good);
    	
        return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/supplier/goods", method = RequestMethod.GET)
    @Authorization
    public ResponseEntity<ResultModel> findSupplierGoodsByStatus(@RequestParam("status") String status, HttpServletRequest request){
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	List<Good> goods = this.goodMapper.findSupplierGoodsByStatus(status, currentUserId);
    	
   	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/question/{questionId}", method = RequestMethod.POST)
    @Authorization
    public ResponseEntity<ResultModel> submitAnwser(
    		@PathVariable Integer goodId, 
    		@PathVariable Integer questionId, 
    		@RequestBody Question question, HttpServletRequest request){
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}

    	question.setAnswerUser(currentUserId);
    	question.setAnswerDate(new Date());
    	this.questionMapper.update(question);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok("success"), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/goods/search/keyword/{keyword}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> searchByKeyword(@PathVariable String keyword){
    	
    	List<Good> goods = this.goodMapper.searchByKeyword("%" + keyword + "%");
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/goods/search/category/{category}", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> searchByCategory(@PathVariable Integer category){
    	
    	List<Good> goods = this.goodMapper.searchByCategory(category);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/favorite", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> getFavorite(@PathVariable Integer goodId, HttpServletRequest request){
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	Favorite favorite = this.favoriteMapper.findFavorite(goodId, currentUserId);
    	
    	Boolean flag = false;
    	
    	if(favorite != null) {
    		flag = favorite.getFlag();
    	}
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(flag), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/good/{goodId}/favorite", method = RequestMethod.POST)
    public ResponseEntity<ResultModel> updatefavorite(@PathVariable Integer goodId, HttpServletRequest request){
    	
		int currentUserId = -1;
		    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	Favorite favorite = this.favoriteMapper.findFavorite(goodId, currentUserId);
    	
    	if(favorite == null) {
    		favorite = new Favorite();
    		favorite.setGoodId(goodId);
    		favorite.setUserId(currentUserId);
    		favorite.setFlag(true);
    		this.favoriteMapper.insertFavorite(favorite);
    	}else {
    		if(favorite.getFlag()) {
    			favorite.setFlag(false);
    		}else {
    			favorite.setFlag(true);
    		}
    		this.favoriteMapper.updateFavorite(favorite);
    	}
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(favorite.getFlag()), HttpStatus.OK);
    }
    
    @RequestMapping(value = "/user/v2/user/favorited/goods", method = RequestMethod.GET)
    public ResponseEntity<ResultModel> findFavoritedGoods(HttpServletRequest request){	
    	
    	int currentUserId = -1;
    	
    	if(request.getAttribute(Constants.CURRENT_USER_ID) != null) {
    		currentUserId = (int)request.getAttribute(Constants.CURRENT_USER_ID);
    	}
    	
    	List<Good> goods = this.goodMapper.selectByFavoriteUser(currentUserId);
    	
    	return new ResponseEntity<ResultModel>(ResultModel.ok(goods), HttpStatus.OK);
    	
    }
    
}
