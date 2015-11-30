package com.book.buy.vo;

import java.io.Serializable;

/**
 * Created by chao on 2015/10/28.
 * version=1.0
 */
public class LocationVo implements Serializable{
    /**
    * 
    */
    private static final long serialVersionUID = 1L;
    
    private int userID;
    private String dorName;
    private int dorNum;
    private int floorNum;

    public LocationVo(){}
    
    public LocationVo(int userID,String dorName,int dorNum,int floorNum){
        this.userID = userID;
        this.dorName = dorName;
        this.dorNum = dorNum;
        this.floorNum = floorNum;
    }
    
    public LocationVo(String dorName,int dorNum,int floorNum){
        this.dorName = dorName;
        this.dorNum = dorNum;
        this.floorNum = floorNum;
    }

    public int getFloorNum() {
        return floorNum;
    }

    public void setFloorNum(int floorNum) {
        this.floorNum = floorNum;
    }

    public int getDorNum() {
        return dorNum;
    }

    public void setDorNum(int dorNum) {
        this.dorNum = dorNum;
    }

    public String getDorName() {
        return dorName;
    }

    public void setDorName(String dorName) {
        this.dorName = dorName;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    @Override
    public String toString(){
	return "LocationVo [userID=" + userID + ", dorName=" + dorName + ", dorNum=" + dorNum + ", floorNum=" + floorNum
			+ "]";
    }
}
