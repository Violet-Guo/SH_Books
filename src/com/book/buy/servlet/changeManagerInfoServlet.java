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

        PrintWriter out = response.getWriter();
        String href = "";

        //验证管理员登陆状态
        ManagerVo admin1 = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin1){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        //从jsp页面获得密码信息
        String oldpass = request.getParameter("oldpass");
        String newpass1 = request.getParameter("newpass1");
        String newpass2 = request.getParameter("newpass2");

        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        ManagerDao admindao = ManagerDaoImpFactory.getManagerDaoImp();

        //在后台对密码进行判断
        if (null == oldpass){
            admindao.close();
            href = "/changepassadmin";
            out.print("<script language='javascript'>alert('原密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
            return;
        } else if (null == newpass1){
            admindao.close();
            href = "/changepassadmin";
            out.print("<script language='javascript'>alert('新密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
            return;
        } else if (null == newpass2){
            admindao.close();
            href = "/changepassadmin";
            out.print("<script language='javascript'>alert('新密码不能为空');"
                    + "window.location.href='" + href + "';</script>");
            return;
        } else if (oldpass.equals(admin.getPassword())){     //原始密码与数据库中原始密码相同

            if (newpass1.equals(newpass2)){          //两次输入的新密码相同
                admin.setPassword(newpass1);

                try {
                    admindao.updateManager(admin);
                } catch (SQLException e) {
                    e.printStackTrace();
                }

                admindao.close();
                href = "/managerinfo";
                out.print("<script language='javascript'>alert('修改成功');"
                        + "window.location.href='" + href + "';</script>");
                return;

            } else {                                  //两次输入的新密码不通
                admindao.close();
                href = "/changepassadmin";
                out.print("<script language='javascript'>alert('两个新密码不一致');"
                        + "window.location.href='" + href + "';</script>");
                return;
            }
        } else {                               //原始密码与数据库中原始密码不相同
            admindao.close();
            href = "/changepassadmin";
            out.print("<script language='javascript'>alert('原密码不正确');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
