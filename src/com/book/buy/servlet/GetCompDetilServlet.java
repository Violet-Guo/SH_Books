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
@WebServlet(name = "GetCompDetilServlet", urlPatterns = "/getcompdetil")
public class GetCompDetilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String compid = request.getParameter("compid");
        int id = Integer.parseInt(compid);
        BookVo bookvo = new BookVo();
        ComplainVo compvo = new ComplainVo();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        try {
            compvo = compdao.getCompById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            bookvo = bookdao.findById(compvo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("compdetil", compvo);
        request.getSession().setAttribute("bookvo", bookvo);

        bookdao.close();
        compdao.close();

        response.sendRedirect("pages/managerPage/info/CompDetil.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
