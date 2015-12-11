package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.WantDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.WantDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;
import com.book.buy.vo.WantVo;


/**
 * 将心愿单进行分页
 * 输出该用户所有的心愿单信息
 */
@WebServlet(name="WantListServlet",urlPatterns="/WantListServlet")
public class WantListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	public Integer pagesize;  //每页显示的行数
    public Integer curpage;  //当前页页码
    public Integer maxpage;  //总共页数
    public Integer i; //循环计数
	public Integer[] bookid_arr = null;    
    private List<BookVo> books = null;
	private List<WantVo> wants = null;
	
    public WantListServlet() {
    	pagesize = 5;
        books = new ArrayList<>();  
        wants = new ArrayList<>();
    }
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NullPointerException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		
		UserVo user = (UserVo)request.getSession().getAttribute("user");
		String curpage1 = request.getParameter("curPage");  //获取当前页页码
		if (curpage1 == null){
	    	curpage = 1;
	       }
	    else {
	        curpage = Integer.parseInt(curpage1);
	        if (curpage < 1){
	        	curpage = 1;
	       }
	    }

		WantVo wantVo = new WantVo();
		BookVo bookVo = new BookVo();
		Integer userID = user.getId();
		
		BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
		WantDao wantDao = WantDaoImpFactory.getWantDao();
		
		try{
			wants = wantDao.findAllwant(userID);
			Integer size = wants.size();
			maxpage = size/pagesize + ((size%pagesize)>0?1:0);
			bookid_arr = new Integer[size]; 
			for (i=0; i<size; i++){
				wantVo = (WantVo) wants.get(i);
				bookid_arr[i] = wantVo.getBookID();
			}
			books.clear();
			if(curpage < maxpage){
				for (i= (curpage-1)*5; i<(5*curpage); i++){
				bookVo = bookDao.findById(bookid_arr[i]);
			    books.add(bookVo);
			    request.getSession().setAttribute("books", books);
				}
			}
			else if (maxpage != 0 && curpage >= maxpage){
				curpage = maxpage;
				for (i= (maxpage-1)*5; i<size;i++){
					bookVo = bookDao.findById(bookid_arr[i]);
				    books.add(bookVo);
				    request.getSession().setAttribute("books", books);
				}
			}
			if(maxpage == 0)
			request.getSession().setAttribute("maxpage", 1);
			else
			request.getSession().setAttribute("maxpage", maxpage);
			request.getSession().setAttribute("curpage",curpage);
			bookDao.close();
			wantDao.close();
			request.getRequestDispatcher("/wantlist?curpage="+ curpage + ".jsp").forward(request, response);
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
