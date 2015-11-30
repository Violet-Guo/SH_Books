package com.book.buy.servlet;

import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.OrderformDaoImpFactory;
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
    //----------------------------这里添加购物车

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");//----参数讨论
        Boolean isLogin  = false;
        //----------------------------
        userVo = new UserVo();
        userVo.setId(1);
        //-------------------------------
        if(userVo!=null){
            isLogin = true;
            OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();
            try {
                List<OrderFormVo> orderFormVos = orderformDao.findAllitem(userVo.getId());
                request.setAttribute("orderFormVos",orderFormVos);
                int len = orderFormVos.size();
                ArrayList<ArrayList<OrderFormVo>> orderFormVoLists;
                String sellerName;
                for(int i=0;i<len;i++){
                    if(i==0){
                        //sellerName = orderFormVos.get(i).getBookID();
                    }else{
                        //if(sellerName.equals(""))
                    }
                    //ArrayList<OrderFormVo> orderFormVoList =
                }
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
