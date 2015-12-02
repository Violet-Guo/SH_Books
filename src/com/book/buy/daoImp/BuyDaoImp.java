package com.book.buy.daoImp;

import com.book.buy.dao.BuyDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.BuyVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public class BuyDaoImp implements BuyDao {
    private QueryRunner queryRunner;
    private Connection conn;
    public BuyDaoImp(){
        queryRunner = new QueryRunner();
        conn = DBUtils.getConnection();
    }
    @Override
    public void addBuy(BuyVo buyVo) throws SQLException {
        //插入sureTime和moneyTime初始值为空，判断为空那么即为未确认和未付款的状态，但是填写插入时间
        String sql = "insert into buy (orderID,userID,time,sureTime,moneyTime) values(?,?,?,?,?)";
        //其中的orderID可以自动生成，所以不需要插入，但是插入后需要读出
        queryRunner.update(conn,sql,buyVo.getOrderID(),buyVo.getUserID(),buyVo.getTime(),buyVo.getSureTime(),buyVo.getMoneyTime());
    }

    @Override
    public void delBuyByOrderID(int orderID) throws SQLException {
        String sql = "delete from buy where orderID="+orderID;
        queryRunner.update(conn,sql);
    }

    @Override
    public void delBuyByUserID(int userID) throws SQLException {
        String sql = "delete from buy where userID="+userID;
        queryRunner.update(conn,sql);
    }

    @Override
    public BuyVo getBuyByUserID(int userID) throws SQLException {
        String sql = "select * from buy where userID="+userID;
        return queryRunner.query(conn,sql,new BeanHandler<BuyVo>(BuyVo.class));
    }

    @Override
    public BuyVo getBuyByOrderID(int orderID) throws SQLException {
        String sql = "select * from buy where orderID="+orderID;
        return queryRunner.query(conn,sql,new BeanHandler<BuyVo>(BuyVo.class));
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }
}
