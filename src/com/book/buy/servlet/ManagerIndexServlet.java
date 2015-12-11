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
 * 拿到主页显示的投诉、申诉、用户投诉情况、反馈信息
 */
@WebServlet(name = "ManagerIndexServlet", urlPatterns = "/AdminIndex")
public class ManagerIndexServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        //校验管理员的登陆状态
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

        //查找到所有的投诉、反馈和申诉信息
        try {

            complis = compdao.getAllComp();
            appeallis = compdao.getAllAppeal();
            fedlis = feddao.showFeedBack();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        //根据投诉信息找到所有被投诉过的用户的详情
        for (int i = 0; i < complis.size(); i++){
            ComplainVo comp = (ComplainVo)complis.get(i);

            //通过投诉信息中的bookid找到被投诉的这本书，通过被投诉的这本书的UserID查找到卖家
            try {

                bookvo = bookdao.findById(comp.getBookid());
                uservo = userdao.findUserById(bookvo.getUserID());

                //若这个卖家已经在userlis中，不放入list中
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

        //把拿到的数据放在session中
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
