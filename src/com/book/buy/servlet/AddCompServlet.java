package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.vo.ComplainVo;

import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by violet on 2015/11/11.
 */
@WebServlet(name = "AddCompServlet", urlPatterns = "/addcomp")
public class AddCompServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        ComplainVo compvo = new ComplainVo();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        try {
            compdao.addComp(compvo);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        compdao.close();

        response.sendRedirect("serverPage/state/addCompSucc.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
