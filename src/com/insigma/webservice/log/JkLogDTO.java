package com.insigma.webservice.log;

import java.util.Date;



/**
 * �ӿ���־DTO
 * @author wengsh
 * @date 2013-12-9
 *
 */
public class JkLogDTO {
	private String jkId;//����ˮ��
	private String jkkey;//�ӿ���ˮ��
	private String jkSendXml;//������Ϣ
	private String jkType;//�ӿ�����
	private String jkSender;//�ӿڷ���
	private String jkReciver;//�ӿڽ��շ�
	private Date jkCallStart;//��ʼʱ��
	private Date jkCallEnd;//����ʱ��
	private String jkResult;//���ý�� 
	private String jkReturnCode;//ҵ�񷵻���
	private String jkReturnMsg;//ҵ�񷵻���˵��
	private String jkReturnXml;//���ص�xmlͷ
	public String getJkId() {
		return jkId;
	}
	public void setJkId(String jkId) {
		this.jkId = jkId;
	}
	public String getJkkey() {
		return jkkey;
	}
	public void setJkkey(String jkkey) {
		this.jkkey = jkkey;
	}

	
	public String getJkType() {
		return jkType;
	}
	public void setJkType(String jkType) {
		this.jkType = jkType;
	}
	public String getJkSender() {
		return jkSender;
	}
	public void setJkSender(String jkSender) {
		this.jkSender = jkSender;
	}
	
	public String getJkReciver() {
		return jkReciver;
	}
	public void setJkReciver(String jkReciver) {
		this.jkReciver = jkReciver;
	}
	public Date getJkCallStart() {
		return jkCallStart;
	}
	public void setJkCallStart(Date jkCallStart) {
		this.jkCallStart = jkCallStart;
	}
	public Date getJkCallEnd() {
		return jkCallEnd;
	}
	public void setJkCallEnd(Date jkCallEnd) {
		this.jkCallEnd = jkCallEnd;
	}
	public String getJkResult() {
		return jkResult;
	}
	public void setJkResult(String jkResult) {
		this.jkResult = jkResult;
	}
	
	public String getJkReturnCode() {
		return jkReturnCode;
	}
	public void setJkReturnCode(String jkReturnCode) {
		this.jkReturnCode = jkReturnCode;
	}
	public String getJkReturnMsg() {
		return jkReturnMsg;
	}
	public void setJkReturnMsg(String jkReturnMsg) {
		this.jkReturnMsg = jkReturnMsg;
	}
	public String getJkSendXml() {
		return jkSendXml;
	}
	public void setJkSendXml(String jkSendXml) {
		this.jkSendXml = jkSendXml;
	}
	public String getJkReturnXml() {
		return jkReturnXml;
	}
	public void setJkReturnXml(String jkReturnXml) {
		this.jkReturnXml = jkReturnXml;
	}
	
}
