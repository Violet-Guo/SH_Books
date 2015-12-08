package com.book.buy.daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.OrderformDao;
import com.book.buy.vo.OrderFormVo;
import org.apache.commons.dbutils.handlers.ScalarHandler;


public class OrderformDaoImp implements OrderformDao{
	private QueryRunner runner = null;
	private Connection conn = null;
	
	public OrderformDaoImp(){
		runner = new QueryRunner();
		conn = com.book.buy.utils.DBUtils.getConnection();
	}
	
	@Override
	public void addOrderform(OrderFormVo orderform) throws SQLException{
		String sql = "insert into orderform(userID, orderId, bookID, bookNum) value(?, ?, ?, ?) ";
		runner.update(conn, sql,orderform.getUserID(), orderform.getOrderId(), orderform.getBookID(),orderform.getBookNum());
	}
	
	@Override
	public void delOrderformByUserID(Integer userID) throws SQLException{
		String sql = "delete from orderform where userID = ?";
		runner.update(conn, sql, userID);
	}
	
	@Override
	public void updateOrderform(OrderFormVo orderform) throws SQLException{
		String sql = "update orderform set userID = ?, orderId = ?, bookID = ?, bookNum = ? where userID = ? and bookID = ?";
		runner.update(conn, sql, orderform.getUserID(), orderform.getOrderId(), orderform.getBookID(), orderform.getBookNum(), orderform.getUserID(), orderform.getBookID());
	}
	
	@Override
	public OrderFormVo findByuseridandbookid(Integer userID, Integer bookID) throws SQLException{
		String sql = "select id, userID, orderId, bookID, bookNum from orderform where userID = ? and bookID = ?";
		return runner.query(conn, sql, new BeanHandler<OrderFormVo>(OrderFormVo.class), userID, bookID);
	}
	
	@Override
	public List<OrderFormVo> findAllitem(Integer userID) throws SQLException{
		String sql = "select id, userID, orderId, bookID, bookNum from orderform where userID = ? and isnull(orderID)";
		return runner.query(conn, sql, new BeanListHandler<OrderFormVo>(OrderFormVo.class),userID);	
	}

	@Override
	public Double findSumPriceByUserID(int userID) throws SQLException {
		String sql = "SELECT SUM(b.price) as price FROM book b,orderform o where o.bookID = b.id and isnull(o.orderID) and o.userID=?";
		return (Double)runner.query(conn,sql,new ScalarHandler(),userID);
	}

	@Override
	public void  updateByuserid(Integer userID, Integer orderID) throws SQLException {
		String sql = "update orderform set orderID = ? where userID = ?";
		runner.update(conn, sql , orderID, userID);	
	}
	
	@Override
	public void delOrderformByid(Integer id) throws SQLException {
		String sql = "delete from orderform where id = ?";
		runner.update(conn, sql, id);
	}
	
	@Override
	public List<OrderFormVo> findByOrderID(Integer orderid) throws SQLException {
		String sql = "select id, userID, orderID, bookiD, bookNum from orderform where orderid = ?";
		return runner.query(conn, sql, new BeanListHandler<OrderFormVo>(OrderFormVo.class), orderid);
	}

	@Override
	public void setOrderNullByOrderID(Integer orderID) throws SQLException {
		String sql = "update order set orderID=null where orderID=?";
		runner.update(conn,sql,orderID);
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
	
