package com.book.buy.vo;

import java.io.Serializable;

/**
 * Created by violet on 2015/11/4.
 */
public class ManagerVo implements Serializable{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private int id;
    private String username;
    private String password;

    /**
     * 不带参数的初始化
     */
    public ManagerVo(){}

    /**
     * 带id的初始化
     */
    public void ManageerVo(int id, String username, String password){
        this.id = id;
        this.username = username;
        this.password = password;
    }

    /**
     * 不带id的初始化
     */
    public ManagerVo(String username, String password){
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
