package com.book.buy.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import java.io.IOException;

/**
 * Created by chao on 2015/12/3.
 */
public class Paging {
    private int everyPageNum;
    private HttpServletRequest request;
    private int allNum;
    private Integer thisPage;
    private int pageNum;
    private String beforeUrl;

    public Paging(int everyPageNum, HttpServletRequest request,int allNum,String beforeUrl){
        this.everyPageNum = everyPageNum;
        this.request = request;
        this.allNum = allNum;
        this.beforeUrl = beforeUrl;

        String strPage = request.getParameter("thisPage");

        if(strPage==null||strPage.equals("")){
            thisPage = 1;
        }else{
            thisPage = Integer.valueOf(strPage);
        }

        pageNum = allNum%everyPageNum==0?allNum/everyPageNum:allNum/everyPageNum+1;//计算总共多少页数%>

        if(thisPage>pageNum)
            thisPage=pageNum;
        if(thisPage<=0)
            thisPage=1;

        request.setAttribute("thisPage",thisPage);
        request.setAttribute("everyPageNum",everyPageNum);
        request.setAttribute("allNum",allNum);
        request.setAttribute("pageNum",pageNum);
    }

    public void printPage(JspWriter out){
        try {
            if (out != null&&pageNum>1) {
                out.println("<ul id='page'>");
                out.println("<li><a href='" + beforeUrl + "thisPage=" + (thisPage-1) + "'>上一页</a></li>");
                int firstOne = thisPage % 10 == 0 ? (((thisPage - 1) / 10) * 10 + 1) : ((thisPage / 10) * 10 + 1);
                int lastOne = thisPage % 10 == 0 ? (((thisPage - 1) / 10 + 1) * 10) : ((thisPage / 10 + 1) * 10);
                for (int i = firstOne; i <= (lastOne > pageNum ? pageNum : lastOne); i++) {
                    out.println("<li><a " + (thisPage == i ? "id='thisPage'" : "") + " href='" + beforeUrl + "thisPage=" + i + "'>" + i + "</a></li>");
                }
                if (pageNum % 10 > 0 && pageNum / 10 > (thisPage - 1) / 10) {
                    out.print("<a href='"+beforeUrl+"?thisPage=" + ((((thisPage - 1) / 10) + 1) * 10 + 1) + "'>&gt;&gt;</a>");
                }
                out.println("<li><a href='" + beforeUrl + "thisPage=" + (thisPage + 1) + "'>下一页</a></li>");
                out.println("</ul>");
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public int getStart(){
        return (thisPage-1)*everyPageNum;
    }
    public int getEnd(){
        return thisPage*everyPageNum>allNum?allNum:thisPage*everyPageNum;
    }

    public int getEveryPageNum() {
        return everyPageNum;
    }

    public void setEveryPageNum(int everyPageNum) {
        this.everyPageNum = everyPageNum;
    }

    public int getAllNum() {
        return allNum;
    }

    public void setAllNum(int allNum) {
        this.allNum = allNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getThisPage() {
        return thisPage;
    }

    public void setThisPage(Integer thisPage) {
        this.thisPage = thisPage;
    }

    public String getBeforeUrl() {
        return beforeUrl;
    }

    public void setBeforeUrl(String beforeUrl) {
        this.beforeUrl = beforeUrl;
    }
}
