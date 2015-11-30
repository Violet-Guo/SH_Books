package com.book.buy.dao;


import com.book.buy.vo.ComplainVo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by violet on 15/10/27.
 * 2015/11/11 修改：添加申诉的相关接口,complainType = 0代表投诉，complainType = 1代表申诉
 */
public interface ComplainDao{
    
    /**
     * 添加投诉
     */
    public void addComp(ComplainVo comp) throws SQLException;
    /**
     * 添加申诉
     */
    public void addAppeal(ComplainVo comp) throws SQLException;

    /**
     * 显示所有的投诉
     */
    public List<ComplainVo> getAllComp() throws SQLException;

    /**
     * 显示所有的投诉
     */
    public List<ComplainVo> getAllAppeal() throws SQLException;

    /**
     * 根据用户ID查找投诉
     */
    public List<ComplainVo> getCompByUserid(int uid) throws SQLException;

    /**
     * 根据投诉状态查找投诉
     */
    public List<ComplainVo> getCompByState(int state) throws SQLException;

    /**
     * 根据用户ID查找申诉
     */
    public List<ComplainVo> getAppealByUserid(int uid) throws SQLException;

    /**
     * 根据申诉状态查找申诉
     */
    public List<ComplainVo> getAppealByState(int state) throws SQLException;

    /**
     * 关闭连接
     */
    public void close();
}
