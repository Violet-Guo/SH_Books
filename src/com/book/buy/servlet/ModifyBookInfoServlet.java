package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.MajorDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.MajorDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.MajorVo;

/**
 * 获取修改图书的图书信息
 * Servlet implementation class ModifyBookInfoServlet
 */
@WebServlet("/ModifyBookInfoServlet")
public class ModifyBookInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        public ModifyBookInfoServlet() {
            super();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    //获取修改图书的ID
	    String bookID = request.getParameter("bookID");
	    //String bookID = "1";
	    BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
	    BookVo bookVo = null; 
	    MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
	    List<MajorVo> list = null;
	    
	    try {
		//获取图书信息
		bookVo = bookDao.findById(Integer.parseInt(bookID));
		//获取专业列表
		list = majorDao.showAll();
	    } catch (NumberFormatException | SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    //设置session中的相应的值（图书信息，专业信息，修改图书的id）
	    request.getSession().setAttribute("bookVo", bookVo);
	    request.getSession().setAttribute("major", list);
	    request.getSession().setAttribute("changeBookId", bookID);
	    //设置标识符（1表示修改图书）
	    request.getSession().setAttribute("method", 1);
	    //关闭流
	    bookDao.close();
	    majorDao.close();
	    
	    response.sendRedirect("/publishPage");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
