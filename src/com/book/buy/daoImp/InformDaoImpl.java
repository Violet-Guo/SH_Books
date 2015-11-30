package com.book.buy.daoImp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.InformDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.InformVo;

public class InformDaoImpl implements InformDao
{

	private QueryRunner runner = null;
	private Connection conn = null;
	
	public  InformDaoImpl()
	{
		runner = new QueryRunner();
		conn = DBUtils.getConnection();
	}
	
	@Override
	public void addInform(InformVo informvo) throws SQLException 
	{
		// TODO Auto-generated method stub
		String sql="insert into Inform(userID,type,num,time) values(?,?,?,?)";
		runner.update(conn, sql, informvo.getUserID(),informvo.getType(),informvo.getNum(),informvo.getTime());
		
	}

	@Override
	public void deleteInform(int userID, int type, int num) throws SQLException 
	{
		// TODO Auto-generated method stub
		String sql="delete from inform where userID=? and type=? and num=?";
		runner.update(conn, sql, userID,type,num);
	}
	

	@Override
	public void close() 
	{
		// TODO Auto-generated method stub
		try{
			if(conn != null)
				conn.close();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<InformVo> findallbyut(int userID, int type)throws SQLException 
	{
		// TODO Auto-generated method stub
		String sql = "select * from Inform where userID=? and type=? order by time desc";
		return  runner.query(conn, sql, new BeanListHandler<InformVo>(InformVo.class), userID, type);
	}

	@Override
	public List<InformVo> findbyuserid(int userID) throws SQLException 
	{
		// TODO Auto-generated method stub
		String sql = "select * from Inform where userID=? order by time desc";
		return  runner.query(conn, sql, new BeanListHandler<InformVo>(InformVo.class), userID);
	}

	@Override
	public void updateInform(int userID) throws SQLException 
	{
		// TODO Auto-generated method stub
	
	    	String sql="update inform set hasRead=1 where userID=?";
	    	runner.update(conn,sql,userID);
	}

	@Override
	public List<InformVo>   count(int userID) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from inform where userID=? and hasRead=0";
		return runner.query( conn, sql, new BeanListHandler<InformVo>(InformVo.class), userID);
	}

}
