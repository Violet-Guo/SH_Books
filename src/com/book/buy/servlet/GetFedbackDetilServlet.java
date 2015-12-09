package com.book.buy.servlet;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.FeedBackVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/12/7.
 */
@WebServlet(name = "GetFedbackDetilServlet", urlPatterns = "/getfedbackdetil")
public class GetFedbackDetilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        UserVo admin = (UserVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
        }


        String userid = (String)request.getParameter("userid");
        String time = (String)request.getParameter("time");
        int id = Integer.parseInt(userid);

        FeedBackVo fed = new FeedBackVo();
        UserVo user = new UserVo();
        FeedBackDao feddao = FeedBackDaoImplFactory.getFeedBackDaoImpl();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();

        try {
            fed = feddao.findbyut(id, time);
            user = userdao.findUserById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("feddetil", fed);
        request.getSession().setAttribute("feduser", user);

        feddao.close();
        userdao.close();

        response.sendRedirect("/fedbackdetil");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
