package com.book.buy.factory;

import com.book.buy.dao.EvaluateDao;
import com.book.buy.daoImp.EvaluateDaoImp;

/**
 * Created by violet on 2015/11/21.
 */
public class EvaluateDaoImpFactory {
    public static EvaluateDao getEvaluateDaoImp(){
        return new EvaluateDaoImp();
    }
}
