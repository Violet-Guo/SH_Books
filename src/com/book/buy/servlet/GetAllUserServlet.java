package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.ManagerVo;
import com.book.buy.vo.UserVo;

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
 * Created by violet on 2015/11/11.
 */
@WebServlet(name = "GetAllUserServlet", urlPatterns = "/getalluser")
public class GetAllUserServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        //校验管理员登陆状态
        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        List<ComplainVo> complis = (List)request.getSession().getAttribute("allcomp");
        List<UserVo> userlis = new ArrayList<>();
        int everyPageNum = 5;


        UserVo uservo = new UserVo();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();

        BookVo bookvo = new BookVo();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();

        //从jsp页面获取state的值
        String state = (String)request.getParameter("state");
        if (null == state){
            state = "all";
        }

        Paging paging = new Paging();

        for (int i = 0; i < complis.size(); i++){
            ComplainVo compvo = (ComplainVo)complis.get(i);

            try {
                /**
                 * 根据投诉中书的ID找到这本书，再根据找到的书来确定发布这本书的人的ID
                 * 因为一个卖家可能被投诉过次，多次每次找到一个卖家的时候要进行筛选，看是否已经放到List中了
                */
                bookvo = bookdao.findById(compvo.getBookid());
                uservo = userdao.findUserById(bookvo.getUserID());
                if (userlis.contains(uservo)){           //如果在userlis中已经有这个人了，则不再添加
                    continue;
                }
                else {
                    //如果userlis中没有这个人，我们根据state的状态来进行筛选
                    if (state.equals("all")){
                        userlis.add(uservo);
                    } else if (state.equals("yes") && uservo.getComplainNum() < 3){
                        userlis.add(uservo);
                    } else if (state.equals("no") && uservo.getComplainNum() >= 3){
                        userlis.add(uservo);
                    }

                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //根据状态不同分页处理
        if (state.equals("all")){
            paging = new Paging(everyPageNum,request,userlis.size(),"/getalluser?");
        } else if (state.equals("yes")){
            paging = new Paging(everyPageNum,request,userlis.size(),"/getalluser?state=yes&&");
        } else if (state.equals("no")) {
            paging = new Paging(everyPageNum,request,userlis.size(),"/getalluser?state=no&&");
        }

        //分页
        request.getSession().setAttribute("paging", paging);
        userlis = userlis.subList(paging.getStart(),paging.getEnd());

        request.getSession().setAttribute("allcompuser", userlis);

        bookdao.close();
        userdao.close();

        response.sendRedirect("/allusercomp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
