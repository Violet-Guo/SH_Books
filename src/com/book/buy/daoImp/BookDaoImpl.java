package com.book.buy.daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.BookDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.BookVo;

/**
*	图书Dao的实现
*	@author Nvpiao
*	@time:2015年10月27日 下午1:14:08
*/
public class BookDaoImpl implements BookDao{
	private QueryRunner runner = null;
	private Connection conn = null;
	
	public BookDaoImpl(){
		runner = new QueryRunner();
		conn = DBUtils.getConnection();
	}
	
	@Override
	public void addBook(BookVo book) throws SQLException{
		String sql = "insert into book(name, userID, majorID, pubNumber, oldGrade, publicYear, author,"
				+ " hasNote, imagePath, description, bookNum, price, canBargain, time, state) "
				+ " values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		runner.update(conn, sql, book.getName(), book.getUserID(), book.getMajorID(), 
			book.getPubNumber(), book.getOldGrade(), book.getPublicYear(), 
			book.getAuthor(), book.getHasNote(), book.getImagePath(), 
			book.getDescription(), book.getBookNum(), book.getPrice(),
			book.getCanBargain(), book.getTime(), book.getState());
	}

	@Override
	public void deleteBookById(Integer id) throws SQLException{
		String sql = "delete from book where id = ?";
		runner.update(conn, sql, id);
	}

	@Override
	public void updateBook(BookVo book) throws SQLException{
		String sql = "update book set name = ?, userID = ?, majorID = ?, pubNumber= ?, oldGrade = ?,"
				+ " publicYear = ?, author = ?, hasNote = ?, imagePath = ?, description = ?,"
				+ " bookNum = ?, price = ?, canBargain = ?, time = ?, state = ? where id = ?";
		runner.update(conn, sql, book.getName(), book.getUserID(), book.getMajorID(), 
			book.getPubNumber(), book.getOldGrade(), book.getPublicYear(), 
			book.getAuthor(), book.getHasNote(), book.getImagePath(),
			book.getDescription(), book.getBookNum(), book.getPrice(), 
			book.getCanBargain(), book.getTime(), book.getState(), 
			book.getId());
	}

	@Override
	public BookVo findById(Integer id) throws SQLException{
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author,"
			+ " hasNote, imagePath, description, bookNum, price, canBargain, time,"
			+ " state from book where id = ?";
	    return runner.query(conn, sql, new BeanHandler<BookVo>(BookVo.class), id);
	}

	@Override
	public BookVo findByUserId(Integer userId) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author,"
			+ " hasNote, imagePath, description, bookNum, price, canBargain, time,"
			+ " state from book where userID = ?";
	    return runner.query(conn, sql, new BeanHandler<BookVo>(BookVo.class), userId);
	}

	@Override
	public BookVo findByUserId(Integer userId, Integer start, Integer length) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author,"
			+ " hasNote, imagePath, description, bookNum, price, canBargain, time,"
			+ " state from book where userID = ? limit ?, ?";
	    return runner.query(conn, sql, new BeanHandler<BookVo>(BookVo.class), userId, start, length);
	}

	@Override
	public Integer getLastInfertID() throws SQLException {
	    String sql = "SELECT LAST_INSERT_ID();";
	    return runner.query(sql, new BeanHandler<Integer>(Integer.class));
	}
	
	@Override
	public List<BookVo> findLatestBook(Integer tim) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author,"
			+ " hasNote, imagePath, description, bookNum, price, canBargain, time,"
			+ " state from book order by time desc limit 1, ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), tim);
	}
	
	@Override
	public List<BookVo> findAllByName(String name) throws SQLException{
		String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
				+ " imagePath, description, bookNum, price, canBargain, time, state from book where name like ?";
		return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), "%" + name + "%");
	}

	@Override
	public List<BookVo> findAllByName(String name, Integer start, Integer length) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
			+ " imagePath, description, bookNum, price, canBargain, time, state from book where name like ? "
			+ " limit ?, ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), "%" + name + "%",
		    start, length);
	}
	
	@Override
	public List<BookVo> findAllByNameAndAuthor(String name, String author) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
			+ " imagePath, description, bookNum, price, canBargain, time, state from book where name = ? and author = ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), name, author);
	}

	@Override
	public List<BookVo> findAllByNameAndAuthor(String name, String author, Integer start, Integer length)
		throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
			+ " imagePath, description, bookNum, price, canBargain, time, state from book where name = ? and author = ?"
			+ " limit ?, ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), name, author,
		    start, length);
	}

	@Override
	public List<BookVo> findAllByMajorID(Integer majorID) throws SQLException{
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
				+ " imagePath, description, bookNum, price, canBargain, time, state from book where majorID = ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), majorID);
	}

	@Override
	public List<BookVo> findAllByMajorID(Integer majorID, Integer start, Integer length) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
			+ " imagePath, description, bookNum, price, canBargain, time, state from book where majorID = ?"
			+ " limit ?, ?";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), majorID, start, length);
	}

	@Override
	public List<BookVo> findAlByMajorName(String name) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
		+ " imagePath, description, bookNum, price, canBargain, time, state from book where majorID in "
		+ "(select id from major where name = ?)";
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), name);
	}

	@Override
	public List<BookVo> findAlByMajorName(String name, Integer start, Integer length) throws SQLException {
	    String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
			+ " imagePath, description, bookNum, price, canBargain, time, state from book where majorID in "
			+ "(select id from major where name = ?) limit ?, ?";
		    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class), name, start, length);
	}

	@Override
	public List<BookVo> findAllByPart(String sql) throws SQLException {
	    return runner.query(conn, sql, new BeanListHandler<BookVo>(BookVo.class));
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
