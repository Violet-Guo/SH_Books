package com.book.buy.servlet;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.WantDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.WantDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;
import com.book.buy.vo.WantVo;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 添加心愿单
 * 跳转到心愿单列表界面
 */
@WebServlet(name="AddWantServlet",urlPatterns="/AddWantServlet")
public class AddWantServlet extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    
	public Integer bookid;
	
    public AddWantServlet() {
        super();
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");

		UserVo user = (UserVo)request.getSession().getAttribute("user");
		PrintWriter out = response.getWriter();
		if(user==null){
			out.println("<script>alert('用户登录状态出错，请重新登录');window.location.href='/login';</script>");
			return;
		}

		Date now = new Date();
		String name = request.getParameter("bookname");
		String year = request.getParameter("year");
		String writer = request.getParameter("writer");
		String ISBN = request.getParameter("ISBN");
		
		BookVo book = new BookVo(name, null, 0, ISBN, 0, year,
		    writer, 0, "", name, 1, (float)0, 0, NewDate.getDate(now), 1);
			
		BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
		WantDao wantDao = WantDaoImpFactory.getWantDao();
		
		try {
			bookDao.addBook(book);
            bookid = bookDao.getLastInfertID();
			wantDao.addWant(new WantVo(user.getId(), bookid, NewDate.getDateTime(new Date())));
			bookDao.close();
			wantDao.close();
			response.sendRedirect("/WantListServlet");
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
