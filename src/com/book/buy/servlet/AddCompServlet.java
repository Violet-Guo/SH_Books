package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.UserVo;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/11.
 */
@WebServlet(name = "AddCompServlet", urlPatterns = "/addcomp")
public class AddCompServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();

        UserVo user1 = (UserVo)request.getSession().getAttribute("user");
        if (user1 == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }

        UserVo user = new UserVo();
        user = (UserVo)request.getSession().getAttribute("user");

        ComplainVo compvo = new ComplainVo();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        int bookid = Integer.parseInt(request.getParameter("bookid"));
        String complain = request.getParameter("comp");

        compvo.setUserid(user.getId());
        compvo.setBookid(bookid);
        compvo.setDescription(complain);

        try {
            compdao.addComp(compvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        compdao.close();

        response.sendRedirect("serverPage/state/addCompSucc.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
