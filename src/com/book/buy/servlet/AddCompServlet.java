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
        BookVo bookvo = new BookVo();    //被投诉的书籍
        UserVo usered = new UserVo();    //被投诉人
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();
        int compnum = 0;   //被投诉者被投诉的次数

        int bookid = Integer.parseInt(request.getParameter("bookid"));
        String complain = request.getParameter("comp");

        compvo.setUserid(user.getId());
        //UserID是投诉人的ID，不是被投诉者的ID
        compvo.setBookid(bookid);
        compvo.setDescription(complain);

        try {
            compdao.addComp(compvo);               //添加投诉
            bookvo = bookdao.findById(bookid);     //通过书的ID找到这本书
            usered = userdao.findUserById(bookvo.getUserID());       //通过书籍的发布者的ID，查找到这个人
            compnum = usered.getComplainNum();     //获得发布书籍者的被投诉次数
            compnum = compnum+1;                   //被投诉次数+1
            usered.setComplainNum(compnum);
            userdao.updateUser(usered);            //更新到用户表中

        } catch (SQLException e) {
            e.printStackTrace();
        }

        compdao.close();
        userdao.close();
        bookdao.close();

        response.sendRedirect("serverPage/state/addCompSucc.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
