package com.insigma.webservice.server;

import com.insigma.webservice.client.ServiceCall;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostruct.Message;
import com.insigma.webservice.infostructofsebao.Request;
import com.insigma.webservice.infostructofsebao.Response;
import com.insigma.webservice.xml.JaxbUtil;


/**
 * 
 * @author wengsh
 * @date 2012-3-20
 * 
 */
public class WebService {
	
	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}

	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}
	

	/**
	 * 
	 * @param xml
	 * @return
	 * @throws AppException 
	 */
	public String jk_212003(String requestxml) {
		  JaxbUtil jaxb =null;
		  jaxb = new JaxbUtil(Message.class);  
		  //请求message
		  Message requestmsg= jaxb.fromXml(requestxml);
		  //返回message
		  Message returnmsg = servicecall.jk_212003(requestmsg);
		  String responsexml=jaxb.toXml(returnmsg,"UTF-8");
		 
		  return responsexml;
	}
	
	/**
	 * jk_22KOW6
	 * @param xml
	 * @return
	 * @throws AppException 
	 */
	public String jk_22KOW6(String requestxml) {
		  JaxbUtil jaxb =null;
		  jaxb = new JaxbUtil(Request.class,Response.class);  
		  //请求message
		  Request request= jaxb.fromXml(requestxml);
		  //返回message
		  Response response = servicecall.jk_22KOW6(request);
		  String responsexml=jaxb.toXml(response,"UTF-8");
		  return responsexml;
	}
	
	/**
	 * jk_Q1BQ7U
	 * @param xml
	 * @return
	 * @throws AppException 
	 */
	public String jk_Q1BQ7U(String requestxml) {
		  JaxbUtil jaxb =null;
		  jaxb = new JaxbUtil(Request.class,Response.class);  
		//请求message
		  Request request= jaxb.fromXml(requestxml);
		  //返回message
		  Response response = servicecall.jk_Q1BQ7U(request);
		  String responsexml=jaxb.toXml(response,"UTF-8");
		  return responsexml;
	}
}
