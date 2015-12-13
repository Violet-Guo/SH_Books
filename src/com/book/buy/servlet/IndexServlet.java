package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.InformDao;
import com.book.buy.dao.MajorDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.factory.MajorDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.InformVo;
import com.book.buy.vo.MajorVo;
import com.book.buy.vo.UserVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chao on 2015/11/9.
 */
@WebServlet(name = "IndexServlet",urlPatterns = "/index")
public class IndexServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //------获取用户信息
        UserVo userVo = (UserVo) req.getSession().getAttribute("user");
        //--------------------------------这里获取首页需要的信息

        //----获取专业院系列表
        MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        try {
            List<MajorVo> majorVos = majorDao.showdepartment();
            List<MajorVo> majorVos1 = majorDao.showname();
            req.setAttribute("majorVosDep",majorVos);
            req.setAttribute("majorVosName",majorVos1);
            //-----------------获取最新上架的书籍
            List<BookVo> bookLastVos = bookDao.findLatestBook(5);//这里需要参数@import
            req.setAttribute("bookLastVos",bookLastVos);
            //-----------------获取推荐给用户的书籍
            if(userVo!=null) {
                List<BookVo> bookRecVos = bookDao.getRecommedBooks(userVo.getMajorID(), userVo.getId());
                req.setAttribute("bookRecVos",bookRecVos);
            }
            bookDao.close();
            majorDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //----获取最新上架书籍列表


        RequestDispatcher dispatcher = req.getRequestDispatcher("/pages/mainPage/index.jsp");
        dispatcher.forward(req,resp);
    }
}
