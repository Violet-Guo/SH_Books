package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.ComplainDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.BookVo;
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
 * Created by violet on 2015/11/26.
 */
@WebServlet(name = "GetCompDetilServlet", urlPatterns = "/getcompdetil")
public class GetCompDetilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String compid = request.getParameter("compid");
        int id = Integer.parseInt(compid);

        UserVo uservo = new UserVo();
        BookVo bookvo = new BookVo();
        ComplainVo compvo = new ComplainVo();

        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        try {
            compvo = compdao.getCompById(id);
            uservo = userdao.findUserById(compvo.getUserid());
            bookvo = bookdao.findById(compvo.getBookid());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("compuser", uservo);
        request.getSession().setAttribute("compdetil", compvo);
        request.getSession().setAttribute("compbook", bookvo);

        bookdao.close();
        compdao.close();

        response.sendRedirect("/compdetil");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
