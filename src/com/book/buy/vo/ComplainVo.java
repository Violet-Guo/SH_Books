package com.book.buy.vo;

import java.io.Serializable;

/**
 * Created by violet on 2015/10/28.
 * 15/11/11 添加字段bookid
 */
public class ComplainVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private int userid;
    private int bookid;
    private String description;
    private int complainType;
    private int state;     //默认值是0

    /*不带参数的初始化*/
    public ComplainVo(){
        super();
    }

    /*带参数的初始化*/
    public ComplainVo(int id, int userid, String description){
        super();
        this.id = id;
        this.userid = userid;
        this.description = description;
    }

    /*不带id的初始化*/
    public ComplainVo(int userid, String description){
        super();
        this.userid = userid;
        this.description = description;
    }

    public int getBookid() {
        return bookid;
    }

    public void setBookid(int bookid) {
        this.bookid = bookid;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getComplainType() {
        return complainType;
    }

    public void setComplainType(int complainType) {
        this.complainType = complainType;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
	return "ComplainVo [id=" + id + ", userid=" + userid + ", description=" + description + ", state=" + state
		+ "]";
    }
}
