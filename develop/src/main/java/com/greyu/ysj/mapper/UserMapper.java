package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.User;
import com.greyu.ysj.entity.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    
    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /***********************************************************/
    User findByOpenId(String openId);
    int insert(User record);
    User findById(Integer userId);
    List<User> findBySupplier(Integer supplier);
    User findByName(String name);
    List<User> findAll();
}