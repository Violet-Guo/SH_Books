package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.UserDao;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.UserVo;

/**
 * 用户登录进行身份验证的servlet
 * 如果登录成功则保存整个user的信息在session中
 * @author NvPiao
 *
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        public LoginServlet() {
            super();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    //获取登陆时的用户名和密码
	    String username = request.getParameter("username");
	    String password = request.getParameter("password");
	    //获取用户Dao和输出流
	    UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
	    PrintWriter out = response.getWriter();
	    
	    String href = "";
	    try {
		//在数据库中查询是否存在该用户
		UserVo user = userDao.findUserByName(username);
		if(user == null){
		    href = "/login";
		    if(username == null || username.equals("") || password == null || password.equals(""))
			out.print("<script language='javascript'>alert('用户名或密码不能为空！！！');"
			    	+ "window.location.href='"+ href + "';</script>");
		    else
		    //提示未注册，不跳转
		    out.print("<script language='javascript'>alert('该用户不存在！请先注册！！！');"
		    	+ "window.location.href='"+ href + "';</script>");
		}else{
		    if(user.getPassword().equals(password)){
			//验证成功保存session
			request.getSession().setAttribute("user", user);
			//跳转
			response.sendRedirect("/index");
		    }
		    else{
			//提示密码错误，不跳转
			href = "/login";
			out.print("<script language='javascript'>alert('该用户不存在或密码错误！！！');"
			    + "window.location.href='"+ href + "';</script>");
		    }
		}
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
