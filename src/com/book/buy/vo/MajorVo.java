package com.book.buy.vo;

import java.io.Serializable;

public class MajorVo implements Serializable
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    private int id;
    private String name;
    private String department;
    private int grade;
    


	public MajorVo()
    {
    }
    
    public MajorVo(int id)
    {
	setId(id);
    }
    
    public MajorVo(String department,String name,int grade)
    {
    	
		setDepartment(department);
		setName(name);
    	setGrade(grade);
    }
    
    public MajorVo(String name,int grade)
    {
    	setName(name);
    	setGrade(grade);
    }
    
    public int getGrade() 
    {
		return grade;
	}

	public void setGrade(int grade) 
	{
		this.grade = grade;
	}
	
    public int getId() {
    	return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
    
    public String getName() {
    	return name;
    }
    
    public void setName(String name) {
    	this.name = name;
    }
    
    public String getDepartment() {
    	return department;
    }
    
    public void setDepartment(String department) {
    	this.department = department;
    }

    @Override
    public String toString() {
	return "MajorVo [id=" + id + ", department=" + department + ", name=" + name  + ", grade=" + grade+ "]";
    }
}
