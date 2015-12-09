package com.book.buy.servlet;

import com.book.buy.dao.EvaluateDao;
import com.book.buy.factory.EvaluateDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.EvaluateVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by chao on 2015/12/9.
 */
@WebServlet(name = "AddEvaServlet",urlPatterns = "/addeva")
public class AddEvaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        PrintWriter out = response.getWriter();
        if(userVo==null){
            out.print("<script>alert('登录状态出错，重新登录');window.location.href='/login';</script>");
            return;
        }
        String strUserID = request.getParameter("userID");
        String content = request.getParameter("content");
        Integer userID = Integer.valueOf(strUserID);

        EvaluateDao evaluateDao = EvaluateDaoImpFactory.getEvaluateDaoImp();

        EvaluateVo evaluateVo = new EvaluateVo();
        Date date = new Date();
        String time = NewDate.getDateTime(date);

        evaluateVo.setTime(time);
        evaluateVo.setContent(content);
        evaluateVo.setSellUserID(strUserID);
        evaluateVo.setUserID(userVo.getId()+"");

        try {

            evaluateDao.addEvaluate(evaluateVo);
            out.print("yes");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("no");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
