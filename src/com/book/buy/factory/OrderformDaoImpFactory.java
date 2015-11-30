package com.book.buy.factory;

import com.book.buy.dao.OrderformDao;
import com.book.buy.daoImp.OrderformDaoImp;

public class OrderformDaoImpFactory {
	public static OrderformDao getOrderformDao(){
		return new OrderformDaoImp();
	}
}
