package com.book.buy.factory;

import com.book.buy.dao.BuyDao;
import com.book.buy.daoImp.BuyDaoImp;

/**
 * Created by 宋超 on 2015/10/31.
 * version=1.0
 */
public class BuyDaoImpFactory {
    public static BuyDao getBuyDaoImp(){
        return new BuyDaoImp();
    }
}
