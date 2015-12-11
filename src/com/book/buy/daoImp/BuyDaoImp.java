package com.book.buy.daoImp;

import com.book.buy.dao.BuyDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.BuyVo;
import com.book.buy.vo.UserVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.DoubleSummaryStatistics;
import java.util.List;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public class BuyDaoImp implements BuyDao {
    private QueryRunner queryRunner;
    private Connection conn;
    public static final int ISBUYER = 1;
    public static final int ISSELLER = 0;
    public BuyDaoImp(){
        queryRunner = new QueryRunner();
        conn = DBUtils.getConnection();
    }
    @Override
    public void addBuy(BuyVo buyVo) throws SQLException {
        //插入sureTime和moneyTime初始值为空，判断为空那么即为未确认和未付款的状态，但是填写插入时间
        String sql = "insert into buy (userID,time,sureTime,moneyTime,hasEva) values(?,?,?,?,?)";
        //其中的orderID可以自动生成，所以不需要插入，但是插入后需要读出
        queryRunner.update(conn,sql,buyVo.getUserID(),buyVo.getTime(),buyVo.getSureTime(),buyVo.getMoneyTime(),buyVo.getHasEva());
    }

    @Override
    public void updateByOrderID(BuyVo buyVo,int orderID) throws SQLException {
        String sql = "update buy set sureTime=?,hasEva=? where orderID=?";
        queryRunner.update(conn,sql,buyVo.getSureTime(),buyVo.getHasEva(),orderID);
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
    public List<BuyVo> getBuyByUserID(int userID,int begin,int count) throws SQLException {
        String sql = "select * from buy where userID=? order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getBuyByUserID(UserVo userVo, int begin, int count, int state) throws SQLException {
        String sql = "";
        if(state==ISBUYER) {
            sql = "select * from buy b,orderform o,book,`user` u where"
                    + " b.userID=? and b.orderID=o.orderID and o.bookID=book.id"
                    + " and book.userID=u.id and u.id!=? order by b.time desc limit ?,?";
        }else if(state==ISSELLER){
            sql = "select * from buy b,orderform o,book,`user` u where"
                    + " b.userID=? and b.orderID=o.orderID and o.bookID=book.id"
                    + " and book.userID=u.id and u.id=? order by b.time desc limit ?,?";
        }
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),userVo.getId(),begin,count);
    }

    @Override
    public Long getCountByUserID(int userID) throws SQLException {
        String sql = "select count(*) from buy where userID=?";
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userID);
    }

    @Override
    public Long getCountByUserID(UserVo userVo, int state) throws SQLException {
        String sql = "";
        if(state==ISBUYER) {
            sql = "select count(*) from buy b,orderform o,book,`user` u where"
                    + " b.userID=? and b.orderID=o.orderID and o.bookID=book.id"
                    + " and book.userID=u.id and u.id!=? order by b.time desc";
        }else if(state==ISSELLER){
            sql = "select count(*) from buy b,orderform o,book,`user` u where"
                    + " b.userID=? and b.orderID=o.orderID and o.bookID=book.id"
                    + " and book.userID=u.id and u.id=? order by b.time desc";
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }

    @Override
    public BuyVo getBuyByOrderID(int orderID) throws SQLException {
        String sql = "select * from buy where orderID="+orderID;
        return queryRunner.query(conn,sql,new BeanHandler<BuyVo>(BuyVo.class));
    }
    //-----------------waitMoney先省略
    @Override
    public List<BuyVo> getWaitMoneyByUserID(int userID, int begin, int count) throws SQLException {
        String sql = "select * from buy where userID=? and time=moneyTime order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getWaitMoneyByUserID(UserVo userVo, int begin, int count, int state) throws SQLException {

        return null;
    }

    //----------不用的方法---
    @Override
    public Long getWaitMoneyCount(int userID) throws SQLException {
        String sql = "select count(*) from buy where userID=? and time=moneyTime";
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userID);
    }

    @Override
    public Long getWaitMoneyCount(UserVo userVo, int state) throws SQLException {
        return null;
    }
    //-----------END

    @Override
    public List<BuyVo> getWaitSureByUserID(int userID, int begin, int count) throws SQLException {
        String sql = "select * from buy where userID=? and isNull(sureTime) order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getWaitSureByUserID(UserVo userVo, int begin, int count, int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            sql = "select * from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and ISNULL(b.sureTime) and o.bookID=book.id"
                    +" and book.userID=u.id and u.id!=? order by b.time desc limit ?,?";
        }else if(state == ISSELLER){
            sql = "select * from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and ISNULL(b.sureTime) and o.bookID=book.id"
                    +" and book.userID=u.id and u.id=? order by b.time desc limit ?,?";
        }
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),userVo.getId(),begin,count);
    }

    @Override
    public Long getWaitSureCount(int userID) throws SQLException {
        String sql = "select count(*) from buy where userID=? and isNull(sureTime)";
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userID);
    }

    @Override
    public Long getWaitSureCount(UserVo userVo, int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            sql = "select count(*) from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and ISNULL(b.sureTime) and o.bookID=book.id"
                    +" and book.userID=u.id and u.id!=? order by b.time desc";
        }else if(state == ISSELLER){
            sql = "select count(*) from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and ISNULL(b.sureTime) and o.bookID=book.id"
                    +" and book.userID=u.id and u.id=? order by b.time desc";
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }

    @Override
    public int getLastInsertID() throws SQLException {
        String sql = "select last_insert_id() as id";
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sql);
        int id = 0;
        if(resultSet.first()){
            id = resultSet.getInt("id");
        }
        return id;
    }

    @Override
    public List<BuyVo> getWaitEvaByUserID(int userID, int begin, int count) throws SQLException {
        String sql = "SELECT * from buy b WHERE b.userID=? and !isNull(b.sureTime) AND b.hasEva=0 order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getWaitEvaByUserID(UserVo userVo,int begin,int count,int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            sql = "select * from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and !ISNULL(b.sureTime) and b.hasEva=0 and o.bookID=book.id"
                    +" and book.userID=u.id and u.id!=? order by b.time desc limit ?,?";
        }else if(state == ISSELLER){
            sql = "select * from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and !ISNULL(b.sureTime) and b.hasEva=0 and o.bookID=book.id"
                    +" and book.userID=u.id and u.id=? order by b.time desc limit ?,?";
        }
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),userVo.getId(),begin,count);
    }

    @Override
    public Long getWaitEvaCount(UserVo userVo, int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            sql = "select count(*) from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and !ISNULL(b.sureTime) and b.hasEva=0 and o.bookID=book.id"
                    +" and book.userID=u.id and u.id!=? order by b.time desc";
        }else if(state == ISSELLER){
            sql = "select count(*) from buy b,orderform o,book,`user` u where b.userID=?"
                    +" and b.orderID=o.orderID and !ISNULL(b.sureTime) and b.hasEva=0 and o.bookID=book.id"
                    +" and book.userID=u.id and u.id=? order by b.time desc";
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }

    @Override
    public Long getWaitEvaCount(int userID) throws SQLException {
        String sql = "SELECT count(*) from buy b WHERE b.userID=? AND b.hasEva=0";
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userID);
    }

    @Override
    public Double getBuyPrice(int orderID) throws SQLException {
        String sql = "SELECT SUM(bo.price*o.bookNum) as price from orderform o,book bo WHERE o.orderID=? and o.bookID=bo.id;";
        return (Double) queryRunner.query(conn,sql,new ScalarHandler(),orderID);
    }

    @Override
    public void close() throws SQLException {
        conn.close();
    }
}
