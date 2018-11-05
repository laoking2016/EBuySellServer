package com.greyu.ysj.repository;

import java.util.List;
import com.greyu.ysj.entity.Good;

public interface GoodRepository {
	List<Good> findByKeword(String keyword);
	List<Good> findPublishedAuctionGoods();
	Good findById(Integer goodId);
}
