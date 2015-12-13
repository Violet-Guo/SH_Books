package com.book.buy.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.book.buy.dao.BookDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.UserVo;

/**
 * 发布图书的Servlet
 * Servlet implementation class PublishBookServlet
 */
@WebServlet("/PublishBookServlet")
public class PublishBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public PublishBookServlet() {
	    super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    //获取到method来判断是修改图书的信息还是新增图书
	    String method = request.getParameter("method");
	    String newPath = null;
	    String extName = null;
	    String newName = null;
	    Integer mark = 1;
	    //获取表单数据
	    DiskFileItemFactory factory = new DiskFileItemFactory();
		//获取文件上传的工具
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
		    	//获取所有的文件项
			List<FileItem> list = upload.parseRequest(request);
			
			for(int i = 0; i < list.size(); ++i){
				FileItem item = list.get(i);
				//判断是否不是文件而是普通的表单域
				if(item.isFormField()){
				    String name = item.getFieldName();
				    String value = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				    request.setAttribute(name, value);
				}else{
				    	//上传图片
				    	//判断是否有过图片的上传操作
					if(item.getSize() != 0) {
            					mark = 0;
            					//修改文件名称
						String filename = item.getName();
						extName = filename.substring(filename.lastIndexOf("."));
						newName = UUID.randomUUID().toString();
						String rootPath = request.getServletContext().getRealPath("/images");
						newPath = rootPath + "/" + newName + extName;
						//进行文件上传
						item.write(new File(newPath));
						request.getSession().setAttribute("newImagePath", newPath);
					}
				}
			}
			
		} 
		catch (FileUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    //获取参数
	    newPath = "/images/" + newName + extName;
	    UserVo userVo = (UserVo) request.getSession().getAttribute("user");
	    //UserVo userVo = new UserVo(1, "nihao", "./sdf", "nihao", 1, "nihao", "nihao", "nihao", 0);
	    String name = (String) request.getAttribute("bookName");
	    String price = (String) request.getAttribute("nowPrice");
	    String pubNumber = (String) request.getAttribute("isbn");
	    String oldGrade = (String) request.getAttribute("pselect");
	    String author = (String) request.getAttribute("author");
	    String majorID = (String) request.getAttribute("selectzhuanye");
	    String description = (String) request.getAttribute("description");
	    String bookNum = (String) request.getAttribute("bookNum");
	    String publicYear = (String) request.getAttribute("publicYear");
	    String pchoice1 = (String) request.getAttribute("pchoice1");//被选中的值
	    String pchoice2 = (String) request.getAttribute("pchoice2");//被选中的值
	    PrintWriter out = response.getWriter();
	    
	    String href = "/publishPage";
	    //判断不能有选项为空。除了是否有笔记或者是否能议价
	    if(name == null || price == null || pubNumber == null || oldGrade == null
		    || author == null || majorID == null || description == null 
		    || bookNum == null || publicYear == null || name.equals("") ||
		    price.equals("") || pubNumber.equals("") || oldGrade.equals("")
		    || author.equals("") || majorID.equals("") || description.equals("") 
		    || bookNum.equals("") || publicYear.equals(""))
	    {
		out.print("<script language='javascript'>alert('不能有选项为空！！！');"
		    	+ "window.location.href='"+ href + "';</script>");
	    }
	    else{  
		    int matches = 1;
		    if(publicYear.length() == 10){
			for(int i = 0; i < 10; ++i){
			    if(i == 4 || i == 7){
				if(publicYear.charAt(i) != '-'){
				    matches = 0;
				    break;
				}
			    }
			    else if(!Character.isDigit(publicYear.charAt(i))){
				matches = 0;
				break;
			    }
			}
		    }
		    else
			matches = 0;
		    
		    if(matches == 0)
		    {
			out.print("<script language='javascript'>alert('请按照yyyy-mm-dd输入日期');"
			    	+ "window.location.href='"+ href + "';</script>");
			return;
		    }
		    
		    for(int i = 0; i < bookNum.length(); ++i){
			if(!Character.isDigit(bookNum.charAt(i))){
			    out.print("<script language='javascript'>alert('图书数量有误!!!');"
				    	+ "window.location.href='"+ href + "';</script>");
				return;
			}
		    }
		    
		    try{			
			Float f = Float.parseFloat(price);
		    }catch(NumberFormatException e){
			//e.printStackTrace();
			out.print("<script language='javascript'>alert('价格输入有误!!!');"
			    	+ "window.location.href='"+ href + "';</script>");
			return;
		    }
		    
		    //获取状态和其他的参数
        	    Integer hasNote = 0, canBargain = 0, state = 1;
        	    if(pchoice1 != null && pchoice1.equals("keyijia"))
        		canBargain = 1;
        	    if(pchoice2 != null && pchoice2.equals("youbiji"))
        		hasNote = 1;
        	    String time = NewDate.getDateTime(new Date());
        	    //创建一本新的图书对象
        	    BookVo bookVo = new BookVo(name, userVo.getId(), Integer.parseInt(majorID), pubNumber, 
        		    Integer.parseInt(oldGrade), publicYear, author, hasNote, newPath, 
        		    description, Integer.parseInt(bookNum), Float.parseFloat(price), canBargain, time, state);
        	    //获取图书dao
        	    BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        	    try {
        		//判断method为2时是进行对图书的添加操作，然后将进行对图书的在用户心愿单中添加图书的步骤
        		if(Integer.parseInt(method) == 2)
        		{
        		    bookDao.addBook(bookVo);
        		    Integer bookID = bookDao.getLastInfertID();
        		    bookDao.close();
        		    response.sendRedirect("/UpdateServlet?bookname=" + name + "&author=" + author + "&bookID=" + bookID);
        		}
        		else
        		{
        		    //否则则method == 1则是对图书进行修改
        		    String bookId = (String) request.getSession().getAttribute("changeBookId");
        		    //查找当前需要修改的图书的信息
        		    BookVo bookVo2 = bookDao.findById(Integer.parseInt(bookId));
        		    bookVo.setId(Integer.parseInt(bookId));
        		    //如果用户没有替换原有的图片，则不修改
        		    if (mark == 1)
        			bookVo.setImagePath(bookVo2.getImagePath());
        		    //图书更新操作
        		    bookDao.updateBook(bookVo);
        		    //关闭数据流
        		    bookDao.close();
        		    //跳转到图书详情页面
        		    response.sendRedirect("/ShowBookDetail?bookID=" + bookId);
        		}
        	    } catch (SQLException e) {
        		// TODO Auto-generated catch block
        		e.printStackTrace();
        	    }
	    }
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
