package com.insigma.webservice.infostructofsebao;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * xmlת����,�����籣��ѯ�ӿ�
 * @author wengsh
 *
 */
@XmlRootElement
public class SeRow  implements java.io.Serializable{
	
	private String aae044; //��λ���� 
	private String aae140 ;//�������ͣ��������
	private String aac008; //�α�״̬���������
	private String aac030;//���βα�ʱ��
	private String aac049;//�״βα�ʱ��
	
	private String aae003;//����
	private String aae020; //���˽ɷѽ��
	private String aab001;//��λ��д
	private String aab004;//��λ����
	private String aaa115;//�ɷ�����(�������)
	
	public String getAae003() {
		return aae003;
	}
	public void setAae003(String aae003) {
		this.aae003 = aae003;
	}
	public String getAae020() {
		return aae020;
	}
	public void setAae020(String aae020) {
		this.aae020 = aae020;
	}
	public String getAab001() {
		return aab001;
	}
	public void setAab001(String aab001) {
		this.aab001 = aab001;
	}
	public String getAab004() {
		return aab004;
	}
	public void setAab004(String aab004) {
		this.aab004 = aab004;
	}
	public String getAaa115() {
		return aaa115;
	}
	public void setAaa115(String aaa115) {
		this.aaa115 = aaa115;
	}
	public String getAae044() {
		return aae044;
	}
	public void setAae044(String aae044) {
		this.aae044 = aae044;
	}
	public String getAae140() {
		return aae140;
	}
	public void setAae140(String aae140) {
		this.aae140 = aae140;
	}
	public String getAac008() {
		return aac008;
	}
	public void setAac008(String aac008) {
		this.aac008 = aac008;
	}
	public String getAac030() {
		return aac030;
	}
	public void setAac030(String aac030) {
		this.aac030 = aac030;
	}
	public String getAac049() {
		return aac049;
	}
	public void setAac049(String aac049) {
		this.aac049 = aac049;
	}
}
