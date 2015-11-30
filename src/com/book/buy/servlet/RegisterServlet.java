package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.book.buy.dao.MajorDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.MajorDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.MajorVo;
import com.book.buy.vo.UserVo;

/**
 * 处理学生注册验证信息
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public RegisterServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    //获取参数
	    String xuehao = request.getParameter("xuehao");
	    String username = request.getParameter("username");
	    String mima = request.getParameter("mima");
	    String tel = request.getParameter("tel");
	    String qq = request.getParameter("QQ");
	    String sUrl = "http://jw.zzu.edu.cn/scripts/qscore.dll/search";
	    String nianji = xuehao.substring(0, 4);
	    String headPhoto = "/SH_Books/images/touxiang.png";
	    
            Map<String, String> map = new HashMap<String, String>();
            map.put("nianji", nianji);
            map.put("xuehao", xuehao);
            map.put("mima", mima);
            
            //获取页面
            Document document = Jsoup.connect(sUrl).data(map).post();
            Element e = document.getElementsByTag("p").get(1);
	    String line = e.text();
	    
	    Integer index1 = line.indexOf("专业：");
	    Integer index2 = line.indexOf("学期");
	    String zhuanye = line.substring(index1 + 3, index2 - 2);
	    index1 = line.indexOf("院系：");
	    index2 = line.indexOf("专业");
	    String yuanxi = line.substring(index1 + 3, index2 - 2);
	    
	    String xueqi = line.substring(line.length() - 1);
	    
	    //拿到专业id
	    MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
	    MajorVo majorVo = null;
	    try {
		majorVo = majorDao.getMajorByAll(yuanxi, zhuanye, (Integer.parseInt(xueqi) + 1) /2);
	    } catch (SQLException e2) {
		// TODO Auto-generated catch block
		e2.printStackTrace();
	    }
	    
	    String time = NewDate.getDateTime(new Date());
	    UserVo userVo = new UserVo(xuehao, username, headPhoto, mima, majorVo.getId(), time, qq, tel, 0);
	    
	    UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
	    try {
		//增加用户完成验证
		userDao.addUser(userVo);
	    } catch (SQLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	    }
	    
	    majorDao.close();
	    userDao.close();
	    //跳转登录
	    String href = "/SH_Books/pages/personPage/login/Login.jsp";
	    response.getWriter().print("<script language='javascript'>alert('注册成功，请登录！！！');"
		    + "window.location.href='"+ href + "';</script>");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}
}
