package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.utils.Paging;
import com.book.buy.vo.ComplainVo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by violet on 2015/11/6.
 * 用于管理员查看投诉列表时
 */
@WebServlet(name = "GetAllCompServlet", urlPatterns = "/getallcomp")
public class GetAllCompServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();
        ComplainVo compvo = new ComplainVo();
        int everyPageNum = 5;

        List<ComplainVo> lis = new ArrayList<>();
        Paging paging = new Paging();

        String state = (String)request.getParameter("state");
        if (null == state){
            state = "all";
        }

        try {
            if (state.equals("all")){
                lis = compdao.getAllComp();
                paging = new Paging(everyPageNum,request,lis.size(),"/getallcomp?");
            } else if (state.equals("yes")){
                lis = compdao.getCompByState(1, 0);
                paging = new Paging(everyPageNum,request,lis.size(),"/getallcomp?state=yes&&");
            } else if (state.equals("no")) {
                lis = compdao.getCompByState(0, 0);
                paging = new Paging(everyPageNum,request,lis.size(),"/getallcomp?state=no&&");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        request.getSession().setAttribute("paging", paging);
        lis = lis.subList(paging.getStart(),paging.getEnd());

        request.getSession().setAttribute("allcomp", lis);

        compdao.close();

        response.sendRedirect("/allcomp");

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
