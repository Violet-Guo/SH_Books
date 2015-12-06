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

    public MajorVo() {
        super();
        // TODO Auto-generated constructor stub
    }

    public MajorVo(String name, String department, int grade) {
        super();
        this.name = name;
        this.department = department;
        this.grade = grade;
    }

    public MajorVo(int id, String name, String department, int grade) {
        super();
        this.id = id;
        this.name = name;
        this.department = department;
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

    public int getGrade() {
        return grade;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "MajorVo [id=" + id + ", name=" + name + ", department="
                + department + ", grade=" + grade + "]";
    }
}
