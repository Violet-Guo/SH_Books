package com.book.buy.servlet;

import com.book.buy.dao.ComplainDao;
import com.book.buy.dao.InformDao;
import com.book.buy.factory.ComplainDaoImpFactory;
import com.book.buy.factory.InformDaoImplFactory;
import com.book.buy.utils.NewDate;
import com.book.buy.vo.ComplainVo;
import com.book.buy.vo.InformVo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by violet on 2015/12/14.
 */
@WebServlet(name = "complainfailServlet", urlPatterns = "/complainfail")
public class complainfailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

        String href = "";
        PrintWriter out = response.getWriter();

        String book = (String)request.getParameter("bookid");
        String comp = (String)request.getParameter("compid");
        String appeal = (String)request.getParameter("appealid");

        int bookid = Integer.parseInt(book);

        ComplainVo compvo = new ComplainVo();
        InformVo informvo = new InformVo();

        InformDao informdao = InformDaoImplFactory.getInformDaoImpl();
        ComplainDao compdao = ComplainDaoImpFactory.getCompDaoImp();

        if (comp == null){    //处理申诉
            int appealid = Integer.parseInt(appeal);
            try {
                compvo = compdao.getCompById(appealid);
                compvo.setState(1);

                compdao.updateComp(compvo);

                //申诉失败，给卖家发一条消息
                informvo.setUserID(compvo.getUserid());
                informvo.setType(7);
                informvo.setNum(compvo.getId());
                Date date = new Date();
                String time = NewDate.getDateTime(date);
                informvo.setTime(time);
                informdao.addInform(informvo);

                compdao.close();
                informdao.close();

                href = "/getappealdetil?appid="+appealid;
                out.print("<script language='javascript'>alert('申诉处理成功');"
                        + "window.location.href='" + href + "';</script>");
                return;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else if (appeal == null){    //处理投诉
            int compid = Integer.parseInt(comp);
            try {
                compvo = compdao.getCompById(compid);
                compvo.setState(1);          //投诉显示已处理

                compdao.updateComp(compvo);

                compdao.close();
                informdao.close();

                href = "/getcompdetil?appid="+compid;
                out.print("<script language='javascript'>alert('投诉处理成功');"
                        + "window.location.href='" + href + "';</script>");
                return;

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
