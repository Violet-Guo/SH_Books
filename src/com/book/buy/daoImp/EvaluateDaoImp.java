package com.book.buy.daoImp;

import com.book.buy.dao.EvaluateDao;
import com.book.buy.utils.DBUtils;
import com.book.buy.vo.EvaluateVo;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by violet on 2015/11/21.
 * changed by violet on 2015/12/9 删除orderId
 */
public class EvaluateDaoImp implements EvaluateDao {

    private QueryRunner runner = null;
    private Connection conn = null;

    public EvaluateDaoImp(){
        runner = new QueryRunner();
        conn = DBUtils.getConnection();
    }

    @Override
    public List<EvaluateVo> getAllEvaluate(int sellUserID, int begin, int count) throws SQLException {
        String sql = "select UserID, sellUserID, time, content from evaluate where sellUserID = ? " +
                "limit ?, ?";
        return runner.query(conn, sql, new BeanListHandler<EvaluateVo>(EvaluateVo.class), sellUserID);
    }

    @Override
    public void addEvaluate(EvaluateVo evaluatevo) throws SQLException {
        String sql = "insert into evaluate(UserID, sellUserID, time, content) " +
                "values(?, ?, ?, ?);";
        runner.update(conn, sql, evaluatevo.getUserID(), evaluatevo.getSellUserID(),
                evaluatevo.getTime(), evaluatevo.getContent());

    }

    @Override
    public long getEvaBySellAndUser(int sellUserID, int userID) throws SQLException {
        String sql = "select count(*) from evaluate where sellUserID = ? and userID = ?";
        return (long) runner.query(conn, sql, new ScalarHandler(), sellUserID, userID);
    }


}
