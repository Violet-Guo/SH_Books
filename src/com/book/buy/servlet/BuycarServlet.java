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
            //try {
                int id = Integer.valueOf(delNum);
                //orderformDao.delOrderform(id,1);//------------参数是id
                out.print("yes");
            /*} catch (SQLException e) {
                e.printStackTrace();
            }*/
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
            try {
                buyDao.addBuy(buyVo);
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
            try {
                List<OrderFormVo> orderFormVos = orderformDao.findAllitem(userVo.getId());
                request.setAttribute("orderFormVos",orderFormVos);
                int len = orderFormVos.size();
                ArrayList<UserVo> orderUserVos = new ArrayList<>();
                ArrayList<BookVo> orderBookVos = new ArrayList<>();
                BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
                UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
                for(int i=0;i<len;i++){
                    int bookID = orderFormVos.get(i).getBookID();
                    BookVo bookVo = bookDao.findById(bookID);
                    orderBookVos.add(bookVo);
                    UserVo userVo1 = userDao.findUserById(bookVo.getId());
                    orderUserVos.add(userVo1);
                }
                bookDao.close();
                userDao.close();
                request.setAttribute("orderUserVos",orderUserVos);
                request.setAttribute("orderBookVos",orderBookVos);
                orderformDao.close();
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
