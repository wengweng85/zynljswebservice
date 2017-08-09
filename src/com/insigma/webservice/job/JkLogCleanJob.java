package com.insigma.webservice.job;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;

/**
 * ����ͳһ�Ż�ϵͳͬ���ӿڶ�ʱ��
 * @author wengsh
 */
public class JkLogCleanJob {
	
	
	private HBFactory hbfactory;
	
	
	
	public HBFactory getHbfactory() {
		return hbfactory;
	}



	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}

	public void work() {
		 try{
			 Connection conn =hbfactory.getConnection();
			 //ɾ��һ����ǰ����־
			 String sql="delete from  JK_CLIENT_LOG t where t.jk_call_begin<=sysdate-30";
			 Statement stat= conn.createStatement();
			 int updatecount=stat.executeUpdate(sql);
			 System.out.println("ɾ������־��¼��:"+updatecount);
			 conn.commit();
		 }catch(AppException e){
			 e.printStackTrace();
		 }catch(SQLException e){
			 e.printStackTrace();
		 }
	}

}
