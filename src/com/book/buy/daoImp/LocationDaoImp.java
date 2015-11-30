package com.book.buy.daoImp;

import com.book.buy.dao.LocationDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.LocationVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public class LocationDaoImp implements LocationDao{
    QueryRunner runner;
    Connection conn;
    public LocationDaoImp(){
        runner = new QueryRunner();
        conn = DBUtils.getConnection();
    }

    @Override
    public void addLocation(LocationVo locationVo) throws SQLException {
        String sql = "insert into location (userID,dorName,dorNum,FloorNum) values (?,?,?,?)";
        runner.update(conn,sql,locationVo.getUserID(),locationVo.getDorName(),locationVo.getDorNum(),locationVo.getFloorNum());
    }

    @Override
    public LocationVo getLocationByuserID(int userID) throws SQLException {
        String sql = "select userID,dorName,dorNum,FloorNum from location where userID="+userID;
        return runner.query(conn,sql,new BeanHandler<LocationVo>(LocationVo.class));
    }

    @Override
    public void UpdateLocation(LocationVo locationVo) throws SQLException {
        String sql = "update location set dorName='"+locationVo.getDorName()+"',dorNum="+locationVo.getDorNum()+",FloorNum="+locationVo.getFloorNum()+"  where userID="+locationVo.getUserID();
        runner.update(conn,sql);
    }

    @Override
	public void close(){
		try{
		    if(conn != null)
			conn.close();
		} 
		catch (SQLException e){
		   // TODO Auto-generated catch block
		    e.printStackTrace();
		}
	}
}
