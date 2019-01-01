package cn.itcast.entity;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {
	private static final long serialVersionUID = 4542286445954735944L;
	private Integer itemId;
	private String itemName;
	private Float price;
	private String detail;
	private String pic;
	private Date itemCreateTime;

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public Date getItemCreateTime() {
		return itemCreateTime;
	}

	public void setItemCreateTime(Date itemCreateTime) {
		this.itemCreateTime = itemCreateTime;
	}

	@Override
	public String toString() {
		return "Item [itemId=" + itemId + ", itemName=" + itemName + ", price=" + price + ", detail=" + detail
				+ ", pic=" + pic + ", itemCreateTime=" + itemCreateTime + "]";
	}

}
