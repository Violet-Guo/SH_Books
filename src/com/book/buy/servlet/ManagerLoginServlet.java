package com.book.buy.servlet;

import com.book.buy.dao.ManagerDao;
import com.book.buy.factory.ManagerDaoImpFactory;
import com.book.buy.vo.ManagerVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/4.
 * 用于管理员登陆时
 * arguement:username、password
 */
@WebServlet(name = "ManagerLoginServlet", urlPatterns = "/managerlogin")
public class ManagerLoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String username = request.getParameter("username");
        String pwd = request.getParameter("pwd");

        ManagerDao mdao = ManagerDaoImpFactory.getManagerDaoImp();
        ManagerVo mvo = new ManagerVo();

        try {
            mvo = mdao.getPwdByName(username);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (pwd.equals(mvo.getPassword())){
            request.getSession().setAttribute("username", username);
            request.getSession().setAttribute("userid", mvo.getId());

            mdao.close();

            response.sendRedirect("pages/managerPage/login/loginSucc.jsp");
        }
        else{
            mdao.close();
            response.sendRedirect("pages/managerPage/login/loginFailure.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
