package com.book.buy.dao;

import com.book.buy.vo.LocationVo;

import java.sql.SQLException;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public interface LocationDao {
    
    /**
     * 增加Location
     * @param locationVo
     * @throws SQLException
     */
    public void addLocation(LocationVo locationVo) throws SQLException;
    
    /**
     * 得到Location
     * @param userID
     * @return
     * @throws SQLException
     */
    public LocationVo getLocationByuserID(int userID) throws SQLException;

    /**
     * 更新Location
     *
     */
    public void UpdateLocation(LocationVo locationVo) throws SQLException;

    public void close();
}
