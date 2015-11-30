package com.book.buy.dao;


import com.book.buy.vo.SellVo;

import java.sql.SQLException;


/**
 * Created by violet on 2015/10/27.
 */
public interface SellDao {

    /**
     * 增加sell
     * @param sellvo
     * @throws SQLException
     */
    public void addSell(SellVo sellvo) throws SQLException;
    
    /**
     * 图书上架
     */
    public void upBook(int bid) throws SQLException;

    /**
     * 图书下架
     */
    public void downBook(int bid) throws SQLException;

    /**
     * close
     */
    public void close();

}
