package com.book.buy.factory;

import com.book.buy.dao.SellDao;
import com.book.buy.daoImp.SellImp;

/**
 * Created by violet on 2015/10/29.
 */
public class SellDaoImpFactory {
    public static SellDao getSellDaoImp(){
        return new SellImp();
    }
}
