package com.book.buy.dao;

import com.book.buy.vo.BuyVo;

import java.sql.SQLException;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public interface BuyDao {
    /**
     * 增加
     * @param buyVo
     * @throws SQLException
     */
    public void addBuy(BuyVo buyVo) throws SQLException;
    
    /**
     * 按照OrderId删除
     * @param orderID
     * @throws SQLException
     */
    public void delBuyByOrderID(int orderID) throws SQLException;
    
    /**
     * 按照userID删除
     * @param userID
     * @throws SQLException
     */
    public void delBuyByUserID(int userID) throws SQLException;
    
    /**
     * 按照userID查找
     * @param userID
     * @return
     * @throws SQLException
     */
    public BuyVo getBuyByUserID(int userID) throws SQLException;
    
    /**
     * 按照orderid查找
     * @param orderID
     * @return
     * @throws SQLException
     */
    public BuyVo getBuyByOrderID(int orderID) throws SQLException;

    public void close() throws SQLException;
}
