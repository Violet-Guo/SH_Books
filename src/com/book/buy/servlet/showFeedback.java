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

import com.book.buy.dao.FeedBackDao;
import com.book.buy.factory.FeedBackDaoImplFactory;
import com.book.buy.vo.FeedBackVo;

/**
 * Servlet implementation class showFeedback
 */
@WebServlet("/showFeedback")
public class showFeedback extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public showFeedback() {
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
		 FeedBackDao feedbackDaoImpl = FeedBackDaoImplFactory.getFeedBackDaoImpl();
		
		List<FeedBackVo> feedbacks = null;
		try {
			feedbacks = (List<FeedBackVo>) feedbackDaoImpl.showFeedBack();
			System.out.println(feedbacks);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		 
		session.setAttribute("feedbacklist", feedbacks);
		//String strPage=(String) session.getAttribute("thisPage");
		//Integer strPage = session.getAttribute("thisPage");
		String strPage=request.getParameter("thisPage");
		System.out.print(strPage);
		session.setAttribute("thisPage",strPage);
		feedbackDaoImpl.close();
		//System.out.print(strPage);
		if(feedbacks!=null)
		{
			
			String href="./pages/managePage/showFeedback/showFeedback.jsp";
			out.print("<script language='javascript'>window.location.href='"+href+"';</script>");  //页面重定向
			out.flush();
			out.close();
		}else 
		{
		
			String href="./pages/managePage/showFeedback/showFeedback.jsp";
			out.print("<script language='javascript'>alert('反馈信息为空！');window.location.href='"+href+"';</script>");
		}
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
