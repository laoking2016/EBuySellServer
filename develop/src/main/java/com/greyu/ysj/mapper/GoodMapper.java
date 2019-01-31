package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.Good;
import com.greyu.ysj.entity.GoodExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodMapper {
    int countByExample(GoodExample example);

    int deleteByExample(GoodExample example);

    int deleteByPrimaryKey(Integer goodId);

    

    int insertSelective(Good record);

    List<Good> selectByExample(GoodExample example);

    Good selectByPrimaryKey(Integer goodId);

    int updateByExampleSelective(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByExample(@Param("record") Good record, @Param("example") GoodExample example);

    int updateByPrimaryKeySelective(Good record);

    int updateByPrimaryKey(Good record);
    
       
    
    
    List<Good> selectByBuyer(Integer userId);
    
    List<Good> selectBySupplier(Integer userId);
    
    List<Good> selectSupplierGoodsByStatus(@Param("status") String status, @Param("userId") Integer userId);
    
    List<Good> selectSupplierPaidGoods(Integer userId);
    
    void updateAuction(@Param("goodId") Integer goodId, 
    		@Param("price") Double price,
    		@Param("priceUser") Integer priceUser, 
    		@Param("priceDate") Date priceDate, 
    		@Param("priceCount") Integer priceCount);
    
    List<Good> selectByType(@Param("goodType") String goodType);
    
    /******************************************************************/
    int insert(Good record);
    int update(Good record);
    List<Good> findGoods();
    List<Good> findByKeword(String keyword);
    Good findById(Integer goodId);
    List<Good> searchByKeyword(@Param("keyword") String keyword);
    List<Good> searchByCategory(@Param("category") Integer category);
    List<Good> findSupplierGoodsByStatus(@Param("status") String status, @Param("supplier") Integer supplier); 
    List<Good> selectByFavoriteUser(Integer userId);
}