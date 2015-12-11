package com.book.buy.dao;

import com.book.buy.vo.EvaluateVo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by violet on 2015/11/20.
 * changed by violet on 2015/12/9 添加接口：getEvaBySellAndUser
 * changed by violet on 2015/12/11 添加得到一个卖家的评价数量的方法
 */
public interface EvaluateDao {

    /**
     * 得到所有的评论
     * sellUserID为卖家的ID
     */
    public List<EvaluateVo> getAllEvaluate(int sellUserID, int begin, int count) throws SQLException;

    /**
     * 添加评论
     */
    public void addEvaluate(EvaluateVo evaluatevo) throws SQLException;

    /**
     * 查找是否有这个评价存在
     */
    public long getEvaBySellAndUser(int sellUserID, int userID) throws SQLException;

    /**
     * 得到卖家的评价数量
     */
    public Long getCountBySellerID(int sellerID) throws SQLException;

    /**
     * 关闭连接
     */
    public void close();
}
