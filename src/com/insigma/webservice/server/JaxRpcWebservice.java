package com.insigma.webservice.server;

import javax.xml.rpc.ServiceException;

import org.springframework.remoting.jaxrpc.ServletEndpointSupport;

@SuppressWarnings("deprecation")
public class JaxRpcWebservice extends ServletEndpointSupport  {
	private WebService webservice;  
    
    protected void onInit() throws ServiceException {  
       //在Spring容器中获取Bean的实例  
    	webservice= (WebService) getApplicationContext().getBean("webservice");  
    }  
        
    public String jk_212003(String requestxml){  
        return webservice.jk_212003(requestxml);
    }  
    
    public String jk_22KOW6(String requestxml){  
        return webservice.jk_22KOW6(requestxml);
    }  
    
    public String jk_Q1BQ7U(String requestxml){  
        return webservice.jk_Q1BQ7U(requestxml);
    }  
    
    
}
