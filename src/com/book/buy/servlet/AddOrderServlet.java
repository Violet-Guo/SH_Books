package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BookVo;
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
        //-----------这里判断是否是一个请求被错误多次提交
        /*Integer beforSign = (Integer) session.getAttribute("beforSign");
        Integer sign = (Integer) session.getAttribute("sign");
        if(beforSign==sign){
            out.print("<script>alert('您已经提交过请求了,返回重新提交');</script>");
            return;
        }*/
        UserVo userVo = (UserVo) session.getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登陆状态出错，重新登陆');window.location.href='/login';</script>");
            return;
        }
        //-------------如果是一键下单，那么从这里处理
        String isQuick = request.getParameter("isQuick");
        if(isQuick!=null&&isQuick.equals("yes")){
            //---------------一键下单-----获取参数
            String strBookID = request.getParameter("bookID");
            Integer bookID = Integer.valueOf(strBookID);
            String strBookNum = request.getParameter("bookNum");
            Integer bookNum = Integer.valueOf(strBookNum);
            if(bookID!=null){
                //-----------判断书的数量是否大于最大数量
                BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
                OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
                BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();

                BookVo bookVo = null;
                try {
                    bookVo = bookDao.findById(bookID);
                    //-----如果书的卖家id和用户id一样，说明用户在买自己发布的书籍---不允许
                    if(bookVo.getUserID()==userVo.getId()){
                        out.print("<script>alert('你不能买自己发布的书');window.location.href='/ShowBookDetail?bookID="+bookID+"'</script>");
                        return;
                    }
                    //-----------------如果书籍数量小于买家需要的数量---不允许
                    if(bookVo.getBookNum()<bookNum){
                        out.print("<script>alert('数量大于最大数量,请重新下单');window.location.href='/ShowBookDetail?bookID="+bookID+"'</script>");
                        return;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
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

                //-----------重要：这里先生成订单
                try {
                    //---------往数据库里插入----先插入buy表，生成orderID
                    buyDao.addBuy(buyVo);
                    Integer orderID = buyDao.getLastInsertID();
                    orderFormVo.setOrderId(orderID);

                } catch (SQLException e) {
                    e.printStackTrace();
                }
                try {
                    //---------把生成的orderID插入order表---完成一个订单
                    orderformDao.addOrderform(orderFormVo);
                    request.setAttribute("orderID", orderFormVo.getOrderId());
                    request.setAttribute("isOrder",true);
                    request.setAttribute("state","isQuick");
                    //-------购买成功之后--书的数量减掉购买数量
                    bookVo.setBookNum(bookVo.getBookNum()-bookNum);
                    bookDao.updateBook(bookVo);

                    buyDao.close();
                    orderformDao.close();
                    bookDao.close();

                    RequestDispatcher dispatcher = request.getRequestDispatcher("/order");
                    dispatcher.forward(request,response);
                    //out.print("yes");
                } catch (SQLException e) {
                    e.printStackTrace();
                    try {
                        BuyDao buyDao1 = BuyDaoImpFactory.getBuyDaoImp();
                        buyDao1.delBuyByOrderID(buyVo.getOrderID());
                        buyDao1.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
            }else{
                out.print("错误");
            }
            return;
        }

        //----------确认收货再这里处理请求
        String strIsSure = request.getParameter("isSure");
        if (strIsSure.equals("yes")){

            Date date = new Date();
            String time = NewDate.getDateTime(date);
            String strOrderID = request.getParameter("orderID");
            Integer orderID = Integer.valueOf(strOrderID);
            BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
            BuyVo buyVo = new BuyVo();
            buyVo.setSureTime(time);
            buyVo.setHasEva(0);
            try {
                buyDao.updateByOrderID(buyVo,orderID);
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
