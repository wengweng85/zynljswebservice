package com.insigma.webservice.job;

import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.webservice.client.ServiceCall;
import com.insigma.webservice.exception.AppException;

/**
 * 调用统一门户系统同步接口定时类
 * @author wengsh
 */
public class JkJob {
	
	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}
	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}
	Log log=LogFactory.getLog(JkJob.class);
	public void work() {
		System.out.println("调用统一门户系统同步接口,当前时间为："+new Date().toLocaleString());
		try{
				//机构同步
			    servicecall.jk_211001(1);
				//个人同步
			    servicecall.jk_211021(1);
			}catch(AppException e){
				e.printStackTrace();
		}
	}

}
