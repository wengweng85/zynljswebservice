package com.insigma.webservice.infostruct.enums;

/**
 * ҵ�񷵻���
 * @author wengsh
 *
 */
public enum BusiCode {
	SUCCESS("0000","�ɹ�"),
	;
	//ҵ�񷵻���
	private String value;
	//ҵ�񷵻���˵�� 
	private String name;
	
	private BusiCode(String value,String name){
		this.value=value;
		this.name=name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	

}
