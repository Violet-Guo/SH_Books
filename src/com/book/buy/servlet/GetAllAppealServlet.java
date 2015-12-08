package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
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

        ManagerVo admin = (ManagerVo)request.getSession().getAttribute("admin");
        PrintWriter out = response.getWriter();
        String href = "";

        if (null == admin){
            href = "/loginmanager";
            out.print("<script language='javascript'>alert('管理员请先登陆');"
                    + "window.location.href='" + href + "';</script>");
        }

        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        List<ComplainVo> lis = new ArrayList<>();

        try {
            lis = compdao.getAllAppeal();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("allappeal", lis);

        compdao.close();

        response.sendRedirect("/getallcomp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
