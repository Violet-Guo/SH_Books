package com.book.buy.vo;

/**
 * Created by Nvpiao on 2015/10/28.
 */
public class UserVo {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private String name;
    private String username;
    private String headPhoto;
    private String password;
    private Integer majorID;
    private String time;
    private String qq;
    private String phoneNumber;
    private Integer complainNum;
    
    public UserVo() {
	super();
	// TODO Auto-generated constructor stub
    }

    public UserVo(String name, String username, String headPhoto, String password, Integer majorID, String time,
	    String qq, String phoneNumber, Integer complainNum) {
	super();
	this.name = name;
	this.username = username;
	this.headPhoto = headPhoto;
	this.password = password;
	this.majorID = majorID;
	this.time = time;
	this.qq = qq;
	this.phoneNumber = phoneNumber;
	this.complainNum = complainNum;
    }

    public UserVo(Integer id, String name, String username, String headPhoto, String password, Integer majorID,
	    String time, String qq, String phoneNumber, Integer complainNum) {
	super();
	this.id = id;
	this.name = name;
	this.username = username;
	this.headPhoto = headPhoto;
	this.password = password;
	this.majorID = majorID;
	this.time = time;
	this.qq = qq;
	this.phoneNumber = phoneNumber;
	this.complainNum = complainNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadPhoto() {
        return headPhoto;
    }

    public void setHeadPhoto(String headPhoto) {
        this.headPhoto = headPhoto;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getMajorID() {
        return majorID;
    }

    public void setMajorID(Integer majorID) {
        this.majorID = majorID;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Integer getComplainNum() {
        return complainNum;
    }

    public void setComplainNum(Integer complainNum) {
        this.complainNum = complainNum;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
	return "UserVo [id=" + id + ", name=" + name + ", username=" + username + ", headPhoto=" + headPhoto
		+ ", password=" + password + ", majorID=" + majorID + ", time=" + time + ", qq=" + qq + ", phoneNumber="
		+ phoneNumber + ", complainNum=" + complainNum + "]";
    }
}
