package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.ManagerVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/26.
 */
@WebServlet(name = "DownBookByAdminServlet", urlPatterns = "/downbookbyadmin")
public class DownBookByAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        String bookid = request.getParameter("bookid");
        String compid = request.getParameter("compid");

        int bid = Integer.parseInt(bookid);
        int cid = Integer.parseInt(compid);

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        ComplainVo compvo = new ComplainVo();
        BookVo bookvo = new BookVo();

        try {
            bookvo = bookdao.findById(bid);
            compvo = compdao.getCompById(cid);

            bookvo.setState(3);
            compvo.setState(1);

            bookdao.updateBook(bookvo);
            compdao.updateComp(compvo);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        bookdao.close();
        compdao.close();


        href = "/getcompdetil?compid="+cid;
        out.print("<script language='javascript'>alert('下架成功');"
                + "window.location.href='" + href + "';</script>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         this.doPost(request, response);
    }
}
