package com.book.buy.servlet;

import com.book.buy.dao.EvaluateDao;
import com.book.buy.factory.EvaluateDaoImpFactory;
import com.book.buy.vo.EvaluateVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by songchao on 15/11/28.
 */
@WebServlet(name = "EvaluationServlet",urlPatterns = "/evaluation")
public class EvaluationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登陆出现错误,重新登陆');window.location.href='/login';</script>");
            return;
        }

        String sellerID = request.getParameter("sellID");
        EvaluateDao evaluateDao = EvaluateDaoImpFactory.getEvaluateDaoImp();
        //evaluateDao.getAllEvaluate();
        EvaluateVo evaluateVo;
    }
}
