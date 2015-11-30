package com.book.buy.vo;

import java.io.Serializable;

public class OrderFormVo implements Serializable
{
	
	private static final long serialVersionUID = 1L;

	/** item id */
	private Integer id;
	
	/** 用户id */
	private Integer userID;
	
	/** 订单id */
	private Integer orderId;
	
	/** 书籍id */
	private Integer bookID;
	
	/** 书籍数目 */
	private Integer bookNum;

	public OrderFormVo(){
		super();
	}
	
	public OrderFormVo(Integer userID, Integer orderId, Integer bookID, Integer bookNum){
		this.userID = userID;
		this.orderId = orderId;
		this.bookID = bookID;
		this.bookNum = bookNum;
	}
		
	public OrderFormVo(Integer id, Integer userID, Integer orderId, Integer bookID, Integer bookNum){
		this.id = id;
		this.userID = userID;
		this.orderId = orderId;
		this.bookID = bookID;
		this.bookNum = bookNum;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}

	public Integer getBookNum() {
		return bookNum;
	}

	public void setBookNum(Integer bookNum) {
		this.bookNum = bookNum;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "Orderform [id=" + id + ", userID=" + userID + ", orderId=" + orderId + ", bookID=" + bookID
				+ ", bookNum=" + bookNum + "]";
	}
}
