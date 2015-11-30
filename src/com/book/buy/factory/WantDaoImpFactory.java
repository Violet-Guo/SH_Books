package com.book.buy.factory;

import com.book.buy.dao.WantDao;
import com.book.buy.daoImp.WantDaoImp;

public class WantDaoImpFactory {
	public static WantDao getWantDao(){
		return new WantDaoImp();
	}
}