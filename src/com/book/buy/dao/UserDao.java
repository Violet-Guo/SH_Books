package com.book.buy.dao;

import java.sql.SQLException;

import com.book.buy.vo.UserVo;

/**
*	用户Dao
*	@author Nvpiao
*	@time:2015年10月27日 上午1:07:55
*/
public interface UserDao{
	/**
	 * 增加用户
	 * @param user
	 * @throws SQLException
	 */
	public void addUser(UserVo user) throws SQLException;
	
	/**
	 * 按照用户名去删除用户
	 * @param name
	 * @throws SQLException
	 */
	public void deleteUser(String name) throws SQLException;
	
	/**
	 * 修改用户用户
	 * @param user
	 * @throws SQLException
	 */
	public void updateUser(UserVo user) throws SQLException;
	
	/**
	 * 按照用户名去查找用户
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public UserVo findUserByName(String name) throws SQLException;
	
	/**
	 * 按照用户ID去查找用户
	 * @param ID
	 * @return
	 * @throws SQLException
	 */
	public UserVo findUserById(Integer ID) throws SQLException;
	
	/**
	 * 释放连接
	 * @param conn
	 */
	public void close();
}
