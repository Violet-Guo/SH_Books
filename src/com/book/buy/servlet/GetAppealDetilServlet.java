package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.ComplainVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/26.
 */
@WebServlet(name = "GetAppealDetilServlet", urlPatterns = "/getappealdetil")
public class GetAppealDetilServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String appid = request.getParameter("appid");
        int id = Integer.parseInt(appid);
        ComplainVo compvo = new ComplainVo();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        try {
            compvo = compdao.getAppealById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        request.getSession().setAttribute("appealdetil", compvo);

        compdao.close();

        response.sendRedirect("pages/managerPage/info/AppealDetil.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
