package com.book.buy.factory;

import com.book.buy.dao.LocationDao;
import com.book.buy.daoImp.LocationDaoImp;

/**
 * Created by 宋超 on 2015/10/31.
 * version=1.0
 */
public class LocationDaoImpFactory {
    public static LocationDao getLocationDaoImp(){
        return new LocationDaoImp();
    }
}
