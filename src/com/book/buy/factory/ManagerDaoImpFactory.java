package com.book.buy.factory;

import com.book.buy.dao.ManagerDao;
import com.book.buy.daoImp.ManagerDaoImp;

/**
 * Created by violet on 2015/11/4.
 */
public class ManagerDaoImpFactory {
    public static ManagerDao getManagerDaoImp(){
        return new ManagerDaoImp();
    }
}
