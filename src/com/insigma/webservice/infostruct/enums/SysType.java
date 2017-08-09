package com.insigma.webservice.infostruct.enums;

/**
 * ϵͳ����
 * @author wengsh
 *
 */
public enum SysType {
	MHXT("MHXT","������Ϣ��"),
	JYGL("JYGL","��ҵ����"),
	SYTH("SYTH","��ᱣ��һ�廯"),
	RYKH("RYKH","��Ա����"),
	XZGL("XZGL","��������"),
	ZYJS("ZYJS","ְҵ����"),
	BMOA("BMOA","����OA"),
	ZXFW("ZXFW","��ѯ����"),
	RLSB("RLSB","���������籣ƽ̨"),
	LDJC("LDJC","�Ͷ����"),
	DBBS("DBBS","����BBS"),
	BGOA("BGOA","�칫OA"),
	PXJD("PXJD","��ѵ����"),
	;
	
	private String code;
	private String name;
	
	private SysType(String code,String name){
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
