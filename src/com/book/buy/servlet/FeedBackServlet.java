package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.buy.dao.FeedBackDao;
import com.book.buy.dao.InformDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.FeedBackVo;
import com.book.buy.vo.InformVo;
import com.book.buy.vo.UserVo;

/**反馈
 * Servlet implementation class FeedBack
 */
@WebServlet("/FeedBackServlet")
public class FeedBackServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	public FeedBackServlet() 
	{
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		//Integer userId=-1;
		String description=null;
		//UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
		UserVo userVo=new UserVo();
		userVo=(UserVo)session.getAttribute("user");//从session中拿到user
		if(userVo==null)//拿取失败则提示
		{
			out.print("<script>alert('你的登陆状态出错');window.location.href='/login';</script>");
			return;
		}
		int userId=userVo.getId();	 
		description=request.getParameter("description");//反馈信息
		if(description=="")
			{
				//description输入为空
				String href="./feedback";out.print("<script language='javascript'>alert('oops！输入不能为空哦~');window.location.href='"
				+ href + "';</script>");
				 out.close();
				 out.flush();
			}
		else if(description.length()>255)
		{
			//description太长
			String href="./feedback";out.print("<script language='javascript'>alert('oops！输入字数过多~');window.location.href='"
			+ href + "';</script>");
			 out.close();
			 out.flush();
		}
		else{
		String date = NewDate.getDateTime(new Date());
		System.out.println(date);
	    FeedBackVo feedBackVo = new FeedBackVo(userId,description,date);
		FeedBackDao FeedBackDaoImpl = FeedBackDaoImplFactory.getFeedBackDaoImpl();
		try 
		{
		 FeedBackDaoImpl.addFeedBack(feedBackVo);//插入数据库
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		 
		FeedBackDaoImpl.close();//关闭连接
		String href = "/controlCenter";//成功后跳转至服务中心
		out.print("<script language='javascript'>alert('反馈成功！');window.location.href='"
				+ href + "';</script>");
		out.flush();
		out.close();
		}
	  
	}
}
