package com.book.buy.factory;

import com.book.buy.dao.InformDao;
import com.book.buy.daoImp.InformDaoImpl;



public class InformDaoImplFactory 
{
	public static InformDao getInformDaoImpl(){
		return new InformDaoImpl();
	}

}
