package com.book.buy.servlet;

import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
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
 * Created by songchao on 15/12/11.
 */
@WebServlet(name = "DelOrderServlet",urlPatterns = "/delorder")
public class DelOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('您已经提交过请求了,返回重新提交');</script>");
            return;
        }
        String strOrderID = request.getParameter("orderID");
        Integer orderID = Integer.valueOf(strOrderID);
        BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
        //-----------这里需要判断这个订单是否属于这个人

        //-------------END
        try {
            orderformDao.delOrderformByOrderID(orderID);
            buyDao.delBuyByOrderID(orderID);
            out.print("yes");
        } catch (SQLException e) {
            e.printStackTrace();
            out.print("no");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
