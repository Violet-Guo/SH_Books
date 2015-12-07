package com.book.buy.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.FeedBackVo;

public class FeedBackDaoImpl implements FeedBackDao{	
	private QueryRunner runner=null;
	private Connection conn=null;
	public FeedBackDaoImpl(){
		runner=new QueryRunner();
		conn=DBUtils.getConnection();
	}

	@Override
	public void deleteFeedBack(int userId) throws SQLException {
		// TODO Auto-generated method stub
		String sql="delete from feedback where userid=?";
		runner.update(conn,sql,userId);
	}

	@Override
	public void close() {
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
	public List<FeedBackVo> showFeedBack() throws SQLException {
		// TODO Auto-generated method stub
		String sql="select userId,description,time from feedback order by time desc";
		return runner.query(conn, sql, new BeanListHandler<FeedBackVo>(FeedBackVo.class));
	}
	
	@Override
	public void addFeedBack(FeedBackVo FeedBack) throws SQLException{
		// TODO Auto-generated method stub
		String sql="insert into feedback (userid,description,time) values(?,?,?)";
		runner.update(conn,sql,FeedBack.getUserId(),FeedBack.getDescription(),FeedBack.getTime());	
	}

	
}
