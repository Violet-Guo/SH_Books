package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;

/**
 * 展示图书详情前获取图书信息
 * Servlet implementation class ShowBookDetail
 */
@WebServlet("/ShowBookDetail")
public class ShowBookDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        public ShowBookDetail() {
            super();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    //获取参数
	    String bookID = request.getParameter("bookID");
	    //获取Dao
	    BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
	    
	    BookVo bookVo = null;
	    UserDao userDao = null;
	    try {
		//按照图书的id去查找图书
		bookVo = bookDao.findById(Integer.parseInt(bookID));
		request.getSession().setAttribute("bookDetils", bookVo);
		
		String newOld = null;
		//设置新旧程度
		if(bookVo.getOldGrade() == 10)
		    newOld = "全新";
		else if(bookVo.getOldGrade() == 9)
		    newOld = "九成新";
		else if(bookVo.getOldGrade() == 8)
		    newOld = "八成新";
		else if(bookVo.getOldGrade() == 5)
		    newOld = "五成新";
		else if(bookVo.getOldGrade() == 4)
		    newOld = "五成新以下";
		request.getSession().setAttribute("newOld", newOld);
		//获取用户Dao
		userDao = UserDaoImpFactory.getUserDaoImpl();
		//按照用户的id进行用户查找
		UserVo userVo = userDao.findUserById(bookVo.getUserID());
		request.getSession().setAttribute("userVo", userVo);
	    } catch (NumberFormatException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    //关闭流
	    bookDao.close();
	    userDao.close();
	    //跳转
	    response.sendRedirect("/bookDetail");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
