package com.book.buy.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
	    String method = request.getParameter("method");
	    String newPath = null;
	    String extName = null;
	    String newName = null;
	    Integer mark = 1;
	    //获取表单数据
	    DiskFileItemFactory factory = new DiskFileItemFactory();
		
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			List<FileItem> list = upload.parseRequest(request);
			
			for(int i = 0; i < list.size(); ++i){
				FileItem item = list.get(i);
				if(item.isFormField()){
				    String name = item.getFieldName();
				    String value = new String(item.getString().getBytes("ISO-8859-1"), "utf-8");
				    request.setAttribute(name, value);
				}else{
				    //上传图片
				    	mark = 0;
					String filename = item.getName();
					extName = filename.substring(filename.lastIndexOf("."));
					newName = UUID.randomUUID().toString();
					String rootPath = request.getServletContext().getRealPath("/images");
					newPath = rootPath + "/" + newName + extName;
					item.write(new File(newPath));
					request.getSession().setAttribute("newImagePath", newPath);
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
	    newPath = "/SH_Books/images/" + newName + extName;
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
	    Integer hasNote = 0, canBargain = 0, state = 0;
	    if(pchoice1 != null && pchoice1.equals("keyijia"))
		canBargain = 1;
	    if(pchoice2 != null && pchoice2.equals("youbiji"))
		hasNote = 1;
	    String time = NewDate.getDateTime(new Date());
	   
	    BookVo bookVo = new BookVo(name, userVo.getId(), Integer.parseInt(majorID), pubNumber, 
		    Integer.parseInt(oldGrade), publicYear, author, hasNote, newPath, 
		    description, Integer.parseInt(bookNum), Float.parseFloat(price), canBargain, time, state);
	    //添加图书
	    BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
	    try {
		if(Integer.parseInt(method) == 2)
		{
		    bookDao.addBook(bookVo);
		    bookDao.close();
		    response.sendRedirect("#");
		}
		else
		{
		    Integer bookId = (Integer) request.getSession().getAttribute("changeBookId");
		    BookVo bookVo2 = bookDao.findById(bookId);
		    bookVo.setId(bookId);
		    if(mark == 1)
			bookVo.setImagePath(bookVo2.getImagePath());
		    bookDao.updateBook(bookVo);
		    bookDao.close();
		    response.sendRedirect("#?bookName=" + name + "&author=" + author);
		}
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
