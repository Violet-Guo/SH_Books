package com.book.buy.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 过滤所有的页面设置字符集
 * Servlet Filter implementation class CharSet
 */
@WebFilter("/*")
public class CharSet implements Filter {

        public CharSet() {
        }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
		throws IOException, ServletException {
	    HttpServletRequest req = (HttpServletRequest) request;
	    HttpServletResponse res = (HttpServletResponse) response;
	    
	    req.setCharacterEncoding("UTF-8");
	    res.setCharacterEncoding("UTF-8");
	    res.setContentType("text/html;charset=UTF-8");

		/*//----------宋超添加-----每次安全的请求需要一个唯一识别码,所以在过滤器添加
		Integer sign = (Integer) req.getSession().getAttribute("sign");
		if(sign==null) {
			req.getSession().setAttribute("sign", 0);
		}else{
			System.out.println("sign changed");
			sign ++;
			req.getSession().setAttribute("sign",sign);
		}
		//---------宋超添加----End*/

	    chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
