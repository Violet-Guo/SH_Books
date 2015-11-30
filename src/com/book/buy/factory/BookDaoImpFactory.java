package com.book.buy.factory;

import com.book.buy.dao.BookDao;
import com.book.buy.daoImp.BookDaoImpl;

/**
*	图书Dao实现的工厂对象
*	@author Nvpiao
*	@time:2015年10月27日 下午8:14:51
*/
public class BookDaoImpFactory{
	public static BookDao getBookDaoImpl(){
		return new BookDaoImpl();
	}
}
