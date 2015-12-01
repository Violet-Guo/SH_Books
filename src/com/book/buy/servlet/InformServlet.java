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















import com.book.buy.dao.InformDao;
import com.book.buy.factory.InformDaoImplFactory;










import com.book.buy.vo.InformVo;
import com.book.buy.vo.UserVo;

import javax.servlet.http.HttpSession;
/**
 * Servlet implementation class Inform
 */
@WebServlet("/Inform")
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
		//userID=1;
		UserVo userVo=new UserVo();
	   userVo=(UserVo)session.getAttribute("user");
		userID=userVo.getId();
		session.setAttribute("userID", userID);
			//userID=1;
			//out.print(userID);
		//userID= (Integer) session.getAttribute("userID");
		//out.print(userID);
		String href = "";// 跳转的界面
		String strPage=request.getParameter("thisPage");
		System.out.print(strPage);
		session.setAttribute("userID",userID);
		session.setAttribute("thisPage",strPage);
	if (userID == null || userID.intValue() == -1) {
			href = "./pages/managePage/controlCenter/controlCenter.jsp";
			out.print("<script language='javascript'>alert('该用户没有权限！');window.location.href='"
					+ href + "';</script>"); // 页面重定向
		}
	

	
		InformDao InformDaoImpl = InformDaoImplFactory.getInformDaoImpl();
		List<InformVo> informs = null;
		try {
			informs = (List<InformVo>) InformDaoImpl.findbyuserid(userID);
			System.out.println(informs);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	
		
		session.setAttribute("informlist", informs);
		
		
		if (informs != null) {
		try {
				InformDaoImpl.updateInform(userID);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		href = "./pages/managePage/inform/inform.jsp";
			out.print("<script language='javascript'>window.location.href='"
					+ href + "';</script>"); // 页面重定向
		
		} else {
			href = "./pages/managePage/inform/inform.jsp";
			out.print("<script language='javascript'>alert('信息有误或无到货书籍！');window.location.href='"
					+ href + "';</script>");
		}
	
	out.flush();
	out.close();
	
	}

	
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		doGet(request, response);
	}

}
