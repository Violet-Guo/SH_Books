package com.book.buy.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;


/**
 * 修改心愿单
 */
@WebServlet(name="AlterWantServlet",urlPatterns="/AlterWantServlet")
public class AlterWantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public Integer bookID;
	
    public AlterWantServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		UserVo user = (UserVo)request.getSession().getAttribute("user");
		
		String id = request.getParameter("id");
		bookID = Integer.parseInt(id);
		String name = request.getParameter("bookname");
		String year = request.getParameter("year");
		String writer = request.getParameter("writer");
		String ISBN = request.getParameter("ISBN");
		Date now = new Date();
		
		
		BookVo book = new BookVo(bookID, name, null, 0, ISBN, 0, year,
			    writer, 0, "", name, 1, (float)0, 0, NewDate.getDate(now), 1);
		
		BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
		
		try {
			bookDao.updateBook(book);
			bookDao.close();
			response.sendRedirect("/WantListServlet");
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
