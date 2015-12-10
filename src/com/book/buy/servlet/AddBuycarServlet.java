package com.book.buy.servlet;

import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.vo.OrderFormVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

/**
 * Created by chao on 2015/12/7.
 */
@WebServlet(name = "AddBuycarServlet",urlPatterns = "/addbuycar")
public class AddBuycarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登录状态出错，重新登录');window.location.href='/login';</script>");
            return;
        }
        String strBookID = request.getParameter("bookID");
        Integer bookID = Integer.valueOf(strBookID);
        String strBookNum = request.getParameter("bookNum");
        Integer bookNum = Integer.valueOf(strBookNum);
        if(bookNum==null){
            bookNum=1;
        }
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();

        try {
            OrderFormVo orderFormVo = new OrderFormVo();
            orderFormVo.setOrderId(null);
            orderFormVo.setUserID(userVo.getId());
            orderFormVo.setBookNum(bookNum);
            orderFormVo.setBookID(bookID);

            orderformDao.addOrderform(orderFormVo);
            orderformDao.close();

            out.print("yes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
