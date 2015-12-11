package com.book.buy.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by violet on 2015/12/9.
 * 管理员退出登陆
 */
@WebServlet(name = "quitAdminServlet", urlPatterns = "/quitadmin")
public class quitAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        //把session中存放的管理员信息清除
        request.getSession().removeAttribute("admin");

        response.sendRedirect("/loginmanager");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
