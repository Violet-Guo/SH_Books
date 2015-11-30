package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.InformDao;
import com.book.buy.dao.LocationDao;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.factory.LocationDaoImpFactory;
import com.book.buy.vo.LocationVo;
import com.book.buy.vo.UserVo;

/**
 * 获取用户地址
 * Servlet implementation class PersonInfoServlet
 */
@WebServlet("/PersonInfoServlet")
public class PersonInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public PersonInfoServlet() {
	    super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    UserVo userVo = (UserVo) request.getSession().getAttribute("user");
	    //UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
	    request.getSession().setAttribute("user", userVo);
	    LocationDao locationDao = LocationDaoImpFactory.getLocationDaoImp();
	    LocationVo locationVo = null;
	    //获取num
	    InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
	    try {
		Integer num = InformDaoImpl.count(userVo.getId()).size();
		request.setAttribute("num", num);
		locationVo = locationDao.getLocationByuserID(userVo.getId());
		request.getSession().setAttribute("location", locationVo);
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    locationDao.close();
	    response.sendRedirect("/SH_Books/pages/personPage/personInfo/personInfo.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
