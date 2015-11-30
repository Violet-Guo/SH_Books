package com.book.buy.vo;

/**
 * Created by violet on 2015/10/28.
 */
public class SellVo {
    
    private int userid;
    private int bookid;
    private String time;

    public SellVo(){
        super();
    }

    public SellVo(int userid, int bookid, String time){
        super();
        this.userid = userid;
        this.bookid = bookid;
        this.time = time;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
	return "SellVo [userid=" + userid + ", bookid=" + bookid + ", time=" + time + "]";
    }
}
