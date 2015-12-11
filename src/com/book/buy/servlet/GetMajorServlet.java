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
	    //获取专业Dao
	    MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
	    List<MajorVo> list = null;
	    //获取专业列表
	     try {
		 list = majorDao.showAll();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    //设置图书信息为空，专业列表和跳转用途
	    request.getSession().setAttribute("bookVo", null);
	    request.getSession().setAttribute("major", list);
	    request.getSession().setAttribute("method", 2); //表示为增加图书
	    //关闭流
	    majorDao.close();
	    //跳转到增加图书页面
	    response.sendRedirect("/publishPage");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
