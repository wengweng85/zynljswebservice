package com.insigma.webservice.infostructofsebao.enums;

/**
 * ϵͳ����
 * @author wengsh
 *
 */
public enum SystemCode{
	PXJD("PXJD","��ѵ����ϵͳ");
	
	private String code;
	private String name;
	
	private SystemCode(String code,String name){
		this.code=code;
		this.name=name;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}
	
	

}
