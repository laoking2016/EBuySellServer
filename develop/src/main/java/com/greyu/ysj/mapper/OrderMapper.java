package com.greyu.ysj.mapper;

import com.greyu.ysj.entity.Order;
import com.greyu.ysj.entity.OrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OrderMapper {
    int countByExample(OrderExample example);

    int deleteByExample(OrderExample example);

    int deleteByPrimaryKey(Long orderId);

    

    int insertSelective(Order record);

    List<Order> selectByExample(OrderExample example);

    Order selectByPrimaryKey(Long orderId);

    int updateByExampleSelective(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByExample(@Param("record") Order record, @Param("example") OrderExample example);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);
    
    /*****************************************************/
    List<Order> findByGoodId(Integer goodId);
    Order findTopByPrice(Integer goodId);
    List<Order> findSupplierOrders(Integer supplier);
    Order findById(Integer orderId);
    int insert(Order order);
    void updateStatus(@Param("status") String status, @Param("orderId") Integer orderId);
    List<Order> findForBuyer(Integer userId);
    Order findForBuyerById(Integer orderId);
    Order findByGoodIdAndBuyer(@Param("goodId") Integer goodId, @Param("buyer") Integer buyer);
    void update(Order order);
}