package com.insigma.webservice.infostruct.enums;

/**
 * ���׷�����
 * @author wengsh
 *
 */
public enum TradeCode {
	
	SUCCESS("0000","���׳ɹ�"),
	;
	
	//���׷�����
	private String code;
	//���׷�����˵�� 
	private String name;
	
	private TradeCode(String code,String name){
		this.code=code;
		this.name=name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
