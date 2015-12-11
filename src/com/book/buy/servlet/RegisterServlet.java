package com.book.buy.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
        String headPhoto = "/SH_Books/images/touxiang.png";
        Integer cuowu = 0;
        PrintWriter out = response.getWriter();
	String href = "";
	
	//判断学号是否是11位
	if(xuehao.length() != 11){
	    href += "/register";
	    out.print("<script language='javascript'>alert('学号输入错误！！！');"
		    	+ "window.location.href='"+ href + "';</script>");
	    cuowu = -1;
	}
	else{
	    //判断学号是否是11位数字
	    for(int i = 0; i < 11; ++i)
		if(!Character.isDigit(xuehao.charAt(i))){
		    href += "/register";
	    	    out.print("<script language='javascript'>alert('学号输入错误！！！');"
	    		    	+ "window.location.href='"+ href + "';</script>");
	    	    cuowu = -1;
	    	    break;
		}
	    String date = (NewDate.getDate(new Date())).substring(0, 4);
	    //判断学号特征是否为1997到当前的年份的前缀
    	    if(((Integer.parseInt(xuehao.substring(0, 4)) > Integer.parseInt(date)
    		    || (Integer.parseInt(xuehao.substring(0, 4)) < 1997)))){
    	        href += "/register";
    	        out.print("<script language='javascript'>alert('学号输入错误！！！');"
    		    	+ "window.location.href='"+ href + "';</script>");
    	        cuowu = -1;
    	    }
	}
	if(cuowu != -1)
	{
	    //如果学号没有错误则去校园网上去搜索个人信息
	    String nianji = xuehao.substring(0, 4);
            Map<String, String> map = new HashMap<String, String>();
            map.put("nianji", nianji);
            map.put("xuehao", xuehao);
            map.put("mima", mima);
            Document document = null;
            
            //获取页面
            //post提交数据获取去文本			
            try {
		document = Jsoup.connect(sUrl).data(map).post();
            } catch (Exception e) {
        	// 无法连接网络，此处写处理代码
        	boolean isAvailable = false;
        	e.printStackTrace();
            }
            
            Element e = document.getElementsByTag("p").get(1);
            String line = e.text();
            //判断页面上是否出现了一下的错误信息
            cuowu = line.indexOf("系统没有找到你的信息，原因可能如下：");
            if(cuowu != -1){
                href += "/register";
                //错误时判断是否密码为空
                if(mima == null || mima.equals(""))
                    out.print("<script language='javascript'>alert('请输入密码！！！');"
                        + "window.location.href='"+ href + "';</script>");
                else
                    //或者是因为用户不存在
                    out.print("<script language='javascript'>alert('用户不存在！！！');"
                            + "window.location.href='"+ href + "';</script>");
    	    }
            else{
        	//正确的获取到页面后获取学生信息
                Integer index1 = line.indexOf("专业：");
                Integer index2 = line.indexOf("学期");
                if(index1 != -1 && index2 != -1)
                {
                    String zhuanye = line.substring(index1 + 3, index2 - 2);
                    index1 = line.indexOf("院系：");
                    index2 = line.indexOf("专业");
                    String yuanxi = line.substring(index1 + 3, index2 - 2);
            
                    String xueqi = line.substring(line.length() - 1);
            
                    //拿到专业id，获取专业Dao
                    MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
                    MajorVo majorVo = new MajorVo();
                    try {
                        //按照院系，专业，年级拿到对应的专业对象
                        majorVo = majorDao.getMajorByAll(yuanxi, zhuanye, (Integer.parseInt(xueqi) + 1) / 2);
                    } catch (SQLException e2) {
                        // TODO Auto-generated catch block
                        e2.printStackTrace();
                    }
                    //获取系统时间并格式化
                    String time = NewDate.getDateTime(new Date());
                    //新增用户信息
                    UserVo userVo = new UserVo(xuehao, username, headPhoto, mima, majorVo.getId(), time, qq, tel, 0);
                    //获取用户Dao
                    UserDao userDao = UserDaoImpFactory.getUserDaoImpl();
                    try {
                        //增加用户完成验证
                        userDao.addUser(userVo);
                    } catch (SQLException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                    }
                    //关闭数据流
                    majorDao.close();
                    userDao.close();
                    //跳转登录
                    href += "/login";
                    response.getWriter().print("<script language='javascript'>alert('注册成功，请登录！！！');"
                            + "window.location.href='" + href + "';</script>");
                }
                else
                {
                    //跳转到注册
                    href += "/register";
                    response.getWriter().print("<script language='javascript'>alert('系统异常，暂时无法注册！！！');"
                            + "window.location.href='" + href + "';</script>");
                }
            }
	}
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }
}
