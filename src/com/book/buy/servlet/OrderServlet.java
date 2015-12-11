package com.book.buy.servlet;

import com.book.buy.dao.*;
import com.book.buy.daoImp.BuyDaoImp;
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
import java.util.HashMap;
import java.util.List;

/**
 * Created by chao on 2015/11/25.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }


        //------------有种情况会经过这个servlet 一键下单 评价 同时要区分买家，卖家的情况，在用户点击买家卖家进行切换的时候，需要这个类进行处理
        Boolean isOrder = (Boolean) request.getAttribute("isOrder");
        String strIsBuyer = request.getParameter("isbuyer");
        String strIsEva = request.getParameter("isEva");
        Boolean isEva = false;
        if(strIsEva!=null){
            isEva = Boolean.valueOf(strIsEva);
        }
        Boolean isBuyer = null;
        if(strIsBuyer==null){
            isBuyer = (Boolean) session.getAttribute("isuyer");
        }else {
            isBuyer = Boolean.valueOf(strIsBuyer);
            session.setAttribute("isBuyer",isBuyer);
        }

        if(isOrder==null)
            isOrder=false;
        BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
        //---------------获取参数
        String state;
        //-----------------如果是订单
        if(!isOrder) {
            state = request.getParameter("state");
        }else {
            state = (String) request.getAttribute("state");
        }
        //----如果是评价
        if(isEva){
            state="isQuick";
        }
        //--------------如果状态是空设为all
        if(state==null){
            state="all";
        }

        //--------------下面是对传出的各种状态参数的处理和返回
        //---------------这里进行分页------不同的情况下有不同的页面数量，需要分别获取
        Integer count = null;
        int everypagenum = 5;
        Paging paging = null;

        try {
            List<BuyVo> buyVos = null;
            if(isBuyer==null && !state.equals("isQuick")){
                if (state.equals("all")) {//----------全部订单
                    //获取buy列表
                    //-----获取数量--进行分页
                    count = buyDao.getCountByUserID(userVo.getId()).intValue();
                    paging = new Paging(5, request, count, "/order?state=" + state+"&");

                    buyVos = buyDao.getBuyByUserID(userVo.getId(), paging.getStart(), paging.getEnd());
                }else if(state.equals("waitsure")){//--------待确认订单
                    //-----获取数量--进行分页
                    count = buyDao.getWaitSureCount(userVo.getId()).intValue();
                    paging = new Paging(5, request, count, "/order?state=" + state+"&");

                    buyVos = buyDao.getWaitSureByUserID(userVo.getId(), paging.getStart(), paging.getEnd());
                }else if(state.equals("waiteva")) {//----------待评价订单
                    //-----获取数量--进行分页
                    count = buyDao.getWaitEvaCount(userVo.getId()).intValue();
                    paging = new Paging(5, request, count, "/order?state=" + state+"&");

                    buyVos = buyDao.getWaitEvaByUserID(userVo.getId(), paging.getStart(), paging.getEnd());
                }
            }else if(isBuyer!=null && !state.equals("isQuick")){
                //--------这里处理用户点击了切换开关之后的代码
                if(isBuyer) {//--------买家
                    if (state.equals("all")) {
                        //获取buy列表
                        count = buyDao.getCountByUserID(userVo,BuyDaoImp.ISBUYER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getBuyByUserID(userVo, paging.getStart(), paging.getEnd(), BuyDaoImp.ISBUYER);
                    } else if (state.equals("waitsure")) {
                        count = buyDao.getWaitSureCount(userVo,BuyDaoImp.ISBUYER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getWaitSureByUserID(userVo, paging.getStart(), paging.getEnd(), BuyDaoImp.ISBUYER);
                    } else if (state.equals("waiteva")) {
                        count = buyDao.getWaitEvaCount(userVo, BuyDaoImp.ISBUYER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getWaitEvaByUserID(userVo, paging.getStart(), paging.getEnd(), BuyDaoImp.ISBUYER);
                    }
                }else{//----------卖家
                    if (state.equals("all")) {
                        //获取buy列表
                        count = buyDao.getCountByUserID(userVo, BuyDaoImp.ISSELLER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getBuyByUserID(userVo, paging.getStart(), paging.getEnd(),BuyDaoImp.ISSELLER);
                    }else if(state.equals("waitsure")){
                        count = buyDao.getWaitSureCount(userVo, BuyDaoImp.ISSELLER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getWaitSureByUserID(userVo, paging.getStart(), paging.getEnd(),BuyDaoImp.ISSELLER);
                    }else if(state.equals("waiteva")) {
                        count = buyDao.getWaitEvaCount(userVo, BuyDaoImp.ISSELLER).intValue();
                        paging = new Paging(5, request, count, "/order?state=" + state+"&");

                        buyVos = buyDao.getWaitEvaByUserID(userVo, paging.getStart(), paging.getEnd(),BuyDaoImp.ISSELLER);
                    }
                }
            }else if(state.equals("isQuick")){//--------一键下单部分
                Integer orderID = (Integer) request.getAttribute("orderID");
                paging = new Paging(5, request, 1, "/order?state=" + state+"&");
                if(orderID==null){
                    String strOrderID = request.getParameter("orderID");
                    orderID = Integer.valueOf(strOrderID);
                }

                Double allPrice = buyDao.getBuyPrice(orderID);
                LocationDao locationDao = LocationDaoImpFactory.getLocationDaoImp();
                LocationVo locationVo = locationDao.getLocationByuserID(userVo.getId());
                request.setAttribute("locationVo",locationVo);
                request.setAttribute("allPrice",allPrice);
                buyVos = new ArrayList<>();
                buyVos.add(buyDao.getBuyByOrderID(orderID));
            }
            //通过buy列表获取orderForm
            BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
            UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
            HashMap<Long, List<OrderFormVo>> orderFormVoMap = new HashMap<>();
            HashMap<Long, List<UserVo>> orderFormUserMap = new HashMap<>();
            HashMap<Long, List<BookVo>> orderFormBookMap = new HashMap<>();
            ArrayList<Double> orderPriceList = new ArrayList<>();

            //-----------对这个用户获取的buyMap进行整理，获取用户，书 和order，然后进行整合，多个商品一个卖家进行整合
            for (int r = 0; r < buyVos.size(); r++) {

                List<OrderFormVo> orderFormVos = orderformDao.findByOrderID(buyVos.get(r).getOrderID());
                orderFormVoMap.put(Long.valueOf(r), orderFormVos);


                //------------计算结算价格
                Double price = buyDao.getBuyPrice(buyVos.get(r).getOrderID());
                if (price == null) {
                    price = 0.0;
                }
                orderPriceList.add(price);

                ArrayList<UserVo> orderUserVos = new ArrayList<>();
                ArrayList<BookVo> orderBookVos = new ArrayList<>();

                int len = orderFormVos.size();

                for (int i = 0; i < len; i++) {
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
                for (int i = 0; i < orderFormVos.size(); i++) {
                    tempName = orderUserVos.get(i).getUsername();
                    for (int h = i; h < orderFormVos.size(); h++) {
                        if (orderUserVos.get(h).getUsername().equals(tempName)) {
                            orderFormVos1.add(orderFormVos.get(h));
                            orderUserVos1.add(orderUserVos.get(h));
                            orderBookVos1.add(orderBookVos.get(h));

                            orderFormVos.remove(h);
                            orderUserVos.remove(h);
                            orderBookVos.remove(h);

                            i = -1;
                        }
                    }
                }
                orderFormVoMap.put(Long.valueOf(r), orderFormVos1);
                orderFormUserMap.put(Long.valueOf(r), orderUserVos1);
                orderFormBookMap.put(Long.valueOf(r), orderBookVos1);

            }

            orderformDao.close();
            bookDao.close();
            userDao.close();
            buyDao.close();

            request.setAttribute("state", state);
            request.setAttribute("buyVos", buyVos);
            request.setAttribute("orderFormVoMap", orderFormVoMap);
            request.setAttribute("orderFormUserMap", orderFormUserMap);
            request.setAttribute("orderFormBookMap", orderFormBookMap);
            request.setAttribute("orderPriceList", orderPriceList);

            //-------------不同的情况下跳转到不同的页面
            RequestDispatcher dispatcher = null;
            if(isOrder){
                dispatcher = request.getRequestDispatcher("/pages/personPage/isOrder.jsp");
            }else if(isEva){
                dispatcher = request.getRequestDispatcher("/pages/personPage/evaluation.jsp");
            }else {
                dispatcher = request.getRequestDispatcher("/pages/personPage/order.jsp");
            }
            request.setAttribute("paging", paging);
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
