package com.insigma.webservice.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.webservice.client.ServiceCall;
import com.insigma.webservice.exception.AppException;

/**
 * ����ͳһ�Ż�ϵͳͬ���ӿڶ�ʱ��
 * @author wengsh
 */
public class JkAc01SynJob {
	
	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}
	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}
	Log log=LogFactory.getLog(JkJob.class);
	public void work() {
		System.out.println("����ͳһ�Ż�ϵͳ��ѯ�޸Ĺ��ĸ�����Ϣ�ӿ�,��ǰʱ��Ϊ��"+new Date().toLocaleString());
		try{
			//ac01���¹�����Աͬ��
			servicecall.jk_212004(1);
		}catch(AppException e){
			e.printStackTrace();
		}
	}

}
