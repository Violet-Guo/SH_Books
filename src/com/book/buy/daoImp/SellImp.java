package com.book.buy.daoImp;

import com.book.buy.dao.SellDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.SellVo;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by violet on 2015/10/28.
 */
public class SellImp implements SellDao {
    private QueryRunner runner = null;
    private Connection conn = null;

    public SellImp(){
        runner = new QueryRunner();
        conn = DBUtils.getConnection();
    }


    @Override
    public void addSell(SellVo sellvo) throws SQLException {
        String sql = "insert into sell(userID, bookID, time) values(?, ?, ?)";
        runner.update(conn, sql, sellvo.getUserid(), sellvo.getBookid(), sellvo.getTime());
    }

    @Override
    public void upBook(int bid) throws SQLException {
        String sql = "update book set state = 1 where id = ?";
        runner.update(conn, sql, bid);

    }

    @Override
    public void downBook(int bid) throws SQLException {
        String sql = "update book set state = 0 where id = ?";
        runner.update(conn, sql, bid);
    }

    @Override
    public void close() {
        try {
            if(conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
