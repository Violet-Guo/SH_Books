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
	    //获取session中登陆的user的个人信息
	    UserVo userVo = (UserVo) request.getSession().getAttribute("user");
	    //UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
	    request.getSession().setAttribute("user", userVo);
	    //获取用户dao
	    LocationDao locationDao = LocationDaoImpFactory.getLocationDaoImp();
	    LocationVo locationVo = null;
	    //获取消息dao获取用户所拥有的的消息个数(num)
	    InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
	    try {
		//获取消息个数
		Integer num = InformDaoImpl.count(userVo.getId()).size();
		request.getSession().setAttribute("num", num);
		//获取用户住址
		locationVo = locationDao.getLocationByuserID(userVo.getId());
		request.getSession().setAttribute("location", locationVo);
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    //关闭数据流和跳转操作
	    locationDao.close();
	    response.sendRedirect("/personInfo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
