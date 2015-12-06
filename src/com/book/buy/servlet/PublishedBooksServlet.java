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
import java.io.PrintWriter;
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

        PrintWriter out = response.getWriter();

        if (user == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }

        String state = request.getParameter("state");
        if (state == null) {
            state = "all";
        }

        try {
            if (state.equals("all")){
                booklis = bookdao.findByUserId(user.getId());
            } else if (state.equals("up")){

            } else if (state.equals("down")){

            } else if (state.equals("selled")){

            }

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
