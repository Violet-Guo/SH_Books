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
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/12/2.
 */
@WebServlet(name = "changeManagerInfoServlet", urlPatterns = "/changemanagerinfo")
public class changeManagerInfoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String oldpass = request.getParameter("oldpass");
        String newpass1 = request.getParameter("newpass1");
        String newpass2 = request.getParameter("newpass2");

        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        ManagerDao admindao = ManagerDaoImpFactory.getManagerDaoImp();
        PrintWriter out = response.getWriter();

        String href = "";

        if (null == oldpass){
            href = "/pages/managerPage/managerInfo/changeManagerInfo.jsp";
            out.print("<script language='javascript'>alert('原密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
        } else if (null == newpass1){
            href = "/pages/managerPage/managerInfo/changeManagerInfo.jsp";
            out.print("<script language='javascript'>alert('新密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
        } else if (null == newpass2){
            href = "/pages/managerPage/managerInfo/changeManagerInfo.jsp";
            out.print("<script language='javascript'>alert('新密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
        } else if (oldpass.equals(admin.getPassword())){
            if (newpass1.equals(newpass2)){
                admin.setPassword(newpass1);

                try {
                    admindao.updateManager(admin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                href = "/pages/managerPage/managerInfo/managerInfo.jsp";
                out.print("<script language='javascript'>alert('修改成功');"
                        + "window.location.href='" + href + "';</script>");

            } else {
                href = "/pages/managerPage/managerInfo/changeManagerInfo.jsp";
                out.print("<script language='javascript'>alert('两个新密码不一致');"
                        + "window.location.href='" + href + "';</script>");
            }
        } else {
            href = "/pages/managerPage/managerInfo/changeManagerInfo.jsp";
            out.print("<script language='javascript'>alert('原密码不正确');"
                    + "window.location.href='" + href + "';</script>");
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
