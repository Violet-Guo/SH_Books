package com.book.buy.vo;

import java.io.Serializable;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public class BuyVo implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer orderID;
    private int userID;
    private String time;
    private String sureTime;
    private String moneyTime;
    private int hasEva;

    public BuyVo() {

    }

    public BuyVo(int orderID, int userID, String time, String sureTime, String moneyTime) {
        this.orderID = orderID;
        this.userID = userID;
        this.time = time;
        this.sureTime = sureTime;
        this.moneyTime = moneyTime;
    }

    public BuyVo(int userID, String time, String sureTime, String moneyTime) {
        this.userID = userID;
        this.time = time;
        this.sureTime = sureTime;
        this.moneyTime = moneyTime;
    }

    public String getMoneyTime() {
        //----------------------------------应对从数据库里读出多个0的情况
        if (moneyTime != null)
            moneyTime = moneyTime.substring(0, moneyTime.length() - 2);
        return moneyTime;
    }

    public void setMoneyTime(String moneyTime) {
        this.moneyTime = moneyTime;
    }

    public String getSureTime() {
        if (sureTime != null)
            sureTime = sureTime.substring(0, sureTime.length() - 2);
        return sureTime;
    }

    public void setSureTime(String sureTime) {
        this.sureTime = sureTime;
    }

    public String getTime() {
        if (time != null)
            time = time.substring(0, time.length() - 2);
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public Integer getOrderID() {
        return orderID;
    }

    public void setOrderID(Integer orderID) {
        this.orderID = orderID;
    }

    @Override
    public String toString() {
        return "BuyVo [orderID=" + orderID + ", userID=" + userID + ", time=" + time + ", sureTime=" + sureTime
                + ", moneyTime=" + moneyTime + ", hasEva="+hasEva+"]";
    }

    public int getHasEva() {
        return hasEva;
    }

    public void setHasEva(int hasEva) {
        this.hasEva = hasEva;
    }
}
