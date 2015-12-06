package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;

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
 * Created by violet on 2015/12/1.
 */
@WebServlet(name = "PublishedBooksServlet", urlPatterns = "/publishedbooks")
public class PublishedBooksServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        UserVo user = (UserVo)request.getSession().getAttribute("user");

        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        List<BookVo> booklis = new ArrayList<>();

        try {
            booklis = bookdao.findByUserId(user.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("publishedbook", booklis);


        bookdao.close();
        response.sendRedirect("/publishedbook");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
