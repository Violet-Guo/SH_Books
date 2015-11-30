package com.book.buy.daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.WantDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.WantVo;


public class WantDaoImp implements WantDao{
	private QueryRunner runner = null;
	private Connection conn = null;
	
	public WantDaoImp(){
		runner = new QueryRunner();
		conn = DBUtils.getConnection();
	}
	@Override
	public void addWant(WantVo want) throws SQLException{
		String sql = "insert into want(userID, bookID, time) value(?, ?, ?) ";
		runner.update(conn, sql, want.getUserID(), want.getBookID(), want.getTime());
	}
	
	@Override
	public void delWant(Integer userID, Integer bookID) throws SQLException{
		String sql = "delete from want where userID = ? and bookID = ?";
		runner.update(conn, sql, userID, bookID);
	}
	
	@Override
	public void updateWant(WantVo want) throws SQLException{
		String sql = "update want set userID = ?, bookID = ?, time = ? where userID = ? and bookID = ?";
		runner.update(conn, sql, want.getUserID(), want.getBookID(), want.getTime(),want.getUserID(), want.getBookID());
	}
	
	@Override
	public WantVo findByuseridandbookid(Integer userID, Integer bookID) throws SQLException{
		String sql = "select userID, bookID, time from want where userID = ? and bookID = ?";
		return runner.query(conn, sql, new BeanHandler<WantVo>(WantVo.class), userID, bookID);
	}
	
	@Override
	public List<WantVo> findAllwant(Integer userID) throws SQLException{
		String sql = "select userID, bookID, time from want where userID = ?";
		return runner.query(conn, sql, new BeanListHandler<WantVo>(WantVo.class),userID);	
	}
	
	@Override
	public WantVo findBybookid(Integer bookID) throws SQLException{
		String sql = "select userID, bookID, time from want where bookID = ?";
		return runner.query(conn, sql, new BeanHandler<WantVo>(WantVo.class), bookID);
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
