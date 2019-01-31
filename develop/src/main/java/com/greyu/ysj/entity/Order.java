package com.greyu.ysj.entity;

import java.sql.Timestamp;
import java.util.Date;

public class Order {
    

    private Integer userId;

    private Integer addressId;

    private Double amount;

    private Date createTime;

    private String remarks;

    /**
     * 订单状态
     * 0  待发货
     * 1  已发货
     * 2  确认收货
     */

    //private List<OrderDetail> orderDetails;

    /*public List<OrderDetail> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetail> orderDetails) {
        this.orderDetails = orderDetails;
    }*/

    

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", addressId=" + addressId +
                ", amount=" + amount +
                ", createTime=" + createTime +
                ", remarks='" + remarks + '\'' +
                ", status=" + status +
                "}";
    }
    
    /*********************************************/
    
    private Long orderId;
    
    private Integer goodId;
    
    private Timestamp buyDate;
    
    private Integer buyCount;
    
    private String type;
    
    private Double buyPrice;
    
    private Integer buyer;
    
    private Integer supplier;
    
    private Date shipDate;
    
    private String status;
    
    private Integer currentBuyer;
    
    private String buyerName;
    
    private String phone;
    
    private String address;
    
    private String goodName;
    
    private String images;
    
    private Double topBuyPrice;
    
    private Timestamp deadline;
    
    private String description;
    
    private Double nextBid;
    
    private Double postage;
    
    public Double getPostage() {
		return postage;
	}

	public void setPostage(Double postage) {
		this.postage = postage;
	}

	public Double getNextBid() {
		return nextBid;
	}

	public void setNextBid(Double nextBid) {
		this.nextBid = nextBid;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Timestamp getDeadline() {
		return deadline;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	public Boolean getTopFlag() {
    	return this.buyPrice.equals(this.topBuyPrice);
    }
    
    public Double getTopBuyPrice() {
		return topBuyPrice;
	}

	public void setTopBuyPrice(Double topBuyPrice) {
		this.topBuyPrice = topBuyPrice;
	}

	public String getGoodName() {
		return goodName;
	}

	public void setGoodName(String goodName) {
		this.goodName = goodName;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String getAddress() {
    	return this.address;
    }
    
	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setBuyerName(String buyerName) {
		this.buyerName = buyerName;
	}

	public String getBuyerName() {
		return buyerName;
	}

	public void setBuyName(String buyerName) {
		this.buyerName = buyerName;
	}

	public Boolean getOwnerFlag() {
		if(this.buyer == null) {
			return false;
		}
		return this.buyer.equals(this.currentBuyer);
	}

	public Integer getCurrentBuyer() {
		return currentBuyer;
	}

	public void setCurrentBuyer(Integer currentBuyer) {
		this.currentBuyer = currentBuyer;
	}

	public String getStatus() {
		
		Date now = new Date();
		
		if("精品商城".equals(this.getType())) {
			return status;
		}
		
		if(this.getDeadline() != null && this.getDeadline().compareTo(now) > 0){
			return "拍卖中";
		}
		
		if(this.getTopBuyPrice() != null && this.getBuyPrice() < this.getTopBuyPrice()) {
			return "未拍中";
		}
			
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    
    public Integer getGoodId() {
		return goodId;
	}

	public void setGoodId(Integer goodId) {
		this.goodId = goodId;
	}

	public Timestamp getBuyDate() {
		return buyDate;
	}

	public void setBuyDate(Timestamp buyDate) {
		this.buyDate = buyDate;
	}

	public Integer getBuyCount() {
		return buyCount;
	}

	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Double getBuyPrice() {
		return buyPrice;
	}

	public void setBuyPrice(Double buyPrice) {
		this.buyPrice = buyPrice;
	}

	public Integer getBuyer() {
		return buyer;
	}

	public void setBuyer(Integer buyer) {
		this.buyer = buyer;
	}

	public Integer getSupplier() {
		return supplier;
	}

	public void setSupplier(Integer supplier) {
		this.supplier = supplier;
	}

	public Date getShipDate() {
		return shipDate;
	}

	public void setShipDate(Date shipDate) {
		this.shipDate = shipDate;
	}
}