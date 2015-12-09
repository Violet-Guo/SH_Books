package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

        List<ComplainVo> complis = new ArrayList<>();
        List<BookVo> booklis = new ArrayList<>();
        List<UserVo> userlis = new ArrayList<>();
        complis = (ArrayList)request.getSession().getAttribute("allcomp");

        UserVo uservo = new UserVo();
        UserDao userdao = UserDaoImpFactory.getUserDaoImpl();

        BookVo bookvo = new BookVo();
        BookDao bookdao = BookDaoImpFactory.getBookDaoImpl();



        for (int i = 0; i < complis.size(); i++){
            ComplainVo compvo = (ComplainVo)complis.get(i);

            try {
                bookvo = bookdao.findById(compvo.getBookid());
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

        int everyPageNum = 5;
        Paging paging = new Paging(everyPageNum,request,userlis.size(),"/getalluser?");
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
