package com.book.buy.factory;

import com.book.buy.dao.UserDao;
import com.book.buy.daoImp.UserDaoImpl;

/**
*	用户dao实现的工厂对象
*	@author Nvpiao
*	@time:2015年10月27日 下午8:13:13
*/
public class UserDaoImpFactory{
	public static UserDao getUserDaoImpl(){
		return new UserDaoImpl();
	}
}
