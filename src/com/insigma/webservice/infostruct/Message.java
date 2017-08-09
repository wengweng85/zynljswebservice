package com.insigma.webservice.infostruct;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * ���Ľṹ
 * @author wengsh
 */
@XmlRootElement(name="PACKET")
@XmlAccessorType(XmlAccessType.FIELD)
public class Message implements java.io.Serializable {
	
	private String VERSION="2016";//�汾��
	private String SNDCODE;//���׷���
	
	//@XmlJavaTypeAdapter(CDataAdapter.class)
	private String RCVCODE;//���׽��շ�
	private String MSGCODE;//������Ϣ��
	private String SNDMSGID;//���𷽱���ID
	private String USERCODE;//����Ա���
	private String USERNAME;//����Ա����
	private String SNDTIME;//��������ʱ��
	private String RCVTIME;//������Ӧʱ��
	private Integer DATASIZE;//ҵ��������
	
	@XmlElementWrapper(name="DATALIST")
	@XmlElement(name="DATA")
	
	private List<Data> DATALIST;//ҵ����
	private Integer PAGENO;//ҳ��
	private String EOPSIGN;//βҳ��־
	private String RCVMSGID;//���շ�����ID
	
	/**
	 *  0000	�ɹ�	ҵ����ɹ�
		0001	����ʧ��	�������
		0002	ͨѶ��ʱ	���糬ʱ
		0003	��������ʧ��	��������ʱ����
		0004	������Ϣ���Ӧ�ķ���δ�ҵ�	������Ϣ���Ӧ��Serviceδʵ�ֻ����ô���
		0050	��δ����Ŀ��ϵͳ�������	
		0051	���˻�λIDӳ�����ò�����	
		0052	���˻�λָ������Ϣ������	
		0100	���Ľ���ʧ��	
		0101	��������Ϊ��	
		0102	���׷���Ϊ��	
		0103	���׽��շ�Ϊ��	
		0104	����ҵ����Ϊ��	
		0105	����ҵ�����ж���	
		0500	��β���Ϊ��	
		0501	��ѯ���������������ļ�¼	
		0502	��¼�Ѵ���	
		0503	��¼������	
		1000	ҵ����ʱ����	ҵ����ʱ����
		9999	δ֪ϵͳ����	δ֪ϵͳ����
	 */
	private String STATUS;//����״̬
	private String ERRMSG;//������Ϣ
	private String RESERVED;//Ԥ������
	public String getVERSION() {
		return VERSION;
	}
	public void setVERSION(String version) {
		VERSION = version;
	}
	public String getSNDCODE() {
		return SNDCODE;
	}
	public void setSNDCODE(String sndcode) {
		SNDCODE = sndcode;
	}
	public String getRCVCODE() {
		return RCVCODE;
	}
	public void setRCVCODE(String rcvcode) {
		RCVCODE = rcvcode;
	}
	public String getMSGCODE() {
		return MSGCODE;
	}
	public void setMSGCODE(String msgcode) {
		MSGCODE = msgcode;
	}
	public String getSNDMSGID() {
		return SNDMSGID;
	}
	public void setSNDMSGID(String sndmsgid) {
		SNDMSGID = sndmsgid;
	}
	public String getUSERCODE() {
		return USERCODE;
	}
	public void setUSERCODE(String usercode) {
		USERCODE = usercode;
	}
	public String getUSERNAME() {
		return USERNAME;
	}
	public void setUSERNAME(String username) {
		USERNAME = username;
	}

	
	public String getSNDTIME() {
		return SNDTIME;
	}
	public void setSNDTIME(String sNDTIME) {
		SNDTIME = sNDTIME;
	}
	public String getRCVTIME() {
		return RCVTIME;
	}
	public void setRCVTIME(String rCVTIME) {
		RCVTIME = rCVTIME;
	}
	public Integer getDATASIZE() {
		return DATASIZE;
	}
	public void setDATASIZE(Integer datasize) {
		DATASIZE = datasize;
	}
	public List<Data> getDATALIST() {
		return DATALIST;
	}
	public void setDATALIST(List<Data> datalist) {
		DATALIST = datalist;
	}
	public Integer getPAGENO() {
		return PAGENO;
	}
	public void setPAGENO(Integer pageno) {
		PAGENO = pageno;
	}
	public String getEOPSIGN() {
		return EOPSIGN;
	}
	public void setEOPSIGN(String eopsign) {
		EOPSIGN = eopsign;
	}
	public String getRCVMSGID() {
		return RCVMSGID;
	}
	public void setRCVMSGID(String rcvmsgid) {
		RCVMSGID = rcvmsgid;
	}
	public String getSTATUS() {
		return STATUS;
	}
	public void setSTATUS(String status) {
		STATUS = status;
	}
	public String getERRMSG() {
		return ERRMSG;
	}
	public void setERRMSG(String errmsg) {
		ERRMSG = errmsg;
	}
	public String getRESERVED() {
		return RESERVED;
	}
	public void setRESERVED(String reserved) {
		RESERVED = reserved;
	}
	
	
}

