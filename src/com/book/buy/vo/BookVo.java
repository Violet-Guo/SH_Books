package com.book.buy.vo;

import java.io.Serializable;

/**
*	图书的对象实体
*	@author Nvpiao
*	@time:2015年10月25日 上午10:06:34
 *  changed by violet：book 的state有三个状态，分别是1、2、3，1代表上架，2代表卖家下架，3代表管理员下架
 */
public class BookVo implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Integer userID;
	private Integer majorID;
	private String pubNumber;
	private Integer oldGrade;
	private String publicYear;
	private String author;
	private Integer hasNote;
	private String imagePath;
	private String description;
	private Integer bookNum;
	private Float price;
	private Integer canBargain;
	private String time;
	private Integer state;
	
	public BookVo() {
	    super();
	}

	public BookVo(String name, Integer userID, Integer majorID, String pubNumber, Integer oldGrade,
		String publicYear, String author, Integer hasNote, String imagePath, String description,
		Integer bookNum, Float price, Integer canBargain, String time, Integer state) {
	    super();
	    this.name = name;
	    this.userID = userID;
	    this.majorID = majorID;
	    this.pubNumber = pubNumber;
	    this.oldGrade = oldGrade;
	    this.publicYear = publicYear;
	    this.author = author;
	    this.hasNote = hasNote;
	    this.imagePath = imagePath;
	    this.description = description;
	    this.bookNum = bookNum;
	    this.price = price;
	    this.canBargain = canBargain;
	    this.time = time;
	    this.state = state;
	}

	public BookVo(Integer id, String name, Integer userID, Integer majorID, String pubNumber, Integer oldGrade,
		String publicYear, String author, Integer hasNote, String imagePath, String description,
		Integer bookNum, Float price, Integer canBargain, String time, Integer state) {
	    super();
	    this.id = id;
	    this.name = name;
	    this.userID = userID;
	    this.majorID = majorID;
	    this.pubNumber = pubNumber;
	    this.oldGrade = oldGrade;
	    this.publicYear = publicYear;
	    this.author = author;
	    this.hasNote = hasNote;
	    this.imagePath = imagePath;
	    this.description = description;
	    this.bookNum = bookNum;
	    this.price = price;
	    this.canBargain = canBargain;
	    this.time = time;
	    this.state = state;
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

	public Integer getUserID() {
	    return userID;
	}

	public void setUserID(Integer userID) {
	    this.userID = userID;
	}

	public Integer getMajorID() {
	    return majorID;
	}

	public void setMajorID(Integer majorID) {
	    this.majorID = majorID;
	}

	public String getPubNumber() {
	    return pubNumber;
	}

	public void setPubNumber(String pubNumber) {
	    this.pubNumber = pubNumber;
	}

	public Integer getOldGrade() {
	    return oldGrade;
	}

	public void setOldGrade(Integer oldGrade) {
	    this.oldGrade = oldGrade;
	}

	public String getPublicYear() {
	    return publicYear;
	}

	public void setPublicYear(String publicYear) {
	    this.publicYear = publicYear;
	}

	public String getAuthor() {
	    return author;
	}

	public void setAuthor(String author) {
	    this.author = author;
	}

	public Integer getHasNote() {
	    return hasNote;
	}

	public void setHasNote(Integer hasNote) {
	    this.hasNote = hasNote;
	}

	public String getImagePath() {
	    return imagePath;
	}

	public void setImagePath(String imagePath) {
	    this.imagePath = imagePath;
	}

	public String getDescription() {
	    return description;
	}

	public void setDescription(String description) {
	    this.description = description;
	}

	public Integer getBookNum() {
	    return bookNum;
	}

	public void setBookNum(Integer bookNum) {
	    this.bookNum = bookNum;
	}

	public Float getPrice() {
	    return price;
	}

	public void setPrice(Float price) {
	    this.price = price;
	}

	public Integer getCanBargain() {
	    return canBargain;
	}

	public void setCanBargain(Integer canBargain) {
	    this.canBargain = canBargain;
	}

	public String getTime() {
	    return time;
	}

	public void setTime(String time) {
	    this.time = time;
	}

	public Integer getState() {
	    return state;
	}

	public void setState(Integer state) {
	    this.state = state;
	}

	public static long getSerialversionuid() {
	    return serialVersionUID;
	}

	@Override
	public String toString() {
	    return "BookVo [id=" + id + ", name=" + name + ", userID=" + userID + ", majorID=" + majorID
		    + ", pubNumber=" + pubNumber + ", oldGrade=" + oldGrade + ", publicYear=" + publicYear + ", author="
		    + author + ", hasNote=" + hasNote + ", imagePath=" + imagePath + ", description=" + description
		    + ", bookNum=" + bookNum + ", price=" + price + ", canBargain=" + canBargain + ", time=" + time
		    + ", state=" + state + "]";
	}
}
