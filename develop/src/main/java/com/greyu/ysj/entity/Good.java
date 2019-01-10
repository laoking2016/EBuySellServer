package com.greyu.ysj.entity;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class Good {
    

    private String image;

    private Double originalPrice;

    

    private Integer soldCount;

    private String spec;

    private String origin;

    private CategorySecond category;
    
    private Integer priceUser;
    
    private Date priceDate;
    
    private Integer priceCount;
    
    private Integer supplierUser;
    
    private Date supplierDate;
    
    private Integer supplierCount;
    
    

	public Integer getSupplierUser() {
		return supplierUser;
	}

	public void setSupplierUser(Integer supplierUser) {
		this.supplierUser = supplierUser;
	}

	public Date getSupplierDate() {
		return supplierDate;
	}

	public void setSupplierDate(Date supplierDate) {
		this.supplierDate = supplierDate;
	}

	public Integer getSupplierCount() {
		return supplierCount;
	}

	public void setSupplierCount(Integer supplierCount) {
		this.supplierCount = supplierCount;
	}

	public Integer getPriceUser() {
		return priceUser;
	}

	public void setPriceUser(Integer priceUser) {
		this.priceUser = priceUser;
	}

	public Date getPriceDate() {
		return priceDate;
	}

	public void setPriceDate(Date priceDate) {
		this.priceDate = priceDate;
	}

	public Integer getPriceCount() {
		return priceCount;
	}

	public void setPriceCount(Integer priceCount) {
		this.priceCount = priceCount;
	}
    
    public CategorySecond getCategory() {
        return category;
    }

    public void setCategory(CategorySecond category) {
        this.category = category;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image == null ? null : image.trim();
    }
    
    public Double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(Double originalPrice) {
        this.originalPrice = originalPrice;
    }

    

    public Integer getSoldCount() {
        return soldCount;
    }

    public void setSoldCount(Integer soldCount) {
        this.soldCount = soldCount;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin == null ? null : origin.trim();
    }

    @Override
    public String toString() {
        return "Good{" +
                "goodId=" + goodId +
                ", categorySecondId=" + categorySecondId +
                ", goodName='" + goodName + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", originalPrice=" + originalPrice +
                ", inventory=" + inventory +
                ", soldCount=" + soldCount +
                ", spec='" + spec + '\'' +
                ", origin='" + origin + '\'' +
                ", category=" + category +
                '}';
    }
    
    
    /*****************************************************/
    
    private Integer inventory;
    
    private Integer goodId;

    private Integer categorySecondId;

    private String goodName;
    
    private Double price;
    
    private Integer supplier;
    
    private String number;
    
    private String images;
    
    private String description;
    
    private Double postage;
    
    private Timestamp deadline;
    
    private Integer stockCount;
    
    private String type;
    
    private String payment;
    
  //拍卖中,待支付,待发货,待签收,已完成,
    private String status;
    
    private List<Order> orders;
    
    private Order order;
    
    //private Double finalPrice;
    
    private Double topPrice;
    
    private Integer orderId;
    
    private String status2;
    
    public String getStatus2() {
		return status2;
	}

	public void setStatus2(String status2) {
		this.status2 = status2;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Order getOrder() {
    	
    	if(orders == null) {
    		return null;
    	}
    	
    	if(orders.size() == 0) {
			return null;
		}
    	
    	Order order = 
				orders.stream().max(Comparator.comparing(Order::getBuyPrice)).get();
		
		return order;
    }
    
    public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public String getStatus() {
		
		Date now = new Date();
		
		if(this.getDeadline().compareTo(now) > 0) {
			return "拍卖中";
		}
		
		if(orders == null) {
			return "拍卖中";
		}
		
		if(orders.size() == 0) {
			return "已流拍";
		}
		
		Order order = 
				orders.stream().max(Comparator.comparing(Order::getBuyPrice)).get();
		
		return order.getStatus();
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    public String getPayment() {
		return payment;
	}

	public void setPayment(String payment) {
		this.payment = payment;
	}

	public String getType() {
    	return type;
    }
    
    public void setType(String type) {
    	this.type = type;
    }
    
	public Integer getStockCount() {
		return stockCount;
	}

	public void setStockCount(Integer stockCount) {
		this.stockCount = stockCount;
	}

	public Integer getCategorySecondId() {
        return categorySecondId;
    }

    public void setCategorySecondId(Integer categorySecondId) {
        this.categorySecondId = categorySecondId;
    }
    
    public String getImages() {
    	return images;
    }
    
    public void setImages(String images) {
    	this.images = images;
    }
    
    public Integer getGoodId() {
        return goodId;
    }

    public void setGoodId(Integer goodId) {
        this.goodId = goodId;
    }
    
    public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
    
	public Integer getSupplier() {
		return supplier;
	}

	public void setSupplier(Integer supplier) {
		this.supplier = supplier;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}
	
	public Double getPostage() {
    	return postage;
    }
    
    public void setPostage(Double postage) {
    	this.postage = postage;
    }
	
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    
    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }
    
    public Timestamp getDeadline() {
    	return deadline;
    }
    
    public void setDeadline(Timestamp deadline) {
    	this.deadline = deadline;
    }
    
    public Double getCurrentBid() {
    	
    	if(this.orders == null) {
    		return 0.0;
    	}
    	
    	int size = orders.size();
    	
    	if(size == 0) {
    		return 0.0;
    	}
    	
    	if(size == 1) {
    		return price;
    	}
    	
    	List<Order> sortedOrders = 
    			orders.stream().sorted((f1, f2) -> Double.compare(f1.getBuyPrice(), f2.getBuyPrice())).collect(Collectors.toList());
    	
    	return sortedOrders.get(size - 2).getBuyPrice();
    }
    
    public Double getNextBid() {
    	
    	Double currentBid = getCurrentBid();
    	
    	if(currentBid == 0) {
    		return price;
    	}
    	
    	return (double)Math.round(currentBid * 1.05);
    	
    }
    
    public Double getFinalPrice() {
    	Double price = this.price;
    	Order order = this.getOrder();
    	
    	if(order == null) {
    		return this.getPrice();
    	}
    	
    	int size = orders.size();
    	Double maxPrice = order.getBuyPrice();
  
    	if(size == 1) {
    		price = order.getBuyPrice();
    	}else {
    		List<Order> sortedOrders = 
        			orders.stream().sorted((f1, f2) -> Double.compare(f1.getBuyPrice(), f2.getBuyPrice())).collect(Collectors.toList());
        	
        	price = sortedOrders.get(size - 2).getBuyPrice();
        	price = (double)Math.round(price * 1.05);
    	}
    	
    	if(price > maxPrice) {
    		return (double)Math.round(maxPrice);
    	}else {
    		return price;
    	}
    }
    
    public Double getTopPrice() {
    	
    	if(this.topPrice == null) {
    		return this.price;
    	}
    	
    	return topPrice;
    }
    
    public void setTopPrice(Double topPrice) {
    	this.topPrice = topPrice;
    }
    
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }
    
    private Integer buyer;
    
    public Integer getBuyer() {
    	return this.buyer;
    }
    
    public void setBuyer(Integer buyer) {
    	this.buyer = buyer;
    }
    
    /*public void setFinalPrice(Double finalPrice) {
    	this.finalPrice = finalPrice;
    }*/
}