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

        FeedBackDao feeddao = FeedBackDaoImplFactory.getFeedBackDaoImpl();
        List<FeedBackVo> feedlis = new ArrayList<>();

        try {
            feedlis = feeddao.showFeedBack();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("allfeedback", feedlis);

        feeddao.close();
        response.sendRedirect("/allfedback");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
