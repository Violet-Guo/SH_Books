package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/12/1.
 */
@WebServlet(name = "AddAppealServlet", urlPatterns = "/addappeal")
public class AddAppealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        //管理员登陆状态校验
        UserVo user1 = (UserVo)request.getSession().getAttribute("user");
        if (user1 == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }

        String href = "";

        UserVo user = new UserVo();
        user = (UserVo)request.getSession().getAttribute("user");

        ComplainVo compvo = new ComplainVo();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        //拿到jsp页面传来的参数
        int bookid = Integer.parseInt(request.getParameter("bookid"));
        String appeal = request.getParameter("appeal");

        compvo.setUserid(user.getId());
        compvo.setBookid(bookid);
        compvo.setDescription(appeal);

        //往数据库里添加申诉信息
        try {
            compdao.addAppeal(compvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        compdao.close();

        href = "/addAppeal";
        out.print("<script language='javascript'>alert('发布申诉成功');"
                + "window.location.href='" + href + "';</script>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
