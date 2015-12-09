package com.book.buy.vo;

import java.io.Serializable;

/**
 * Created by violet on 2015/11/20.
 * changed by violet on 2015/12/9 删除orderId
 */
public class EvaluateVo implements Serializable {
    private static final long serialVersionUID = 1L;
    private String userID;
    private String sellUserID;
    private String time;
    private String content;

    public EvaluateVo(){
        super();
    }

    public EvaluateVo(String userID, String sellUserID, String time, String content){
        super();
        this.userID = userID;
        this.sellUserID = sellUserID;
        this.time = time;
        this.content = content;

    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getSellUserID() {
        return sellUserID;
    }

    public void setSellUserID(String sellUserID) {
        this.sellUserID = sellUserID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString(){
        return "EvaluateVo [userId=" + userID + ", sellUserID=" + sellUserID + ", " +
                "time=" + time + ", content =" + content + "]";
    }
}
