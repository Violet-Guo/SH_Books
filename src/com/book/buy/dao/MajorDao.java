package com.book.buy.dao;


import java.sql.SQLException;
import java.util.List;

import com.book.buy.vo.*;
public interface MajorDao 
{
	
	/**
	 * 根据id显示院系专业年级
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public MajorVo showMajor(int id) throws SQLException;
	
	/**
	 * 显示所有院系专业年级
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public List<MajorVo> showAll() throws SQLException;
	
	/**
	 * 根据专业年级查询major
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public MajorVo getMajorByNG(String name,int grade) throws SQLException;
	
	/**
	 * 根据院系专业年级查询major
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public MajorVo getMajorByAll(String department,String name,int grade) throws SQLException;
	
	/**
	 * 根据院系名称查询该院系所有专业名称distinct
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public List<MajorVo> showNamesByDepartment(String department) throws SQLException;
	
	
	/**
	 * 显示所有院系distinct
	 * @param Major
	 * @throws SQLException
	 */
	public List<MajorVo> showdepartment() throws SQLException;
	
	/**
	 * 显示所有专业distinct
	 * @param Major
	 * @throws SQLException
	 */
	public List<MajorVo> showname() throws SQLException;

	/**
	 * add
	 * @param Major
	 * @throws SQLException
	 */
	public void addMajor(MajorVo Major)throws SQLException;

	
	/**
	 * del
	 * @param name
	 * @throws SQLException
	 */
	public void deleteMajor(MajorVo Major) throws SQLException;
	
	/**
	 * close
	 */
	public void close();
	
	/**
	 * 显示所有专业和年级
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public List<MajorVo> showAllNG() throws SQLException;
	
	/**
	 * 根据department显示专业和年级
	 * @param department
	 * @return
	 * @throws SQLException
	 */
	public List<MajorVo> showNGByDepartment(String department) throws SQLException;
}
