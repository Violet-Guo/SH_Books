package com.book.buy.servlet;

import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BuyVo;
import com.book.buy.vo.OrderFormVo;
import com.book.buy.vo.UserVo;
import com.sun.org.apache.xpath.internal.operations.Bool;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by chao on 2015/12/6.
 */
@WebServlet(name = "AddOrderServlet",urlPatterns = "/addorder")
public class AddOrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        PrintWriter out = response.getWriter();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登陆状态出错，重新登陆');window.location.href='/login';</script>");
            return;
        }
        String isQuick = request.getParameter("isQuick");
        if(isQuick!=null&&isQuick.equals("yes")){
            //---------------一键下单
            String strBookID = request.getParameter("bookID");
            Integer bookID = Integer.valueOf(strBookID);
            String strBookNum = request.getParameter("bookNum");
            Integer bookNum = Integer.valueOf(strBookNum);
            if(bookID!=null){
                OrderFormVo orderFormVo = new OrderFormVo();
                orderFormVo.setBookID(bookID);
                orderFormVo.setBookNum(bookNum);
                orderFormVo.setUserID(userVo.getId());

                BuyVo buyVo = new BuyVo();
                Date date = new Date();
                String time = NewDate.getDateTime(date);
                buyVo.setUserID(userVo.getId());
                buyVo.setTime(time);
                buyVo.setMoneyTime(time);

                OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
                BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
                //-----------重要：这里先生成订单
                try {
                    //---------往数据库里插入
                    buyDao.addBuy(buyVo);
                    Integer orderID = buyDao.getLastInsertID();
                    orderFormVo.setOrderId(orderID);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    orderformDao.addOrderform(orderFormVo);
                    request.setAttribute("orderID", orderFormVo.getOrderId());
                    request.setAttribute("isOrder",true);
                    request.setAttribute("state","isQuick");

                    buyDao.close();
                    orderformDao.close();

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/order");
                    dispatcher.forward(request,response);
                    //out.print("yes");
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        BuyDao buyDao1 = BuyDaoImpFactory.getBuyDaoImp();
                        buyDao1.delBuyByOrderID(buyVo.getOrderID());
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }else{
                out.print("错误");
            }
            return;
        }



        /*String boolIsOrder = (String) request.getAttribute("isOrder");
        Boolean isOrder = Boolean.valueOf(boolIsOrder);
        if(isOrder!=null&&isOrder){
            BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
            BuyVo buyVo = new BuyVo();
            buyVo.setUserID(userVo.getId());
            Date date = new Date();
            String time = NewDate.getDateTime(date);//获取时间
            buyVo.setMoneyTime(time);
            buyVo.setSureTime(null);
            buyVo.setTime(time);
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            try {
                buyDao.addBuy(buyVo);
                int orderID = buyDao.getLastInsertID();
                orderformDao.updateByuserid(userVo.getId(), orderID);

                orderformDao.close();
                buyDao.close();


                //request.getRequestDispatcher("/order").forward(request,response);
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<script>alert('出错，请重试');window.location.href='/buycar';</script>");
            }
        }*/

        String strIsSure = request.getParameter("isSure");
        if(strIsSure.equals("yes")){
            //---------确认收货
            Date date = new Date();
            String time = NewDate.getDateTime(date);
            String strOrderID = request.getParameter("orderID");
            Integer orderID = Integer.valueOf(strOrderID);
            BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
            BuyVo buyVo = new BuyVo();
            buyVo.setSureTime(time);
            buyVo.setHasEva(0);
            try {
                buyDao.updateByOrderID(buyVo);
                buyDao.close();
                out.print("yes");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
