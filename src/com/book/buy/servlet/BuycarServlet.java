package com.book.buy.servlet;

import com.book.buy.dao.*;
import com.book.buy.factory.*;
import com.book.buy.utils.NewDate;
import com.book.buy.utils.Paging;
import com.book.buy.vo.*;

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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by 宋超 on 2015/11/13.
 */
@WebServlet(name = "BuycarServlet",urlPatterns = "/buycar")
public class BuycarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //-------这里删除商品
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        PrintWriter out = response.getWriter();
        if(userVo==null){
            out.println("<script>alert('用户登录状态出错，请重新登录');window.location.href='/login'</script>");
            return;
        }

        String delNum = request.getParameter("delNum");
        if(delNum!=null){
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            try {
                int id = Integer.valueOf(delNum);
                orderformDao.delOrderformByid(id);
                out.print("yes");
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                orderformDao.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return;
        }

        //--------------购物车的提交
        String buycarSub = request.getParameter("buycarSub");
        if(buycarSub!=null&&buycarSub.equals("yes")){
            BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
            BuyVo buyVo = new BuyVo();
            buyVo.setUserID(userVo.getId());
            Date date = new Date();
            String time = NewDate.getDateTime(date);//获取时间
            buyVo.setMoneyTime(time);
            buyVo.setSureTime(null);
            buyVo.setTime(time);
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            int orderID = 0;
            try {
                buyDao.addBuy(buyVo);
                orderID = buyDao.getLastInsertID();
                orderformDao.updateByuserid(userVo.getId(), orderID);

                request.setAttribute("isOrder", true);
                request.setAttribute("orderID", orderID);
                //------添加一个消息给卖家
                List<OrderFormVo> orderFormVos = orderformDao.findByOrderID(orderID);
                if(getEvery(request, userVo, orderFormVos)){
                    List<UserVo> userVos = (List<UserVo>) request.getAttribute("orderUserVos");
                    Integer sellerID = 0;
                    Integer beforeID = 0;
                    for(int i=0;i<userVos.size();i++){
                        if((sellerID = userVos.get(i).getId())!=beforeID){
                            beforeID = sellerID;
                            //---向sellerID发一条订单消息
                            InformVo informVo = new InformVo();
                            informVo.setUserID(sellerID);
                            informVo.setHasRead(0);
                            informVo.setNum(1);
                            informVo.setTime(time);
                            informVo.setType(1);///----@import这个值要改，获取订单消息后应该点击进入订单页面--通知黎明

                            InformDao informDao = InformDaoImplFactory.getInformDaoImpl();
                            informDao.addInform(informVo);
                        }
                    }
                }

                request.getRequestDispatcher("/pages/personPage/orderSuccess.jsp").forward(request, response);
                orderformDao.close();
                buyDao.close();
            } catch (SQLException e) {
                e.printStackTrace();
                //-----------出错回滚
                if(orderID!=0) {
                    try {
                        buyDao.delBuyByOrderID(orderID);
                        orderformDao.setOrderNullByOrderID(orderID);
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                }
                out.println("<script>alert('出错，请重试');window.location.href='/buycar';</script>");
            }
            return;
        }
        //----------------------------这里删除所有商品
        String delAll = request.getParameter("delAll");
        if(delAll != null&&delAll.equals("yes")){
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            try {
                orderformDao.delOrderformByUserID(userVo.getId());
                out.print("<script>alert('删除成功');window.location.href='/buycar'</script>");
                orderformDao.close();
                return;
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<script>alert('出错，请重试');window.location.href='/buycar';</script>");
            }
            return;
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if(userVo==null){
            out.print("<script>alert('登陆状态出错，重新登陆');window.location.href='/login';</script>");
            return;
        }

        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
        List<OrderFormVo> orderFormVos = null;
        try {
            orderFormVos = orderformDao.findAllitem(userVo.getId());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //-----分页
        int everyPageNum = 5;
        Paging paging = new Paging(everyPageNum,request,orderFormVos.size(),"/buycar?");
        request.setAttribute("paging",paging);
        //------------计算结算价格
        orderFormVos = orderFormVos.subList(paging.getStart(),paging.getEnd());
        /*BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
        try {
            Double price = orderformDao.findSumPriceByUserID(userVo.getId());
            if(price==null){
                price = 0.0;
            }
            request.setAttribute("allPrice",price);

            ArrayList<UserVo> orderUserVos = new ArrayList<>();
            ArrayList<BookVo> orderBookVos = new ArrayList<>();
            int len = orderFormVos.size();

            for(int i=0;i<len;i++){
                int bookID = orderFormVos.get(i).getBookID();
                BookVo bookVo = bookDao.findById(bookID);
                orderBookVos.add(bookVo);
                UserVo userVo1 = userDao.findUserById(bookVo.getUserID());
                orderUserVos.add(userVo1);
            }
            ArrayList<OrderFormVo> orderFormVos1 = new ArrayList<>();
            ArrayList<UserVo> orderUserVos1 = new ArrayList<>();
            ArrayList<BookVo> orderBookVos1 = new ArrayList<>();

            String tempName;
            for(int i=0;i<orderFormVos.size();i++){
                tempName = orderUserVos.get(i).getUsername();
                for (int h=i;h<orderFormVos.size();h++){
                    if(orderUserVos.get(h).getUsername().equals(tempName)){
                        orderFormVos1.add(orderFormVos.get(h));
                        orderUserVos1.add(orderUserVos.get(h));
                        orderBookVos1.add(orderBookVos.get(h));

                        orderFormVos.remove(h);
                        orderUserVos.remove(h);
                        orderBookVos.remove(h);

                        i=-1;
                    }
                }
            }
            request.setAttribute("orderFormVos",orderFormVos1);
            request.setAttribute("orderUserVos",orderUserVos1);
            request.setAttribute("orderBookVos",orderBookVos1);

            orderformDao.close();
            bookDao.close();
            userDao.close();
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/personPage/buycar.jsp");
            dispatcher.forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }*/
        if(getEvery(request,userVo,orderFormVos)) {
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/personPage/buycar.jsp");
            dispatcher.forward(request, response);
        }
    }

    private boolean getEvery(HttpServletRequest request,UserVo userVo,List<OrderFormVo> orderFormVos){
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
        try {
            Double price = orderformDao.findSumPriceByUserID(userVo.getId());
            if(price==null){
                price = 0.0;
            }
            request.setAttribute("allPrice",price);

            ArrayList<UserVo> orderUserVos = new ArrayList<>();
            ArrayList<BookVo> orderBookVos = new ArrayList<>();
            int len = orderFormVos.size();

            for(int i=0;i<len;i++){
                int bookID = orderFormVos.get(i).getBookID();
                BookVo bookVo = bookDao.findById(bookID);
                orderBookVos.add(bookVo);
                UserVo userVo1 = userDao.findUserById(bookVo.getUserID());
                orderUserVos.add(userVo1);
            }
            ArrayList<OrderFormVo> orderFormVos1 = new ArrayList<>();
            ArrayList<UserVo> orderUserVos1 = new ArrayList<>();
            ArrayList<BookVo> orderBookVos1 = new ArrayList<>();

            String tempName;
            for(int i=0;i<orderFormVos.size();i++){
                tempName = orderUserVos.get(i).getUsername();
                for (int h=i;h<orderFormVos.size();h++){
                    if(orderUserVos.get(h).getUsername().equals(tempName)){
                        orderFormVos1.add(orderFormVos.get(h));
                        orderUserVos1.add(orderUserVos.get(h));
                        orderBookVos1.add(orderBookVos.get(h));

                        orderFormVos.remove(h);
                        orderUserVos.remove(h);
                        orderBookVos.remove(h);

                        i=-1;
                    }
                }
            }
            request.setAttribute("orderFormVos",orderFormVos1);
            request.setAttribute("orderUserVos",orderUserVos1);
            request.setAttribute("orderBookVos", orderBookVos1);

            orderformDao.close();
            bookDao.close();
            userDao.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
