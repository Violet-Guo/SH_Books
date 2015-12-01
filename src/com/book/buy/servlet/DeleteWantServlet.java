package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
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
		PrintWriter out = response.getWriter();
		String id = request.getParameter("id");
	    Integer bookid = Integer.parseInt(id);
	    
	    BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
	    
	    try{
	    	bookDao.deleteBookById(bookid);
	    	bookDao.close();
	    	out.print("1");
	    	}
	    catch(SQLException e)
	    {
	    	e.printStackTrace();
	    	out.println("0");
	    }
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
