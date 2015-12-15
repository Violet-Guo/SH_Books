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

/**4type通知
 * Servlet implementation class informbody
 */
@WebServlet("/InformServlet")
public class InformServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InformServlet() {
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
		{
			//失败则提醒登录
			out.print("<script>alert('你的登陆状态出错');window.location.href='/login';</script>");
			return;
		}
		userID=userVo.getId();//用get拿到id*/
		
		session.setAttribute("userID", userID);//传参
		String href = "";// 跳转的界面
		String strPage=request.getParameter("thisPage");//首次strPage=null
		System.out.print(strPage);
		
		session.setAttribute("thisPage",strPage);
		InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
		List<InformVo> informs = null;
		List<InformVo> list = null;	
		List<InformVo> wants = null;
		List<InformVo> unread = null;
		List<InformVo> manager = null;
		
		
		try 
		{
			informs = (List<InformVo>) InformDaoImpl.findbyuserid(userID);
			unread = (List<InformVo>) InformDaoImpl.count(userID);
			wants = (List<InformVo>) InformDaoImpl.findallbyut(userID,1);
			list = (List<InformVo>) InformDaoImpl.findallbyut(userID,2);
			manager=(List<InformVo>) InformDaoImpl.manager(userID);
		
		} catch (SQLException e) 
		{
			e.printStackTrace();
		}		
		session.setAttribute("informs", informs);//all 通知
		session.setAttribute("list", list);//订单通知
		session.setAttribute("wants", wants);//心愿单通知
		session.setAttribute("unread", unread);//未读通知
		session.setAttribute("manager", manager);//系统通知
		
		if (informs != null) 
		{
			try
			{
				InformDaoImpl.close();//关闭连接
			} catch (SQLException e)
			{
				e.printStackTrace();
			}	
			href = "/inform";//跳转至jsp显示表单内容
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
