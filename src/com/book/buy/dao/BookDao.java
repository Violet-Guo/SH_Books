package com.book.buy.dao;

import java.sql.SQLException;
import java.util.List;

import com.book.buy.vo.BookVo;

/**
*	图书Dao
*	@author Nvpiao
*	@time:2015年10月27日 上午12:39:38
*/
public interface BookDao{
	/**
	 * 增加二手图书
	 * @param book
	 * @throws SQLException
	 */
	public void addBook(BookVo book) throws SQLException;
	
	/**
	 * 删除二手图书
	 * @param name
	 * @throws SQLException
	 */
	public void deleteBookById(Integer id) throws SQLException;
	
	/**
	 * 修改二手图书信息
	 * @param book
	 * @throws SQLException
	 */
	public void updateBook(BookVo book) throws SQLException;
	
	/**
	 * 按照图书编号查找图书
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public BookVo findById(Integer id) throws SQLException;
	
	/**
	 * 按照用户id和图书的状态查找图书
	 * @param UserID
	 * @param State
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByUserIDAndState(Integer UserID, Integer State) throws SQLException;
	
	/**
	 * 按照用户id和图书的状态查找图书(分页)
	 * @param UserID
	 * @param State
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByUserIDAndState(Integer UserID, Integer State, Integer start, Integer length) throws SQLException;
	
	/**
	 * 按照用户id和图书的本数进行图书的查找
	 * @param UserID
	 * @param bookNum
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByUserIDAndBookNum(Integer UserID, Integer bookNum) throws SQLException;
	
	/**
	 * 按照用户id和图书的本数进行图书的查找(分页)
	 * @param UserID
	 * @param bookNum
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByUserIDAndBookNum(Integer UserID, Integer bookNum, Integer start, Integer length) throws SQLException;
	
	/**
	 * 按照用户id去查找图书
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findByUserId(Integer userId) throws SQLException;
	
	/**
	 * 按照用户id去查找图书（分页）
	 * @param id
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findByUserId(Integer userId, Integer start, Integer length) throws SQLException;
	
	/**
	 * 返回最后一次存入的ID
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public Integer getLastInfertID() throws SQLException;
	
	/**
	 * 查找最新上架的图书（前多少个）
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findLatestBook(Integer tim) throws SQLException;
	
	/**
	 * 按照图书名称查找所有图书
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByName(String name) throws SQLException;
	
	/**
	 * 按照图书名称查找所有图书（分页）
	 * @param name
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByName(String name, Integer start, Integer length) throws SQLException;
	
	/**
	 * 按照图书名称和作者查找图书
	 * @param name
	 * @param author
	 * @return
	 */
	public List<BookVo> findAllByNameAndAuthor(String name, String author) throws SQLException;
	
	/**
	 * 按照图书名称和作者查找图书(分页)
	 * @param name
	 * @param author
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByNameAndAuthor(String name, String author, Integer start, Integer length) throws SQLException;
	
	/**
	 * 按照专业编号查询所有的图书
	 * @param majorID
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByMajorID(Integer majorID) throws SQLException;
	
	/**
	 * 按照专业编号查询所有的图书(分页)
	 * @param majorID
	 * @param start
	 * @param length
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByMajorID(Integer majorID, Integer start, Integer length) throws SQLException;
	
	/**
	 * 按照专业名称进行查找
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAlByMajorName(String name) throws SQLException;

	/**
	 * 按照专业名称进行查找（分页）
	 * @param name
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAlByMajorName(String name, Integer start, Integer length) throws SQLException;

	/**
	 * 部分拼接的sql执行
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List<BookVo> findAllByPart(String sql) throws SQLException;

	/**
	 * 释放连接
	 * @param conn
	 */
	public void close();
}
