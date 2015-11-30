package com.book.buy.factory;

import com.book.buy.dao.MajorDao;
import com.book.buy.daoImp.MajorDaoImpl;

public class MajorDaoImpFactory {
	public static MajorDao getmajordaoimpl(){
		return new MajorDaoImpl();
	}
}
