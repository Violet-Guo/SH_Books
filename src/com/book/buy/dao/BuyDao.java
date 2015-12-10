package com.book.buy.dao;

import com.book.buy.vo.BuyVo;
import com.book.buy.vo.UserVo;

import java.sql.SQLException;
import java.util.List;

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

    public void updateByOrderID(BuyVo buyVo,int orderID) throws SQLException;

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
    public List<BuyVo> getBuyByUserID(int userID,int begin,int count) throws SQLException;
    public List<BuyVo> getBuyByUserID(UserVo userVo,int begin,int count,int state) throws SQLException;
    public Long getCountByUserID(int userID) throws SQLException;
    public Long getCountByUserID(UserVo userVo,int state) throws SQLException;
    /**
     * 按照orderid查找
     * @param orderID
     * @return
     * @throws SQLException
     */
    public BuyVo getBuyByOrderID(int orderID) throws SQLException;

    /**
     * 获取待付款
     * @param userID
     * @param begin
     * @param count
     * @return
     * @throws SQLException
     */
    public List<BuyVo> getWaitMoneyByUserID(int userID,int begin,int count) throws SQLException;
    public List<BuyVo> getWaitMoneyByUserID(UserVo userVo,int begin,int count,int state) throws SQLException;
    public Long getWaitMoneyCount(int userID) throws SQLException;
    public Long getWaitMoneyCount(UserVo userVo,int state) throws SQLException;
    /**
     * 获取待确认订单
     * @param userID
     * @param begin
     * @param count
     * @return
     * @throws SQLException
     */
    public List<BuyVo> getWaitSureByUserID(int userID,int begin,int count) throws SQLException;
    public List<BuyVo> getWaitSureByUserID(UserVo userVo,int begin,int count,int state) throws SQLException;
    public Long getWaitSureCount(int userID) throws SQLException;
    public Long getWaitSureCount(UserVo userVo,int state) throws SQLException;
    /**
     * 获取最后插入的一列的id
     * @return
     * @throws SQLException
     */
    public int getLastInsertID() throws SQLException;

    /**
     * 获取未评价的订单
     * @return
     * @param userID
     * @param begin
     * @param count
     * @throws SQLException
     */
    public List<BuyVo> getWaitEvaByUserID(int userID,int begin,int count) throws SQLException;
    public List<BuyVo> getWaitEvaByUserID(UserVo userVo,int begin,int count,int state) throws SQLException;
    public Long getWaitEvaCount(UserVo userVo,int state) throws SQLException;
    public Long getWaitEvaCount(int userID) throws SQLException;

    /**
     * 获取每个订单的总价格
     * @param orderID
     * @return
     * @throws SQLException
     */
    public Double getBuyPrice(int orderID) throws SQLException;
    /**
     * 关闭数据库
     * @throws SQLException
     */
    public void close() throws SQLException;
}
