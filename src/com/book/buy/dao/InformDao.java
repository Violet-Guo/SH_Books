package com.book.buy.dao;

import java.sql.SQLException;
import java.util.List;

import com.book.buy.vo.InformVo;

public interface InformDao 
{
	
	public void addInform(InformVo informvo) throws SQLException;
	   /**
     * 添加通知信息
     * @param Inform
     * @throws SQLException
     */
	
	public void deleteInform(int userID, int type,int num)throws SQLException;
	   /**
     * 删除通知信息
     * @param Inform
     * @throws SQLException
     */
	
	public List<InformVo> findallbyut(int userID,int type) throws SQLException;
	   /**
     * 根据userid和type显示通知信息
     * @param Infrom
     * @throws SQLException
     */
	
	public List<InformVo> findbyuserid(int userID) throws SQLException;
	   /**
     * 根据userid显示通知信息
     * @param Infrom
     * @throws SQLException
     */
	
	public void close() throws SQLException;
	  /**
     * 释放连接
     * @param Infrom
     * @throws SQLException
     */
	
	public void updateInform(int userID) throws SQLException;
	  /**
     * 根据userid把其hasread都设为已读
     * @param Infrom
	 * @return 
     * @throws SQLException
     */
	
	public List<InformVo> count(int userID) throws SQLException; 
	  /**
     * 未读消息
     * @param Infrom
	 * @return 
     * @throws SQLException
     */
	
	public List<InformVo> wants(int userID) throws SQLException; 
	  /**
   * 心愿单到货通知
   * @param Infrom
	 * @return 
   * @throws SQLException
   */
	
	public List<InformVo> list(int userID) throws SQLException; 
	  /**
 * 订单通知
 * @param Infrom
	 * @return 
 * @throws SQLException
 * 
 *
 */
	public List<InformVo> manager(int userID) throws SQLException;
	
}
