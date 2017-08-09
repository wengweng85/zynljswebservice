package com.insigma.webservice.codeconvert;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;

public class CodeConvertUtil {
	
	private  HBFactory hbfactory;
	
	public HBFactory getHbfactory() {
		return hbfactory;
	}


	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}


	/**
	 * ͨ���ӿڶ�Ӧ��ϵ��ת��ƽ̨�����صĴ���ת��Ϊ�ӿ�ƽ̨��Ӧ����ֵ
	 * @param localcodetype
	 * @param localcodevalue
	 * @return
	 */
	public  String getRometeCodeByLocalCode(String localcodetype,String localcodevalue)  throws  AppException{
		String sql="select aaa107 from aa99 t where t.aaa100='"+localcodetype+"' and aaa102='"+localcodevalue+"'";
		String aaa107="";                                                                   
		JdbcTemplate jdbctemplate=hbfactory.getJdbcTemplate();
		aaa107=jdbctemplate.queryForObject(sql,String.class );
		return aaa107;
	}
	
	
	/**
	 * ͨ���ӿڶ�Ӧ��ϵ��ת��ƽ̨���ӿڴ���ת��Ϊ���ش���
	 * @param localcodetype
	 * @param localcodevalue
	 * @return
	 */
	public  String getLocalCodeByRemoteCode(String localcodetype,String remotecodevalue)  throws  AppException{
		String sql="select aaa102 from aa99 t where t.aaa100='"+localcodetype+"' and aaa107='"+remotecodevalue+"'";
		String aaa102="";                                                                  
		JdbcTemplate jdbctemplate=hbfactory.getJdbcTemplate();
		aaa102=jdbctemplate.queryForObject(sql,String.class );
		return aaa102;
	}

}
