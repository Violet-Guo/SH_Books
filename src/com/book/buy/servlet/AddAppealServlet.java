package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/12/1.
 */
@WebServlet(name = "AddAppealServlet", urlPatterns = "/addappeal")
public class AddAppealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        UserVo user = new UserVo();
        user = (UserVo)request.getSession().getAttribute("user");

        ComplainVo compvo = new ComplainVo();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        int bookid = Integer.parseInt(request.getParameter("bookid"));
        String appeal = request.getParameter("appeal");

        compvo.setUserid(user.getId());
        compvo.setBookid(bookid);
        compvo.setDescription(appeal);

        try {
            compdao.addAppeal(compvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.sendRedirect("");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
