package com.insigma.webservice.junit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.insigma.webservice.client.ClientCall;
import com.insigma.webservice.client.JkDTO;
import com.insigma.webservice.exception.AppException;

/**
 * junit≤‚ ‘
 * @author Administrator
 *
 */
public class TestJk211003 {
	
	@Before
	public void before(){
		System.out.println("@before");
	}
	
	@Test
	public void test(){
		try{
			for (int i=0;i<1;i++){
				JkDTO dto=new JkDTO();
			    dto.setBac010("1000048479");
				//dto.setAac001("0000000000485233");
				dto.setAac002("330104196212213346");
				dto.setAac003("–Ï∑Ô’‰5");
				dto.setAac004("01");//–‘±
				System.out.println(i);
				new ClientCall().jk_212003(dto);
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
