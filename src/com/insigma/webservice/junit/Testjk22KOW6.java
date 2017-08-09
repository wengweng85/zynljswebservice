package com.insigma.webservice.junit;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.insigma.webservice.client.ClientCall;
import com.insigma.webservice.client.JkDTO;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostructofsebao.Response;

/**
 * junit≤‚ ‘
 * @author Administrator
 *
 */
public class Testjk22KOW6 {
	
	@Before
	public void before(){
		System.out.println("@before");
	}
	
	@Test
	public void test(){
		try{
			for (int i=0;i<10000;i++){
				JkDTO dto=new JkDTO();
			    dto.setAac002("330821198703312878");
				Response response=new ClientCall().jk_22KOW6(dto);
				String rc=response.getHead().getRc();
				List rowlist= response.getBody().getResponsedata().getAae140ows();
				List rowlist2= response.getBody().getResponsedata().getFeeinfogrid();
			    System.out.println("∑µªÿ◊¥Ã¨"+rc);
			}
			
		}catch(AppException e){
			e.printStackTrace();
		}
	}
	
	@After
	public void after(){
		System.out.println("@After");
	}

}
