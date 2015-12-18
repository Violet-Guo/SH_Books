package com.book.buy.daoImp;

import com.book.buy.dao.BuyDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.BuyVo;
import com.book.buy.vo.UserVo;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
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
    public List<BuyVo> getOrderSql(Boolean isBuy,int state,UserVo userVo,Integer begin,Integer count) throws SQLException {
        String sql = "";
        ArrayList<String> sqls = new ArrayList<>();
        sql = "select * from buy b,orderform o,book,`user` u where" +
                " b.orderID=o.orderID and o.bookID=book.id" +
                " and book.userID=u.id";
        if(isBuy==null || isBuy){
            if(isBuy==null)
                sqls.add("u.id=?");
            else
                sqls.add("u.id!=?");
            sqls.add("b.userID=?");
            //--------------------------1代表待确认，2代表待评价
            switch (state){
                case 1:
                    sqls.add("ISNULL(b.sureTime)");
                    break;
                case 2:
                    sqls.add("!ISNULL(b.sureTime)");
                    sqls.add("b.hasEva=0");
                    break;
                case 3:
                    break;
            }
        }else {
            sqls.add("u.id=?");
            switch (state){
                case 1:
                    sqls.add("ISNULL(b.sureTime)");
                    break;
                case 2:
                    sqls.add("!ISNULL(b.sureTime)");
                    sqls.add("b.hasEva=0");
                    break;
                case 3:
                    break;
            }
        }

        int h = sqls.size();
        for(int i=0;i<h;i++){
            if(isBuy==null){
                if(i==0){
                    sql = sql+" and ( "+sqls.get(i);
                }else if(i==1){
                    sql = sql+" or "+sqls.get(i)+" ) ";
                }else {
                    sql = sql+" and "+sqls.get(i);
                }
            }else {
                sql = sql + " and " + sqls.get(i);
            }
        }

        sql += " order by b.time desc limit ?,?";

        if (isBuy == null || isBuy) {
            return queryRunner.query(conn, sql, new BeanListHandler<BuyVo>(BuyVo.class), userVo.getId(), userVo.getId(), begin, count);
        } else {
            return queryRunner.query(conn, sql, new BeanListHandler<BuyVo>(BuyVo.class), userVo.getId(), begin, count);
        }
    }
    @Override
    public Long getCountSql(Boolean isBuy,int state,UserVo userVo) throws SQLException {
        String sql = "";
        ArrayList<String> sqls = new ArrayList<>();

        sql = "select count(*) from buy b,orderform o,book,`user` u where"
                + " b.orderID=o.orderID and o.bookID=book.id"
                + " and book.userID=u.id";

        if(isBuy==null || isBuy){
            if(isBuy==null)
                sqls.add("u.id=?");
            else
                sqls.add("u.id!=?");
            sqls.add("b.userID=?");
            //--------------------------1代表待确认，2代表待评价
            switch (state){
                case 1:
                    sqls.add("ISNULL(b.sureTime)");
                    break;
                case 2:
                    sqls.add("!ISNULL(b.sureTime)");
                    sqls.add("b.hasEva=0");
                    break;
                case 3:
                    break;
            }
        }else {
            sqls.add("u.id=?");
            switch (state){
                case 1:
                    sqls.add("ISNULL(b.sureTime)");
                    break;
                case 2:
                    sqls.add("!ISNULL(b.sureTime)");
                    sqls.add("b.hasEva=0");
                    break;
                case 3:
                    break;
            }
        }

        int h = sqls.size();
        for(int i=0;i<h;i++){
            if(isBuy==null){
                if(i==0){
                    sql = sql+" and ( "+sqls.get(i);
                }else if(i==1){
                    sql = sql+" or "+sqls.get(i)+" ) ";
                }else {
                    sql = sql+" and "+sqls.get(i);
                }
            }else {
                sql = sql + " and " + sqls.get(i);
            }
        }

        sql += " order by b.time desc";

        if (isBuy == null || isBuy) {
            return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
        } else {
            return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId());
        }
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

    /*@Override
    public List<BuyVo> getBuyByUserID(int userID,int begin,int count) throws SQLException {
        String sql = "select * from buy where userID=? order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }*/

    /*@Override
    public List<BuyVo> getBuyByUserID(UserVo userVo, int begin, int count, int state) throws SQLException {
        String sql = "";
        if(state==ISBUYER) {
            String a[] = new String[2];
            a[0] = "b.userID=?";
            a[1] = "u.id!=?";
            //sql = getOrderSql(2,a);
        }else if(state==ISSELLER){
            String a[] = new String[1];
            a[0] = "u.id=?";
            ///sql = getOrderSql(1,a);
            return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),begin,count);
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
            String a[] = new String[2];
            a[0] = "b.userID=?";
            a[1] = "u.id!=?";
            sql = getCountSql(2,a);
        }else if(state==ISSELLER){
            String a[] = new String[1];
            a[0] = "u.id=?";
            sql = getCountSql(1,a);
            return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId());
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }*/

    @Override
    public BuyVo getBuyByOrderID(int orderID) throws SQLException {
        String sql = "select * from buy where orderID="+orderID;
        return queryRunner.query(conn,sql,new BeanHandler<BuyVo>(BuyVo.class));
    }


    /*@Override
    public List<BuyVo> getWaitSureByUserID(int userID, int begin, int count) throws SQLException {
        String sql = "select * from buy where userID=? and isNull(sureTime) order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getWaitSureByUserID(UserVo userVo, int begin, int count, int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            sql = getOrderSql(true,false,1);
        }else if(state == ISSELLER){
            sql = getOrderSql(false,false,1);
            return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),begin,count);
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
            sql = getOrderSql(true,true,1);
        }else if(state == ISSELLER){
            sql =getOrderSql(false,true,1);
            return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId());
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }*/

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

    /*@Override
    public List<BuyVo> getWaitEvaByUserID(int userID, int begin, int count) throws SQLException {
        String sql = "SELECT * from buy b WHERE b.userID=? and !isNull(b.sureTime) AND b.hasEva=0 order by time desc limit ?,?";
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userID,begin,count);
    }

    @Override
    public List<BuyVo> getWaitEvaByUserID(UserVo userVo,int begin,int count,int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            String a[] = new String[4];
            a[0] = "!ISNULL(b.sureTime)";
            a[1] = "u.id!=?";
            a[2] = "b.userID=?";
            a[3] = "b.hasEva=0";
            //sql = getOrderSql(4,a);
        }else if(state == ISSELLER){
            String a[] = new String[3];
            a[0] = "!ISNULL(b.sureTime)";
            a[1] = "u.id=?";
            a[2] = "b.hasEva=0";
            //sql = getOrderSql(3,a);
            return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),begin,count);
        }
        return queryRunner.query(conn,sql,new BeanListHandler<BuyVo>(BuyVo.class),userVo.getId(),userVo.getId(),begin,count);
    }

    @Override
    public Long getWaitEvaCount(UserVo userVo, int state) throws SQLException {
        String sql = "";
        if(state == ISBUYER){
            String a[] = new String[4];
            a[0] = "!ISNULL(b.sureTime)";
            a[1] = "u.id!=?";
            a[2] = "b.userID=?";
            a[3] = "b.hasEva=0";
            sql = getCountSql(4, a);
        }else if(state == ISSELLER){
            String a[] = new String[3];
            a[0] = "!ISNULL(b.sureTime)";
            a[1] = "u.id=?";
            a[2] = "b.hasEva=0";
            sql = getCountSql(3, a);
            return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId());
        }
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userVo.getId(),userVo.getId());
    }

    @Override
    public Long getWaitEvaCount(int userID) throws SQLException {
        String sql = "SELECT count(*) from buy b WHERE b.userID=? AND b.hasEva=0";
        return (Long) queryRunner.query(conn,sql,new ScalarHandler(),userID);
    }*/

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
