package com.book.buy.factory;

import com.book.buy.dao.InformDao;
import com.book.buy.daoImp.InformDaoImpl;

/**
 *  通知Dao实现的工厂对象
 *  @author 张黎明
 *  @time:2015年11月1日
 */

public class InformDaoImplFactory 
{
	public static InformDao getInformDaoImpl(){
		return new InformDaoImpl();
	}

}
