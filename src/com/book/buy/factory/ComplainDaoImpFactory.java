package com.book.buy.factory;

import com.book.buy.dao.ComplainDao;
import com.book.buy.daoImp.ComplainImp;

/**
 * Created by violet on 2015/10/29.
 */
public class ComplainDaoImpFactory {
    public static ComplainDao getCompDaoImp(){
        return new ComplainImp();
    }
}
