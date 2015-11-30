package com.book.buy.vo;

import java.io.Serializable;

/**
 * 反馈
 * @author 张黎明
 * @time：2015年11月1日
 */

public class FeedBackVo implements Serializable{
    	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer userId;
	private String description;
	private String time;
	public FeedBackVo(){
		super();
	}
	
	public FeedBackVo(Integer userId,String description,String time){
		super();
		this.userId=userId;
		this.description=description;
		this.time=time;
	}
	public FeedBackVo(String description,String time){
		super();

		this.description=description;
		this.time=time;
	}
	
	public String getTime() {
		return time;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public Integer getUserId() {
		return userId;
	}
	
	public void setUserId(Integer userId){
		this.userId = userId;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
	    return "FeedBackVo [userId=" + userId + ", description=" + description + ", time=" + time + "]";
	}
}