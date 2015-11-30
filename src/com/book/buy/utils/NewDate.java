package com.book.buy.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
*	创建时间
*	@author Nvpiao
*	@time:2015年9月17日 下午9:25:20
*/
public class NewDate{
	public static String getDateTime(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return df.format(date);
	}
	
	public static String getDate(Date date){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(date);
	}
}
