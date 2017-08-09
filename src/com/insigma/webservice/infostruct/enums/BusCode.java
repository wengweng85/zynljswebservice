package com.insigma.webservice.infostruct.enums;

/**
 * �ӿ�����
 * @author wengsh
 *
 */
public enum BusCode {
	JK_111091("111091","��ѯ����������Ŀ"),
	JK_211001("211001","��ѯ������Ļ�����Ϣ"),
	JK_211002("211002","������Ϣӳ���"),
	JK_211021("211021","��ѯ��������û���Ϣ"),
	JK_211022("211022","�û���Ϣӳ���"),
	
	JK_212001("212001","������Ա������Ϣ"),
	JK_212002("212002","�޸ĸ�����Ϣ"),
	JK_212003("212003","��ѯ������Ϣ"),
	JK_212004("212004","��ѯ�޸Ĺ��ĸ�����Ϣ"),
	
	//�籣�ӿ�
	JK_22KOW6("22KOW6","���˲α���Ϣ��ѯ"),
	JK_Q1BQ7U("Q1BQ7U","��ᱣ�սɷ���Ϣ��ѯ");
	
	private String code;
	private String name;
	
	private BusCode(String code,String name){
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
