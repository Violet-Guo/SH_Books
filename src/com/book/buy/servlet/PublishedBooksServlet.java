package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.utils.Paging;
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

        PrintWriter out = response.getWriter();

        //校验用户的登陆状态
        UserVo user = (UserVo)request.getSession().getAttribute("user");
        if (user == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }

        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        List<BookVo> booklis = new ArrayList<>();

        //拿到state的对应的值，一以便进行筛选
        String state = request.getParameter("state");
        if (state == null) {
            state = "all";
        }

        //根据state的值的不通，进行不通的查找
        try {
            if (state.equals("all")){
                booklis = bookdao.findByUserId(user.getId());
            } else if (state.equals("up")){
                booklis = bookdao.findAllByUserIDAndState(user.getId(), 1);
            } else if (state.equals("down")){
                booklis = bookdao.findAllByUserIDAndState(user.getId(), 2);
            } else if (state.equals("managerdown")){
                booklis = bookdao.findAllByUserIDAndState(user.getId(), 3);
            } else if (state.equals("selled")){
                booklis = bookdao.findAllByUserIDAndBookNum(user.getId(), 0);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //分页
        int everyPageNum = 5;
        Paging paging = new Paging(everyPageNum,request,booklis.size(),"/publishedbooks?");
        request.getSession().setAttribute("paging", paging);
        booklis = booklis.subList(paging.getStart(),paging.getEnd());

        //把拿到的数据放入session中
        request.getSession().setAttribute("publishedbook", booklis);

        bookdao.close();
        response.sendRedirect("/publishedbook");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
