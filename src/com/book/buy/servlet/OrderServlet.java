package com.book.buy.servlet;

import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.vo.BuyVo;
import com.book.buy.vo.OrderFormVo;
import com.book.buy.vo.UserVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by chao on 2015/11/25.
 */
@WebServlet(name = "OrderServlet",urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }
        BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();

        try {
            List<BuyVo> buyVos = (List<BuyVo>) buyDao.getBuyByUserID(userVo.getId());
            //List<List<OrderFormVo>> orderFromVoLists = orderformDao.findByOrderID(int orderID);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            buyDao.close();
            orderformDao.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
