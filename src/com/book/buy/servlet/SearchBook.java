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
        String fanye = request.getParameter("fenye");
        String majorName = request.getParameter("majorName");
        String bookName = request.getParameter("bookName");
        String method = request.getParameter("method");
        if (request.getSession().getAttribute("mList") == null) {
            MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
            try {
                List<MajorVo> mList = majorDao.showname();
                request.getSession().setAttribute("mList", mList);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            majorDao.close();
        }
        if (majorName != null || (fanye != null && fanye.equals("leibie")))
            ClickSearch(request, response, majorName);
        else if (bookName != null || (fanye != null && fanye.equals("mingzi")))
            NameSearch(request, response, bookName);
        else if (method != null || (fanye != null && fanye.equals("bufen")))
            PartSearch(request, response, method);

        response.sendRedirect("/bookList");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    //点击专业进行类别的搜索
    protected void ClickSearch(HttpServletRequest request, HttpServletResponse response, String majorName)
            throws ServletException, IOException {
        if (majorName != null) {
            request.getSession().setAttribute("majorName", majorName);
            request.getSession().setAttribute("majorNamet", majorName);
            request.getSession().setAttribute("tnianji", null);
            request.getSession().setAttribute("newOld", null);
            request.getSession().setAttribute("nianji", null);
            request.getSession().setAttribute("oldAndNew", null);
        }
        majorName = (String) request.getSession().getAttribute("majorName");
        request.getSession().setAttribute("fanye", "leibie");
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();

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
            List<BookVo> bList = bookDao.findAlByMajorName(majorName, (thisPage - 1) * everyPageNum, everyPageNum);
            request.getSession().setAttribute("bList", bList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookDao.close();
    }

    //按照图书名称进行搜索
    protected void NameSearch(HttpServletRequest request, HttpServletResponse response, String bookName)
            throws ServletException, IOException {
        request.getSession().setAttribute("fanye", "mingzi");
        if (bookName != null) {
            request.getSession().setAttribute("bookName", bookName);
            request.getSession().setAttribute("majorNamet", null);
            request.getSession().setAttribute("tnianji", null);
            request.getSession().setAttribute("newOld", null);
            request.getSession().setAttribute("nianji", null);
            request.getSession().setAttribute("oldAndNew", null);
        }
        bookName = (String) request.getSession().getAttribute("bookName");
        BookDao bookDao = BookDaoImpFactory.getBookDaoImpl();

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
            List<BookVo> bList = bookDao.findAllByName(bookName, (thisPage - 1) * everyPageNum, everyPageNum);
            request.getSession().setAttribute("bList", bList);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookDao.close();
    }

    //拼接的部分搜索
    protected void PartSearch(HttpServletRequest request, HttpServletResponse response, String method)
            throws ServletException, IOException {
        request.getSession().setAttribute("fanye", "bufen");
        String majorName1 = request.getParameter("majorNamet");
        String nianji1 = request.getParameter("nianji");
        String oldAndNew1 = request.getParameter("oldAndNew");
        if (majorName1 != null)
            request.getSession().setAttribute("majorNamet", majorName1);
        if (nianji1 != null)
            request.getSession().setAttribute("nianji", nianji1);
        if (oldAndNew1 != null)
            request.getSession().setAttribute("oldAndNew", oldAndNew1);
        String majorName = (String) request.getSession().getAttribute("majorNamet");
        String nianji = (String) request.getSession().getAttribute("nianji");
        String oldAndNew = (String) request.getSession().getAttribute("oldAndNew");
        String sql = "select id, name, userID, majorID, pubNumber, oldGrade, publicYear, author, hasNote,"
                + " imagePath, description, bookNum, price, canBargain, time, state from book where ";
        Integer mark = 0;
        if (majorName != null && nianji != null) {
            MajorDao majorDao = MajorDaoImpFactory.getmajordaoimpl();
            try {
                MajorVo majorVo = majorDao.getMajorByNG(majorName, Integer.parseInt(nianji));
                sql += "majorID = '" + majorVo.getId() + "' ";
                mark = 1;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } else if (majorName != null) {
            mark = 1;
            sql += "majorID in (select id from major where name = '" + majorName + "') ";
        } else if (nianji != null) {
            mark = 1;
            sql += "majorID in (select id from major where grade = '" + nianji + "') ";
        }

        if(nianji != null) {
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
            List<BookVo> bList = bookDao.findAllByPart(sql + "limit " + (thisPage - 1) * everyPageNum + ", " + everyPageNum + ";");
            request.getSession().setAttribute("bList", bList);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        bookDao.close();
    }
}
