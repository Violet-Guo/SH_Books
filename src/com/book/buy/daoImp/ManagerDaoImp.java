package com.book.buy.daoImp;

import com.book.buy.dao.ManagerDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.ManagerVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/4.
 */
public class ManagerDaoImp implements ManagerDao{
    private QueryRunner runner = null;
    private Connection conn = null;

    public ManagerDaoImp(){
        runner = new QueryRunner();
        conn = DBUtils.getConnection();
    }

    @Override
    public void addManager(ManagerVo manager) throws SQLException {
        String sql = "insert into admin(userName, password) values(?, ?)";
        runner.update(conn, sql, manager.getUsername(), manager.getPassword());
    }

    @Override
    public void deleteManager(int id) throws SQLException {
        String sql = "delete from admin where id = ?";
        runner.update(conn, sql, id);
    }

    @Override
    public ManagerVo getPwdByName(String username) throws SQLException {
        String sql = "select id, userName, password from admin where userName = ?";
        return runner.query(conn, sql, new BeanHandler<ManagerVo>(ManagerVo.class), username);
    }

    @Override
    public void updateManager(ManagerVo managervo) throws SQLException {
        String sql = "update admin set userName = ?, password = ? where id = ?";
        runner.update(conn,sql, managervo.getUsername(), managervo.getPassword(), managervo.getId());
    }


    @Override
    public void close() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
