package com.book.buy.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * 数据库操作工具类
 *
 * @author Nvpiao
 * @time:2015年8月11日 下午2:15:06
 */
public class DBUtils {
    //定义一个获取数据库连接的方法
    private static DataSource ds = null;
    private static Connection conn = null;

    public static Connection getConnection() {

        /*//利用C3P0创建数据源
        try {
            if ( conn == null || conn.isClosed()){
                if (ds == null)
                    ds = new ComboPooledDataSource("dbInfo");
                conn = ds.getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
/*        try {
            if (conn == null || conn.isClosed()) {*/
                try {
                    Class.forName("org.gjt.mm.mysql.Driver");
                } catch (ClassNotFoundException e) {
                    System.out.println("Error: unable to load driver class!");
                    e.printStackTrace();
                }
                try {
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/sh_books?useUnicode=true&characterEncoding=UTF-8", "root", "19930926");

                } catch (SQLException e) {
                    System.out.println("Error: unable to get connection!");
                    e.printStackTrace();
                }
            /*}
            if (conn == null || conn.isClosed()) {
                getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        return conn;
    }
}
