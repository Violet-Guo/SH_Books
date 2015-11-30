package com.book.buy.factory;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.daoImp.FeedBackDaoImpl;

/**
 *  反馈Dao实现的工厂对象
 *  @author 张黎明
 *  @time:2015年11月1日
 */

public class FeedBackDaoImplFactory 
{
 public static FeedBackDao getFeedBackDaoImpl()
 {
	 return new FeedBackDaoImpl();
 }
}
