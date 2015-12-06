package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.BuyVo;
import com.book.buy.vo.OrderFormVo;
import com.book.buy.vo.UserVo;

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
import java.util.HashMap;
import java.util.List;

/**
 * Created by chao on 2015/11/25.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        if (userVo == null) {
            out.print("<script>alert('登录状态错误，请重新登录');window.location.href='/login';</script>");
            return;
        }
        BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
        //---------------获取参数
        String state = request.getParameter("state");
        if (state == null) {
            state = "all";
        }

        //--------------下面是对传出的各种状态参数的处理和返回
        //---------------这里进行分页
        Integer count = null;
        try {
            count = buyDao.getCountByUserID(userVo.getId()).intValue();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Paging paging = new Paging(5, request, count, "/order?state=" + state);
        request.setAttribute("paging", paging);

        try {
            List<BuyVo> buyVos = null;
            if (state.equals("all")) {
                //获取buy列表
                buyVos = buyDao.getBuyByUserID(userVo.getId(), paging.getStart(), paging.getEnd());
            }else if(state.equals("waitmoney")){
                buyVos = buyDao.getWaitMoneyByUserID(userVo.getId(),paging.getStart(),paging.getEnd());
            }else if(state.equals("waitsure")){
                buyVos = buyDao.getWaitSureByUserID(userVo.getId(),paging.getStart(),paging.getEnd());
            }else if(state.equals("waiteva")){
                buyVos = buyDao.getWaitEvaByUserID(userVo.getId(),paging.getStart(),paging.getEnd());
            }
            //通过buy列表获取orderForm
            BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
            UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
            HashMap<Long, List<OrderFormVo>> orderFormVoMap = new HashMap<>();
            HashMap<Long, List<UserVo>> orderFormUserMap = new HashMap<>();
            HashMap<Long, List<BookVo>> orderFormBookMap = new HashMap<>();
            ArrayList<Double> orderPriceList = new ArrayList<>();

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

            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/personPage/order.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
