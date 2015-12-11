package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.InformDao;
import com.book.buy.dao.WantDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.factory.WantDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.InformVo;
import com.book.buy.vo.WantVo;

/**
 * 根据书名进行心愿单匹配提示该user
 * 如果结果匹配推送一条消息
 */
@WebServlet(name="UpdateServlet",urlPatterns="/UpdateServlet")
public class UpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private List<BookVo> books = null;
	public Integer i ;
	public Integer[] bookID_arr = null;
	public Integer[] userID_arr = null;
	
    public UpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		String bookname = request.getParameter("bookname");
		String author = request.getParameter("author");
		String bookID = request.getParameter("bookID");

		Integer bookid = Integer.parseInt(bookID);
		String now = NewDate.getDateTime(new Date());
		
		BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
		WantDao wantDao = WantDaoImpFactory.getWantDao();
		InformDao informDao = InformDaoImplFactory.getInformDaoImpl();
		BookVo bookVo = new BookVo();
		WantVo wantVo = new WantVo();
		
		try{
			books = bookDao.findAllByNameAndAuthor(bookname, author);
			Integer size = books.size();
			bookID_arr = new Integer[size];
			userID_arr = new Integer[size];
			if(size == 0)
			{
				response.sendRedirect("/publishedbooks");
			}
			for (i=0; i<size; i++){
				bookVo = (BookVo) books.get(i);
				bookID_arr[i] = bookVo.getId();
			}

			Integer[] bookids = new Integer[size-1];
			for (i=0; i<(size-1); i++){
				bookids[i] = bookID_arr[i];
			}
			
			for (i=0; i<size; i++){
				wantVo = wantDao.findBybookid(bookids[i]);
				userID_arr[i] = wantVo.getUserID();
			}
			
			for (i=0; i<size; i++)
			{
				InformVo inform = new InformVo(userID_arr[i], 1, bookid, now, 0);
				informDao.addInform(inform);
			}
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
