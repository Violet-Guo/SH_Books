package com.book.buy.vo;

import java.io.Serializable;

public class InformVo implements Serializable{
	
	/*
	 * 通知消息的实现  心愿单到货通知、付款通知
	 * @author 张黎明
	 * 
	 * */
	private static final long serialVersionUID = 1L;
	private int userID;
	private int type;//1：心愿单到货通知               2：付款通知
	private int num;//消息通知所对应的值 bookid||orderid
	private String time;
	private int hasRead;
	

	public InformVo()
	{
		super();
	}
	
	public int getHasRead() {
		return hasRead;
	}
	public void setHasRead(int hasRead) {
		this.hasRead = hasRead;
	}
	
	public InformVo(int userID,int type,int num,String time,int hasRead)
	{
		super();
	    this.userID=userID;
		this.type=type;
		this.num=num;
		this.time=time;
		this.hasRead=hasRead;
	}
	
	public int getUserID() 
	{
		return userID;
	}
	public void setUserID(int userID) 
	{
		this.userID = userID;
	}
	public int getType() 
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	public int getNum()
	{
		return num;
	}
	public void setNum(int num) 
	{
		this.num = num;
	}
	public String getTime() 
	{
		return time;
	}
	public void setTime(String time) 
	{
		this.time = time;
	}
	public static long getSerialversionuid() 
	{
		return serialVersionUID;
	}
	
	@Override
	public String toString() 
	{
	    return "InformVo [userID=" + userID + ", type=" + type + ", num=" + num + ", time=" + time+ ", hasRead=" + hasRead + "]";
	}
	
}
