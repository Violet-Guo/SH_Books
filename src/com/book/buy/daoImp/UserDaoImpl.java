package com.book.buy.daoImp;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import com.book.buy.dao.UserDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.UserVo;

/**
*	用户Dao的实现
*	@author Nvpiao
*	@time:2015年10月27日 下午1:14:30
*/
public class UserDaoImpl implements UserDao{
	private QueryRunner runner = null;
	private Connection conn = null;
	
	public UserDaoImpl(){
		runner = new QueryRunner();
		conn = DBUtils.getConnection();
	}
	
	@Override
	public void addUser(UserVo user) throws SQLException{
		String sql = "insert into user(name, username, headPhoto, password, majorID, time,"
				+ " qq, phoneNumber, complainNum) values(?, ?, ?, ?, ?, ?, ?, ?)";
		runner.update(conn, sql, user.getName(), user.getHeadPhoto(), user.getPassword(),
			user.getMajorID(), user.getTime(), user.getQq(), 
			user.getPhoneNumber(), user.getComplainNum());
	}

	@Override
	public void deleteUser(String name) throws SQLException{
		String sql = "delete from user where name = ?";
		runner.update(conn, sql, name);
	}

	@Override
	public void updateUser(UserVo user) throws SQLException{
		String sql = "update user set name = ?, username = ?, headPhoto = ?, password = ?, majorID = ?,"
				+ " time = ?, qq = ?, phoneNumber = ?, complainNum = ? where id = ?";
		runner.update(conn, sql, user.getName(), user.getUsername(), 
			user.getHeadPhoto(), user.getPassword(),user.getMajorID(), 
			user.getTime(), user.getQq(), user.getPhoneNumber(), 
			user.getComplainNum(), user.getId());
	}

	@Override
	public UserVo findUserByName(String name) throws SQLException{
		String sql = "select id, name, username, headPhoto, password, majorID, time,"
				+ " qq, phoneNumber, complainNum from user where name = ?";
		return runner.query(conn, sql, new BeanHandler<UserVo>(UserVo.class), name);
	}

	@Override
	public UserVo findUserById(Integer ID) throws SQLException {
	    	String sql = "select id, name, username, headPhoto, password, majorID, time,"
			+ " qq, phoneNumber, complainNum from user where id = ?";
	    	return runner.query(conn, sql, new BeanHandler<UserVo>(UserVo.class), ID);
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
