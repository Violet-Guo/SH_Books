package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.ManagerVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by violet on 2015/11/11.
 * 管理员获得所有的申诉信息列表
 */
@WebServlet(name = "GetAllAppealServlet", urlPatterns = "/getallappeal")
public class GetAllAppealServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        PrintWriter out = response.getWriter();
        String href = "";

        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('登录状态失效，管理员请登陆！');"
                    + "window.location.href='" + href + "';</script>");
            return;
        }

        int everyPageNum = 5;

        String state = (String)request.getParameter("state");
        if (null == state){
            state = "all";
        }

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        Paging paging = new Paging();

        List<ComplainVo> lis = new ArrayList<>();

        try {
            if (state.equals("all")){
                lis = compdao.getAllAppeal();
                paging = new Paging(everyPageNum,request,lis.size(),"/getallappeal?");
            } else if (state.equals("yes")){
                lis = compdao.getCompByState(1, 1);
                paging = new Paging(everyPageNum,request,lis.size(),"/getallappeal?state=yes&&");
            } else if (state.equals("no")){
                lis = compdao.getCompByState(0, 1);
                paging = new Paging(everyPageNum,request,lis.size(),"/getallappeal?state=no&&");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("paging", paging);
        lis = lis.subList(paging.getStart(),paging.getEnd());

        request.getSession().setAttribute("allappeal", lis);

        compdao.close();

        response.sendRedirect("/allappeal");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
