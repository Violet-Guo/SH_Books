package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.OrderformDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.OrderformDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.OrderFormVo;
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
 * Created by chao on 2015/12/7.
 */
@WebServlet(name = "AddBuycarServlet",urlPatterns = "/addbuycar")
public class AddBuycarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        UserVo userVo = (UserVo) request.getSession().getAttribute("user");
        if(userVo==null){
            out.print("login");
            return;

        }
        String strBookID = request.getParameter("bookID");
        Integer bookID = Integer.valueOf(strBookID);
        String strBookNum = request.getParameter("bookNum");
        Integer bookNum = Integer.valueOf(strBookNum);
        if(bookNum==null){
            bookNum=1;
        }
        OrderformDao orderformDao = OrderformDaoImpFactory.getOrderformDao();

        try {
            /*OrderFormVo orderFormVoT = orderformDao.findByuseridandbookid(userVo.getId(),bookID);
            if(orderFormVoT!=null){
                //-----已经购买过这本书
                out.print("happen");
                return;
            }*/
            //-----判断购物车内是否有此商品,若有更改数量,若无,添加商品,判断数量是否超过
            BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
            BookVo bookVo = bookDao.findById(bookID);

            OrderFormVo orderFormVoTem = orderformDao.findByuseridandbookid(userVo.getId(),bookID);

            OrderFormVo orderFormVo = new OrderFormVo();
            orderFormVo.setOrderId(null);
            orderFormVo.setUserID(userVo.getId());
            orderFormVo.setBookNum(bookNum);
            orderFormVo.setBookID(bookID);

            if(orderFormVoTem!=null){
                if(orderFormVoTem.getBookNum()+bookNum>bookVo.getBookNum()){
                    out.print("overnum");
                    return;
                }
                //------购物车有此商品的情况
                orderFormVoTem.setBookNum(bookNum+orderFormVoTem.getBookNum());
                orderformDao.updateOrderform(orderFormVoTem);
            }else {
                 orderformDao.addOrderform(orderFormVo);
            }
            orderformDao.close();

            out.print("yes");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
