package com.book.buy.vo;

import java.io.Serializable;

public class WantVo implements Serializable
{
	
	private static final long serialVersionUID = 1L;
	
	/** 用户id */
	private Integer userID;
	
	/** 书籍名称 */
	private Integer bookID;
	
	/** 时间 */
	private String time;

	public WantVo() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public WantVo(Integer userID, Integer bookID, String  time){
		this.userID = userID;
		this.bookID = bookID;
		this.time = time;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}


	public Integer getBookID() {
		return bookID;
	}

	public void setBookID(Integer bookID) {
		this.bookID = bookID;
	}


	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	@Override
	public String toString() {
		return "WantVo [userID=" + userID + ", bookID=" + bookID + ", time=" + time + "]";
	}
	
}

