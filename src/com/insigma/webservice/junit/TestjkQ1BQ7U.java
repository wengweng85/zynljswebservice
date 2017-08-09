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
 * junit²âÊÔ
 * @author Administrator
 *
 */
public class TestjkQ1BQ7U {
	
	@Before
	public void before(){
		System.out.println("@before");
	}
	
	@Test
	public void test(){
		try{
			JkDTO dto=new JkDTO();
		    dto.setAac002("330821198703312878");
		    dto.setYear("2017");
		    dto.setAae140("01");
		    dto.setPagesize("10");
		    dto.setPageindex("1");
		    Response response=new ClientCall().jk_Q1BQ7U(dto);
			String rc=response.getHead().getRc();
			List rowlist= response.getBody().getResponsedata().getAae140ows();
			List rowlist2= response.getBody().getResponsedata().getFeeinfogrid();
		    System.out.println("·µ»Ø×´Ì¬"+rc);
		}catch(AppException e){
			e.printStackTrace();
		}
	}
	
	@After
	public void after(){
		System.out.println("@After");
	}

}
