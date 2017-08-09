package com.insigma.webservice.infostructofsebao.enums;

/**
 * ���׷�����
 * @author wengsh
 *
 */
public enum RcCode {
	
	SUCCESS("0","�ɹ�"),
	FAIL("1","ҵ���쳣"),
	ERROR("2","�������쳣"),
	;
	
	//���׷�����
	private String code;
	//���׷�����˵�� 
	private String name;
	
	private RcCode(String code,String name){
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
