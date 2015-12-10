package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.ComplainDao;
import com.book.buy.dao.FeedBackDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by violet on 2015/12/7.
 */
@WebServlet(name = "ManagerIndexServlet", urlPatterns = "/AdminIndex")
public class ManagerIndexServlet extends HttpServlet {
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

        List<ComplainVo> complis = new ArrayList<>();
        List<ComplainVo> appeallis = new ArrayList<>();
        List<FeedBackVo> fedlis = new ArrayList<>();
        List<BookVo> booklis = new ArrayList<>();
        List<UserVo> userlis = new ArrayList<>();


        ComplainVo compvo = new ComplainVo();
        UserVo uservo = new UserVo();
        BookVo bookvo = new BookVo();

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        FeedBackDao feddao = FeedBackDaoImplFactory.getFeedBackDaoImpl();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();

        try {

            complis = compdao.getAllComp();
            appeallis = compdao.getAllAppeal();
            fedlis = feddao.showFeedBack();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < complis.size(); i++){
            ComplainVo comp = (ComplainVo)complis.get(i);

            try {

                bookvo = bookdao.findById(comp.getBookid());
                uservo = userdao.findUserById(bookvo.getUserID());

                if (userlis.contains(uservo)){
                    continue;
                }
                else {
                    userlis.add(uservo);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        request.getSession().setAttribute("allcompuser", userlis);
        request.getSession().setAttribute("allcomp", complis);
        request.getSession().setAttribute("allappeal", appeallis);
        request.getSession().setAttribute("allfeedback", fedlis);

        compdao.close();
        feddao.close();
        bookdao.close();
        userdao.close();

        response.sendRedirect("/indexmanager");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
