package com.book.buy.servlet;

import com.book.buy.dao.UserDao;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/13.
 */
@WebServlet(name = "ChangePassManagerServlet", urlPatterns = "/changepassmanager")
public class ChangePassManagerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String oldpass = request.getParameter("oldpass");
        String newpass1 = request.getParameter("newpass1");
        String newpass2 = request.getParameter("newpass2");
        String username = (String)request.getSession().getAttribute("username");

        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();
        UserVo uservo = new UserVo();

        try {
            uservo = userdao.findUserByName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!oldpass.equals(uservo.getPassword())){
            userdao.close();
            response.sendRedirect("");
        }

        if(!newpass1.equals(newpass2)){
            userdao.close();
            response.sendRedirect("");
        }





    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
