package com.book.buy.servlet;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.FeedBackVo;
import com.book.buy.vo.ManagerVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by violet on 2015/12/7.
 */
@WebServlet(name = "GetAllFedbackServlet", urlPatterns = "/getallfedback")
public class GetAllFedbackServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        //校验管理员登陆状态
        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        FeedBackDao feeddao = FeedBackDaoImplFactory.getFeedBackDaoImpl();
        List<FeedBackVo> feedlis = new ArrayList<>();

        //从数据库中拿出来所有的反馈信息
        try {
            feedlis = feeddao.showFeedBack();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //分页
        int everyPageNum = 5;
        Paging paging = new Paging(everyPageNum,request,feedlis.size(),"/getallfedback?");
        request.getSession().setAttribute("paging", paging);
        feedlis = feedlis.subList(paging.getStart(),paging.getEnd());

        request.getSession().setAttribute("allfeedback", feedlis);

        feeddao.close();
        response.sendRedirect("/allfedback");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
