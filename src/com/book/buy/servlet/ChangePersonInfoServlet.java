package com.book.buy.servlet;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
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

import com.book.buy.dao.LocationDao;
import com.book.buy.dao.UserDao;
import com.book.buy.factory.LocationDaoImpFactory;
import com.book.buy.factory.UserDaoImpFactory;
import com.book.buy.vo.LocationVo;
import com.book.buy.vo.UserVo;

/**
 * 修改用户信息的servlet
 * Servlet implementation class ChangePersonInfoServlet
 */
@WebServlet("/ChangePersonInfoServlet")
public class ChangePersonInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
        public ChangePersonInfoServlet() {
            super();
        }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
	    String newPath = null;
	    String extName = null;
	    String newName = null;
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
	    String mima = (String) request.getAttribute("mima");
	    String phoneNumber = (String) request.getAttribute("tel");
	    String qq = (String) request.getAttribute("qq");
	    String dorName = (String) request.getAttribute("yuanqu");
	    String dorNum = (String) request.getAttribute("jihaolou");
	    String floorNum = (String) request.getAttribute("jilingji");
	    
	    UserVo userVo = (UserVo) request.getSession().getAttribute("user");
	    userVo.setPassword(mima);
	    userVo.setPhoneNumber(phoneNumber);
	    userVo.setQq(qq);
	    userVo.setHeadPhoto(newPath);
	    
	    LocationVo locationVo = (LocationVo) request.getSession().getAttribute("location");
	    if(locationVo == null){
		locationVo  = new LocationVo(dorName, Integer.parseInt(dorNum), Integer.parseInt(floorNum));
	    }else{
		locationVo.setDorName(dorName);
		locationVo.setDorNum(Integer.parseInt(dorNum));
		locationVo.setFloorNum(Integer.parseInt(floorNum));
	    }
	    //更新数据并加入session
	    UserDao userDao = null;
	    LocationDao locationDao = null;
	    try {
		userDao = UserDaoImpFactory.getUserDaoImpl();
		userDao.updateUser(userVo);
		request.getSession().setAttribute("user", userVo);
		
		locationDao = LocationDaoImpFactory.getLocationDaoImp();
		locationDao.UpdateLocation(locationVo);
		
	    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    
	    
	    locationDao.close();
	    userDao.close();
	    
	    response.sendRedirect("/personInfo");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
		throws ServletException, IOException {
		doGet(request, response);
	}

}
