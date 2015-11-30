package com.book.buy.utils;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
*	数据库操作工具类
*	@author Nvpiao
*	@time:2015年8月11日 下午2:15:06
*/
public class DBUtils {
    //定义一个获取数据库连接的方法
    private static DataSource ds = null;
    private static Connection conn = null;
    public static Connection getConnection(){
	    	
	//利用C3P0创建数据源
	if(ds == null)
	    ds = new ComboPooledDataSource("dbInfo");
	try{
	    conn = ds.getConnection();
	} catch (SQLException e){
	    e.printStackTrace();
	}
	return conn;
    }
}
