package com.book.buy.servlet;

import com.book.buy.dao.EvaluateDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.EvaluateDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.EvaluateVo;
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
 * Created by songchao on 15/11/28.
 */
@WebServlet(name = "EvaluationServlet",urlPatterns = "/evaluation")
public class EvaluationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession();
        UserVo userVo = (UserVo) session.getAttribute("user");
        /*if(userVo==null){
            out.print("<script>alert('登陆出现错误,重新登陆');window.location.href='/login';</script>");
            return;
        }*/

        String strSellerID = request.getParameter("sellID");
        Integer sellerID = Integer.valueOf(strSellerID);
        EvaluateDao evaluateDao = EvaluateDaoImpFactory.getEvaluateDaoImp();
        Paging paging = new Paging(15,request,50,"/evaluation?");
        try {
            UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
            UserVo sellerVo = userDao.findUserById(sellerID);
            List<EvaluateVo> evaluateVos = evaluateDao.getAllEvaluate(sellerID, paging.getStart(), paging.getEnd());
            List<UserVo> userVos = new ArrayList<>();
            for(int i=0;i<evaluateVos.size();i++){
                UserVo userVo1 = userDao.findUserById(Integer.valueOf(evaluateVos.get(i).getUserID()));
                userVos.add(userVo1);
            }
            request.setAttribute("userVos",userVos);
            request.setAttribute("evaluationVos",evaluateVos);
            request.setAttribute("sellVo",sellerVo);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/pages/personPage/showeva.jsp");
            dispatcher.forward(request,response);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
