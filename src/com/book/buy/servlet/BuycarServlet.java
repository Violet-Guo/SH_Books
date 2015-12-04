package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.BuyDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.BuyDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.NewDate;
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
import java.util.Date;
import java.util.List;

/**
 * Created by 宋超 on 2015/11/13.
 */
@WebServlet(name = "BuycarServlet",urlPatterns = "/buycar")
public class BuycarServlet extends HttpServlet {
    /*@Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {



    }*/

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //-------这里删除商品
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        PrintWriter out = response.getWriter();
        if(userVo==null){
            out.println("<script>alert('用户登录状态出错，请重新登录');window.location.href='/login'");
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
        String buycarSub = request.getParameter("buycarSub");
        if(buycarSub.equals("yes")){
            BuyDao buyDao = BuyDaoImpFactory.getBuyDaoImp();
            BuyVo buyVo = new BuyVo();
            buyVo.setUserID(userVo.getId());
            Date date = new Date();
            String time = NewDate.getDateTime(date);//获取时间
            buyVo.setMoneyTime(time);
            buyVo.setSureTime(time);
            buyVo.setTime(time);
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            try {
                buyDao.addBuy(buyVo);
                int id = buyDao.getLastInsertID();
                orderformDao.updateByuserid(userVo.getId(),id);

                orderformDao.close();
                buyDao.close();
            } catch (SQLException e) {
                e.printStackTrace();
                out.println("<script>alert('出错，请重试');window.location.href='/buycar';</script>");
            }
            return;
        }
        //----------------------------这里添加购物车

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");

        //----------------------------
        UserDao userDao1 = UserDaoImpFactory.getUserDaoImpl();
        try {
            userVo = userDao1.findUserById(1);
            session.setAttribute("user",userVo);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //-------------------------------
        if(userVo!=null){
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
            int thisPage = paging.getThisPage();
            request.setAttribute("paging",paging);
            //------------计算结算价格
            orderFormVos = orderFormVos.subList(paging.getStart(),paging.getEnd());
            BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
            UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
            try {
                double price = orderformDao.findSumPriceByUserID(userVo.getId());
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
            }
        }else{
            //----------未登录成功或者未登录
            out.print("<script>alert('登陆有问题,返回登陆页面');window.location.href='/'</script>");
        }
    }
}
