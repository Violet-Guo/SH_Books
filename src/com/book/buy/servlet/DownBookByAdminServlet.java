package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.ComplainDao;
import com.book.buy.dao.InformDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by violet on 2015/11/26.
 */
@WebServlet(name = "DownBookByAdminServlet", urlPatterns = "/downbookbyadmin")
public class DownBookByAdminServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";
        int compnum = 0;

        //校验管理员登陆状态
        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        //获得jsp页面传来的参数
        String bookid = request.getParameter("bookid");
        String compid = request.getParameter("compid");

        //把参数转换成int型
        int bid = Integer.parseInt(bookid);
        int cid = Integer.parseInt(compid);

        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        InformDao informdao = InformDaoImplFactory.getInformDaoImpl();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();

        UserVo uservo = new UserVo();
        ComplainVo compvo = new ComplainVo();
        InformVo informvo = new InformVo();
        BookVo bookvo = new BookVo();

        //根据参数从数据库中取出对应的书籍和投诉信息，进行修改和update
        try {
            bookvo = bookdao.findById(bid);
            compvo = compdao.getCompById(cid);
            uservo = userdao.findUserById(bookvo.getUserID());

            compnum = uservo.getComplainNum();
            compnum = compnum+1;

            uservo.setComplainNum(compnum);
            bookvo.setState(3);
            compvo.setState(1);

            userdao.updateUser(uservo);
            bookdao.updateBook(bookvo);
            compdao.updateComp(compvo);

            //把投诉处理插入到inform表中
            informvo.setType(3);;
            informvo.setUserID(bookvo.getUserID());
            informvo.setNum(compvo.getId());
            Date date = new Date();
            String time = NewDate.getDateTime(date);
            informvo.setTime(time);
            informdao.addInform(informvo);


            //投诉达到三次，用户被冻结
            if (compnum == 3){
                informvo.setUserID(bookvo.getUserID());
                informvo.setType(5);
                informvo.setNum(0);
                Date date1 = new Date();
                String time1 = NewDate.getDateTime(date1);
                informvo.setTime(time1);
                informdao.addInform(informvo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            userdao.close();
            bookdao.close();
            compdao.close();
            informdao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


        href = "/getcompdetil?compid="+cid;
        out.print("<script language='javascript'>alert('下架成功');"
                + "window.location.href='" + href + "';</script>");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
         this.doPost(request, response);
    }
}
