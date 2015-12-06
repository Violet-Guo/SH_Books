package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.ComplainVo;

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
@WebServlet(name = "UpBookByAdminServlet", urlPatterns = "/upbookbyadmin")
public class UpBookByAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String bookid = request.getParameter("bookid");
        String appealid = request.getParameter("appealid");

        int bid = Integer.parseInt(bookid);
        int aid = Integer.parseInt(appealid);

        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        ComplainDao appealdao = ComplainDaoImpFactory.getCompDaoImp();

        BookVo bookvo = new BookVo();
        ComplainVo appealvo = new ComplainVo();

        try {
            bookvo = bookdao.findById(bid);
            appealvo = appealdao.getCompById(aid);

            bookvo.setState(1);
            appealvo.setState(1);

            bookdao.updateBook(bookvo);
            appealdao.updateComp(appealvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookdao.close();
        appealdao.close();

        response.sendRedirect("");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
