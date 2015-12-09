package com.book.buy.daoImp;

import java.sql.SQLException;
import java.util.List;
import java.sql.Connection;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import com.book.buy.dao.MajorDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.*;
public  class MajorDaoImpl implements MajorDao {
	private QueryRunner runner=null;
	private Connection conn=null;
	
        public MajorDaoImpl(){
        	runner = new QueryRunner();
        	conn=DBUtils.getConnection();
        	
        }
        
        @Override
        public void addMajor(MajorVo Major)throws SQLException
        {
        	String sql="insert into major (name,department,grade) values(?,?,?)";
        	runner.update(conn, sql, Major.getName(),Major.getDepartment(),Major.getGrade());
        }
        
        @Override
        public void deleteMajor(MajorVo Major)throws SQLException
        {
        	String sql="delete from major where department=? and name=? and grade=?";
        	runner.update(conn, sql,Major.getDepartment(),Major.getName(),Major.getGrade());
        }
              
        @Override
    	public  List<MajorVo> showNamesByDepartment(String department) throws SQLException 
    	{
    		// TODO Auto-generated method stub
    		String sql = "selet distinct name from major where department= ?";
    		return  runner.query(conn, sql, new BeanListHandler<MajorVo>(MajorVo.class),department);
        }
        
    
        
	
	public void close(){
		try{
			if(conn != null)
				conn.close();
		}catch (SQLException e){
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public List<MajorVo> showdepartment() throws SQLException {
		// TODO Auto-generated method stub
		String sql="select distinct department from major";
		return runner.query(conn, sql, new BeanListHandler<MajorVo>(MajorVo.class));
	}

	@Override
	public List<MajorVo> showname() throws SQLException {
		// TODO Auto-generated method stub

		String sql="select distinct name,department from major";

		return runner.query(conn,sql,new BeanListHandler<MajorVo>(MajorVo.class));
	}

	@Override
	public MajorVo getMajorByNG(String name, int grade) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from major where name=? and grade=?";
    	return  runner.query(conn, sql,new BeanHandler<MajorVo>(MajorVo.class), name, grade);
	}

	@Override
	public MajorVo getMajorByAll(String department, String name, int grade)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from major where department=? and name=? and grade=?";
    	return  runner.query(conn, sql,new BeanHandler<MajorVo>(MajorVo.class), department, name, grade);
	}



	@Override
	public List<MajorVo> showAll() throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from major";
    	return  runner.query(conn, sql,new BeanListHandler<MajorVo>(MajorVo.class));
	}

	@Override
	public MajorVo showMajor(int id) throws SQLException {
		// TODO Auto-generated method stub
		String sql="select * from major where id=?";
    	return  runner.query(conn, sql,new BeanHandler<MajorVo>(MajorVo.class),id);
	}

	@Override
	public List<MajorVo> showAllNG() throws SQLException {
		// TODO Auto-generated method stub
		String sql="select name,grade from major";
    	return  runner.query(conn, sql,new BeanListHandler<MajorVo>(MajorVo.class));
	}

	@Override
	public List<MajorVo> showNGByDepartment(String department)
			throws SQLException {
		// TODO Auto-generated method stub
		String sql="select name,grade from major where department= ?";
    	return  runner.query(conn, sql,new BeanListHandler<MajorVo>(MajorVo.class),department);
	}
}

