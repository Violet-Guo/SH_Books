package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.MajorDao;
import com.book.buy.factory.MajorDaoImpFactory;
import com.book.buy.vo.MajorVo;

/**
 * 增加图书之前查询专业
 * Servlet implementation class GetMajorServlet
 */
@WebServlet("/GetMajorServlet")
public class GetMajorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetMajorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
	    List<MajorVo> list = null;
	    //获取专业列表
	     try {
		 list = majorDao.showAll();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    request.getSession().setAttribute("major", list);
	    
	    majorDao.close();
	    
	    response.sendRedirect("/SH_Books/pages/bookPage/publish/publishPage.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
