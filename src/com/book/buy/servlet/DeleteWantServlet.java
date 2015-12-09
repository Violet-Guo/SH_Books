package com.book.buy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.WantDao;
import com.book.buy.factory.WantDaoImpFactory;
import com.book.buy.vo.UserVo;

import java.sql.SQLException;

/**
 * ajax异步删除
 */
@WebServlet("/DeleteWantServlet")
public class DeleteWantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteWantServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String id = request.getParameter("id");
	    Integer bookid = Integer.parseInt(id);
	    UserVo user = (UserVo)request.getSession().getAttribute("user");
	    
	    WantDao wantDao = WantDaoImpFactory.getWantDao();
	    
	    try{
	    	wantDao.delWant(user.getId(), bookid);
	    	wantDao.close();
	    	}
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
