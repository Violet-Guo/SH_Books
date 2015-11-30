package com.book.buy.dao;

import com.book.buy.vo.EvaluateVo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by violet on 2015/11/20.
 */
public interface EvaluateDao {

    /**
     * 得到所有的评论
     * sellUserID为卖家的ID
     */
    public List<EvaluateVo> getAllEvaluate(int sellUserID) throws SQLException;

    /**
     * 添加评论
     */
    public void addEvaluate(EvaluateVo evaluatevo) throws SQLException;
}
