package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.buy.dao.InformDao;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.vo.InformVo;
import com.book.buy.vo.UserVo;

/**未读通知
 * Servlet implementation class unreadServlet
 */
@WebServlet("/unreadServlet")
public class unreadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public unreadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(true);
		Integer userID = new Integer(-1);
		//UserVo userVo = new UserVo(1, "nihao", "nihao", "/SH_Books/images/touxiang.png", "nihao", 1, "2015-01-01", "nihao", "nihao", 0);
		UserVo userVo;
	    userVo=(UserVo)session.getAttribute("user");//从session中拿到vo
		if(userVo==null)
		{//失败则提醒登录
			out.print("<script>alert('你的登陆状态出错');window.location.href='/login';</script>");
			return;
		}
		userID=userVo.getId();//用get拿到id
		session.setAttribute("userID", userID);//传参
		String href = "";// 跳转的界面
		String strPage=request.getParameter("thisPage");//首次strPage=null
		System.out.print(strPage);
		session.setAttribute("userID",userID);
		session.setAttribute("thisPage",strPage);
		InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
		List<InformVo> informs = null;
		try 
		{
			informs = (List<InformVo>) InformDaoImpl.count(userID);//通知列表
			System.out.println(informs);
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		session.setAttribute("informs", informs);//传给jsp
		if (informs != null) 
		{
		
			try
			{
				InformDaoImpl.close();//关闭连接
			} catch (SQLException e)
			{
				e.printStackTrace();
			}	
		href = "/unread";//跳转至jsp显示表单内容
			out.print("<script language='javascript'>window.location.href='"
					+ href + "';</script>"); // 页面重定向
		} 
		else 
		{
			href = "/controlCenter";
			out.print("<script language='javascript'>alert('暂无通知消息，先去别的地方转转吧~~');window.location.href='"
					+ href + "';</script>");
			try 
			{
				InformDaoImpl.close();
			} catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
		out.flush();
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
