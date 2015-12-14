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
import com.book.buy.vo.BookVo;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.InformVo;
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
import java.util.Date;

/**
 * Created by violet on 2015/11/11.
 */
@WebServlet(name = "AddCompServlet", urlPatterns = "/addcomp")
public class AddCompServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        //验证用户的登陆状态
        UserVo user1 = (UserVo)request.getSession().getAttribute("user");
        if (user1 == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }

        UserVo user = new UserVo();
        user = (UserVo)request.getSession().getAttribute("user");

        ComplainVo compvo = new ComplainVo();
        InformVo informvo = new InformVo();
        BookVo bookvo = new BookVo();               //被投诉的书籍
        UserVo usered = new UserVo();               //被投诉人

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();
        InformDao informdao = InformDaoImplFactory.getInformDaoImpl();
        int compnum = 0;   //被投诉者被投诉的次数

        //从jsp页面拿到参数
        String id = (String)request.getParameter("bookid");
        int bookid = Integer.parseInt(id);
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

            if (compnum == 3){
                informvo.setUserID(bookvo.getUserID());
                informvo.setType(5);
                informvo.setNum(0);
                Date date = new Date();
                String time = NewDate.getDateTime(date);
                informvo.setTime(time);
                informdao.addInform(informvo);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        try {
            compdao.close();
            userdao.close();
            bookdao.close();
            informdao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        href = "/ShowBookDetail?bookID=";
        out.print("<script language='javascript'>alert('发布投诉成功');"
                + "window.location.href='" + href + "" + id +"';</script>");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
