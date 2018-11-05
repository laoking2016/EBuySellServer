package com.greyu.ysj.mapper;

import org.apache.ibatis.annotations.Param;

import com.greyu.ysj.entity.AuctionHistory;

public interface AuctionHistoryMapper {
	void insert(AuctionHistory auctionHistory);
	AuctionHistory selectMaxAuction(@Param("goodId") Integer goodId);
	
	Integer selectAuctionCount(Integer goodId);
}
