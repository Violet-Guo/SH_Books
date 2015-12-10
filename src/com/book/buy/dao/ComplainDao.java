package com.book.buy.dao;


import com.book.buy.vo.ComplainVo;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by violet on 15/10/27.
 * 2015/11/11 修改：添加申诉的相关接口,complainType = 0代表投诉，complainType = 1代表申诉
 * 2015/11/26 修改：添加通过ID查找申诉和投诉的方法
 * 2015/12/4  修改：添加update投诉和申诉的方法
 * 2015/12/4  修改：把申诉和投诉的方法合在了一起
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
     * 更新投诉、申诉
     */
    public void updateComp(ComplainVo comp) throws SQLException;

    /**
     * 显示所有的投诉
     */
    public List<ComplainVo> getAllComp() throws SQLException;

    /**
     * 显示所有的投诉
     */
    public List<ComplainVo> getAllComp(int begin, int count) throws SQLException;

    /**
     * 显示所有的申诉
     */
    public List<ComplainVo> getAllAppeal() throws SQLException;

    /**
     * 显示所有的申诉
     */
    public List<ComplainVo> getAllAppeal(int begin, int count) throws SQLException;

    /**
     * 根据用户ID查找投诉、申诉
     */
    public List<ComplainVo> getCompByUserid(int uid, int complainType) throws SQLException;

    /**
     * 根据投诉、申诉状态查找投诉、申诉
     */
    public List<ComplainVo> getCompByState(int state, int complainType) throws SQLException;

    /**
     * 根据投诉、申诉的ID查找投诉、申诉
     */
    public ComplainVo getCompById(int id) throws SQLException;

    /**
     * 关闭连接
     */
    public void close();
}
