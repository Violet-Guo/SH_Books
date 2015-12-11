package com.book.buy.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.buy.dao.BookDao;
import com.book.buy.dao.MajorDao;
import com.book.buy.factory.BookDaoImpFactory;
import com.book.buy.factory.MajorDaoImpFactory;
import com.book.buy.vo.BookVo;
import com.book.buy.vo.MajorVo;

/**
 * 三种查找方法
 * Servlet implementation class SearchBook
 */
@WebServlet("/SearchBook")
public class SearchBook extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public SearchBook() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
	//获取分页信息，获取是搜索方式
        String fanye = request.getParameter("fenye");
        String majorName = request.getParameter("majorName");
        String bookName = request.getParameter("bookName");
        String method = request.getParameter("method");
        //获取专业列表信息
        if (request.getSession().getAttribute("mList") == null) {
            //获取专业Dao
            MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
            try {
        	//获取专业信息
                List<MajorVo> mList = majorDao.showname();
                request.getSession().setAttribute("mList", mList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            //关闭数据流
            majorDao.close();
        }
        //如果专业名或者是在专业名条件下搜索的图书或者是在该条件下进行翻页的则是按照ClickSearch处理
        if (majorName != null || (fanye != null && fanye.equals("leibie")))
            ClickSearch(request, response, majorName);
        //如果是按照图书名称搜索或者是在该条件下进行翻页的则按照NameSearch处理
        else if (bookName != null || (fanye != null && fanye.equals("mingzi")))
            NameSearch(request, response, bookName);
        //如果存在method参数则说明是按照条件去点击进行拼接的搜索或者在此条件下的翻页都按照PartSearch处理
        else if (method != null || (fanye != null && fanye.equals("bufen")))
            PartSearch(request, response, method);
        //最终跳回到图书列表页面
        response.sendRedirect("/bookList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    //点击专业进行类别的搜索
    protected void ClickSearch(HttpServletRequest request, HttpServletResponse response, String majorName)
            throws ServletException, IOException {
	//如果是按照点击主页上的学生的院系专业进行搜索的操作
        if (majorName != null) {
            //如果专业不是为空的话则需要设置图书列表显示上的专业名和显示的专业名都为获取到的专业
            //同时设置以前的搜索条件为空
            request.getSession().setAttribute("majorName", majorName);
            request.getSession().setAttribute("majorNamet", majorName);
            request.getSession().setAttribute("tnianji", null);
            request.getSession().setAttribute("newOld", null);
            request.getSession().setAttribute("nianji", null);
            request.getSession().setAttribute("oldAndNew", null);
        }
        //获取专业名称
        majorName = (String) request.getSession().getAttribute("majorName");
        //设置是由类别进行的搜索，翻页也将由此方法完成
        request.getSession().setAttribute("fanye", "leibie");
        //获取图书Dao
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        //下面一段是进行分页的处理
        int everyPageNum = 10;//<%--读取文章总数和计算分页数--%>
        String strPage = request.getParameter("thisPage");
        Integer thisPage;
        if (strPage == null || strPage.equals("")) {
            thisPage = 1;
        } else {
            thisPage = Integer.valueOf(strPage);
        }//<%--得到当前页数--%>

        int allNum = 0;
        try {
            allNum = bookDao.findAlByMajorName(majorName).size();
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        int pageNum = allNum % everyPageNum == 0 ? allNum / everyPageNum : allNum / everyPageNum + 1;//计算总共多少页数%>
        if (thisPage > pageNum) {
            thisPage = pageNum;
        }
        if (thisPage <= 0) {
            thisPage = 1;
        }
        request.getSession().setAttribute("thisPage", thisPage);
        request.getSession().setAttribute("pageNum", pageNum);

        try {
            //从数据库中拿到相应的页码所对应的数据
            List<BookVo> bList = bookDao.findAlByMajorName(majorName, (thisPage - 1) * everyPageNum, everyPageNum);
            //将数据放置到session中
            request.getSession().setAttribute("bList", bList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //关闭连接
        bookDao.close();
    }

    //按照图书名称进行搜索
    protected void NameSearch(HttpServletRequest request, HttpServletResponse response, String bookName)
            throws ServletException, IOException {
	//设置按照图书名称进行搜索时的翻页操作是按照此方法进行处理的
        request.getSession().setAttribute("fanye", "mingzi");
        if (bookName != null) {
            //如果bookName不为空则需要设置页面上
            request.getSession().setAttribute("bookName", bookName);
            request.getSession().setAttribute("majorNamet", null);
            request.getSession().setAttribute("tnianji", null);
            request.getSession().setAttribute("newOld", null);
            request.getSession().setAttribute("nianji", null);
            request.getSession().setAttribute("oldAndNew", null);
        }
        bookName = (String) request.getSession().getAttribute("bookName");
        //获取图书Dao
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        //分页计算
        int everyPageNum = 10;//<%--读取文章总数和计算分页数--%>
        String strPage = request.getParameter("thisPage");
        Integer thisPage;
        if (strPage == null || strPage.equals("")) {
            thisPage = 1;
        } else {
            thisPage = Integer.valueOf(strPage);
        }//<%--得到当前页数--%>

        int allNum = 0;
        try {
            allNum = bookDao.findAllByName(bookName).size();
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        int pageNum = allNum % everyPageNum == 0 ? allNum / everyPageNum : allNum / everyPageNum + 1;//计算总共多少页数%>
        if (thisPage > pageNum) {
            thisPage = pageNum;
        }
        if (thisPage <= 0) {
            thisPage = 1;
        }
        request.getSession().setAttribute("thisPage", thisPage);
        request.getSession().setAttribute("pageNum", pageNum);

        try {
            //按照页码拿当页的图书
            List<BookVo> bList = bookDao.findAllByName(bookName, (thisPage - 1) * everyPageNum, everyPageNum);
            request.getSession().setAttribute("bList", bList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //关闭流
        bookDao.close();
    }

    //拼接的部分搜索
    protected void PartSearch(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException {
	//设置当点击分页时如果是按照拼接的部分条件进行的搜索的话则设置该条件，让该类来处理分页操作
        request.getSession().setAttribute("fanye", "bufen");
        //获取当前的数据信息
        String majorName1 = request.getParameter("majorNamet");
        String nianji1 = request.getParameter("nianji");
        String oldAndNew1 = request.getParameter("oldAndNew");
        if (majorName1 != null)
            request.getSession().setAttribute("majorNamet", majorName1);
        if (nianji1 != null)
            request.getSession().setAttribute("nianji", nianji1);
        if (oldAndNew1 != null)
            request.getSession().setAttribute("oldAndNew", oldAndNew1);
        //获取原有的数据信息
        String majorName = (String) request.getSession().getAttribute("majorNamet");
        String nianji = (String) request.getSession().getAttribute("nianji");
        String oldAndNew = (String) request.getSession().getAttribute("oldAndNew");
        //初始sql的前缀部分
        String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
                + " imagePath, description, bookNum, price, canBargain, time, state from book where ";
        Integer mark = 0;
        //如果专业年级不是空的话则按照专业id去查找图书的类别
        if (majorName != null && nianji != null) {
            //获取专业Dao
            MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
            try {
        	//按照专业名称和年级查找专业
                MajorVo majorVo = majorDao.getMajorByNG(majorName, Integer.parseInt(nianji));
                sql += "majorID = '" + majorVo.getId() + "' ";
                //设置标志
                mark = 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (majorName != null) {
            //如果只存在专业名则拼接嵌套子查询按照专业名称查询
            mark = 1;
            sql += "majorID in (select id from major where name = '" + majorName + "') ";
        } else if (nianji != null) {
            //如果只存在年级则拼接嵌套子查询按照年级进行查找
            mark = 1;
            sql += "majorID in (select id from major where grade = '" + nianji + "') ";
        }

        if(nianji != null) {
            //如果年级不为空则设置页面显示的年级
            Integer dnianji = Integer.parseInt(nianji);
            String tnianji = null;
            switch (dnianji) {
                case 1:
                    tnianji = "大一";
                    break;
                case 2:
                    tnianji = "大二";
                    break;
                case 3:
                    tnianji = "大三";
                    break;
                case 4:
                    tnianji = "大四";
                    break;
            }
            request.getSession().setAttribute("tnianji", tnianji);
        }

        if (oldAndNew != null) {
            //如果新旧程度存在则设置页面上显示专业
            if(mark == 1)
                sql += " and";
            sql += " oldGrade = '" + oldAndNew + "' ";
            Integer tnewOld = Integer.parseInt(oldAndNew);
            String newOld = null;
            //新旧程度
            if (tnewOld == 10)
                newOld = "全新";
            else if (tnewOld == 9)
                newOld = "九成新";
            else if (tnewOld == 8)
                newOld = "八成新";
            else if (tnewOld == 5)
                newOld = "五成新";
            else if (tnewOld == 4)
                newOld = "五成新以下";
            request.getSession().setAttribute("newOld", newOld);
        }
        //图书dao
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();
        //分页操作
        int everyPageNum = 10;//<%--读取文章总数和计算分页数--%>
        String strPage = request.getParameter("thisPage");
        Integer thisPage;
        if (strPage == null || strPage.equals("")) {
            thisPage = 1;
        } else {
            thisPage = Integer.valueOf(strPage);
        }//<%--得到当前页数--%>

        int allNum = 0;
        try {
            allNum = bookDao.findAllByPart(sql + ";").size();
        } catch (NumberFormatException e1) {
            e1.printStackTrace();
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        int pageNum = allNum % everyPageNum == 0 ? allNum / everyPageNum : allNum / everyPageNum + 1;//计算总共多少页数%>
        if (thisPage > pageNum) {
            thisPage = pageNum;
        }
        if (thisPage <= 0) {
            thisPage = 1;
        }
        request.getSession().setAttribute("thisPage", thisPage);
        request.getSession().setAttribute("pageNum", pageNum);

        try {
            //按照拼接的sql来查找某页的数据
            List<BookVo> bList = bookDao.findAllByPart(sql + "limit " + (thisPage - 1) * everyPageNum + ", " + everyPageNum + ";");
            request.getSession().setAttribute("bList", bList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookDao.close();
    }
}
