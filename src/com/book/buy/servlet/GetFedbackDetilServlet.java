package com.book.buy.servlet;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.vo.FeedBackVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by violet on 2015/12/7.
 */
@WebServlet(name = "GetFedbackDetilServlet", urlPatterns = "/getfedbackdetil")
public class GetFedbackDetilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String userid = (String)request.getParameter("userid");
        int id = Integer.parseInt(userid);

        FeedBackVo fed = new FeedBackVo();
        FeedBackDao feddao = FeedBackDaoImplFactory.getFeedBackDaoImpl();


        feddao.close();

        response.sendRedirect("/fedbackdetil");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
