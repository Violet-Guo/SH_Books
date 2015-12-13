package com.book.buy.factory;

import com.book.buy.dao.MajorDao;
import com.book.buy.daoImp.MajorDaoImpl;

/**
 *  majorDao实现的工厂对象
 *  @author 张黎明
 *  @time:2015年11月1日
 */
public class MajorDaoImpFactory {
	public static MajorDao getmajordaoimpl(){
		return new MajorDaoImpl();
	}
}
