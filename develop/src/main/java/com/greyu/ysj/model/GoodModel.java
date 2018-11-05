package com.greyu.ysj.model;

import com.greyu.ysj.entity.Good;

public class GoodModel {
	public GoodModel(Good good, int userId) {
		this.good = good;
		this.userId = userId;
		//this.priceUserFlag = false;
	}
	
	private Good good;
	private int userId;
	//private boolean priceUserFlag;

	public Good getGood() {
		return good;
	}

	public int getUserId() {
		return userId;
	}

	public boolean isPriceUserFlag() {
		if(good == null) {
			return false;
		}
		
		if(good.getPriceUser() == null) {
			return false;
		}
		
		if(good.getPriceUser() == userId) {
			return true;
		}
		
		return false;
	}
}
