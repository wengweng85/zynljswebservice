package com.insigma.webservice.infostruct;

import javax.xml.bind.annotation.XmlRootElement;


/**
 * 
 * @author Administrator
 *
 */
@XmlRootElement
public class Row implements java.io.Serializable {
	/**
	 * 
	 * 
	 * 
	����������Ϣ��
	NDNAME	�ڵ��������	string	100	
	NDABBR	�ڵ�������	string	50	
	NDLEAD	�ڵ����������	string	50	
	NDADDR	�ڵ�������ڵ�ַ	string	100	
	CONTACT	��ϵ��	string	50	
	PHONE	��ϵ�绰	string	20	
	PRIORITY	����	number	8	
	GRADE	�����ȼ�	string	10	
	DESCRIBE	����ְ������	string	500	
 
	���˻�����Ϣ��Ŀ
	
	
	LGNAME	��¼��	string	20	
	NAME	����	string	50	
	GENDER	�Ա�	string	1	
	IDNO	���֤����	string	20	
	MPHONE	�ƶ��绰	string	20	
	VPHONE	�ƶ��绰������ţ�	string	20	
	OPHONE	�칫�绰	string	20	
	DUTIES	ְ��	string	500	
	LEVEL	ְ��	string	500	
	POST	��λ	string	500	
	EMAIL	�������䣨������	string	50	
	INEMAIL	�������䣨������	string	50	
	PRIORITY	�û���������������	string	1000	���ݸ�ʽΪ��
	Ŀ�����ID=����[;Ŀ�����2ID=����]
	�û��������ڶ����������;�ָ
	10001=1����ʾ��Ŀ�����10001��������1λ
	10001=0����10001=����ʾ��Ŀ�����10001��������˳��
	10001=��������ʾ��Ŀ�����10001���Ƴ�
	 */
	private String CODE;//ָ�������
	private String VALUE;//�޸ĺ��ֵ
	public String getCODE() {
		return CODE;
	}
	public void setCODE(String code) {
		CODE = code;
	}
	public String getVALUE() {
		return VALUE;
	}
	public void setVALUE(String value) {
		VALUE = value;
	}
	
	
	
	
}
