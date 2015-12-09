package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.vo.BookVo;

/**
 * 书籍上架
 */
@WebServlet(name="OnSaleServlet",urlPatterns="/OnSaleServlet")
public class OnSaleServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public OnSaleServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String id = request.getParameter("bookid");
		Integer bookid = Integer.parseInt(id);
		
		BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
		BookVo bookVo = new BookVo();
		
		try{
			bookVo = bookDao.findById(bookid);
			if(bookVo.getState() == 0)
			{
				bookVo.setState(1);
			}
			bookDao.updateBook(bookVo);
			response.sendRedirect("/publishedbooks");
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
