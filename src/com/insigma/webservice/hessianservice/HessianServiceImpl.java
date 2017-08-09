package com.insigma.webservice.hessianservice;

import com.insigma.webservice.client.ServiceCall;
import com.insigma.webservice.infostruct.Message;
import com.insigma.webservice.infostructofsebao.Request;
import com.insigma.webservice.infostructofsebao.Response;

public class HessianServiceImpl implements HessianService {

	private ServiceCall servicecall;
	

	public ServiceCall getServicecall() {
		return servicecall;
	}

	public void setServicecall(ServiceCall servicecall) {
		this.servicecall = servicecall;
	}
	
	@Override
	public Message jk_212003(Message requestmsg) {
		// TODO Auto-generated method stub
		return  servicecall.jk_212003(requestmsg);
	}

	@Override
	public Response jk_22KOW6(Request request) {
		// TODO Auto-generated method stub
		return servicecall.jk_22KOW6(request);
	}

	@Override
	public Response jk_Q1BQ7U(Request request) {
		// TODO Auto-generated method stub
		return servicecall.jk_Q1BQ7U(request);
	}

}
