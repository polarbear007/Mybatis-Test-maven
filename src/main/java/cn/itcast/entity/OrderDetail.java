package cn.itcast.entity;

import java.io.Serializable;

public class OrderDetail implements Serializable {
	private static final long serialVersionUID = -5910671132115076567L;
	private Integer odId;
	private Integer ItemsNum;
	// 关联对象 item , 一对一关联
	// 一个订单项对应唯一的一个商品
	private Item item;

	public Integer getOdId() {
		return odId;
	}

	public void setOdId(Integer odId) {
		this.odId = odId;
	}

	public Integer getItemsNum() {
		return ItemsNum;
	}

	public void setItemsNum(Integer itemsNum) {
		ItemsNum = itemsNum;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	@Override
	public String toString() {
		return "OrderDetail [odId=" + odId + ", ItemsNum=" + ItemsNum + ", item=" + item + "]";
	}

	
}
