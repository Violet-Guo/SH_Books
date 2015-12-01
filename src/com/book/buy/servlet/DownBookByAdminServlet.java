package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.vo.BookVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/26.
 */
@WebServlet(name = "DownBookByAdminServlet", urlPatterns = "/downbookbyadmin")
public class DownBookByAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String bookid = request.getParameter("bookid");
        int id = Integer.parseInt(bookid);
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        BookVo bookvo = new BookVo();

        try {
            bookvo = bookdao.findById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookvo.setState(4);

        try {
            bookdao.updateBook(bookvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookdao.close();

        response.sendRedirect("");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         this.doPost(request, response);
    }
}
