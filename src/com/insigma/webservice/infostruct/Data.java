package com.insigma.webservice.infostruct;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import com.insigma.webservice.xml.JaxbDateAdapter;
import com.insigma.webservice.xml.JaxbDateTimeAdapter;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Data  implements java.io.Serializable{
	private String DUSERID;//Ŀ��ϵͳ�û�ID
    private String USERID;//�û�ID
    private String NAME;//����
    private String IDNO;//���֤����
    private String ERRMSG;//������Ϣ
    
    private String CGTYPE;//������� 1-������2-�޸ģ�3-ɾ��
    private String CGTIME ;//���ʱ��;
    private String SIZE ;//�޸�������
    
    private String NDID;//�ڵ����ID
    private String DNDID;//Ŀ��ϵͳ�ڵ����ID;
    private String PNDID;//���ڵ����ID
    private String DPNDID;//���ڵ����ӳ���Ŀ��ϵͳ�ڵ�������ڵ�ID
    
    private String BAC010;//Ŀ��ϵͳ����ID
    private String AAC002;//֤������
    private String AAC003;//����
    
	private String 	AAC001;//����ID 
	
	@XmlJavaTypeAdapter(JaxbDateAdapter.class) 
	private Date STARTDATE;//��ʼʱ��
	@XmlJavaTypeAdapter(JaxbDateAdapter.class) 
	private Date ENDDATE;//����ʱ��
	private String AAE302;//��������ϵͳ����
	@XmlJavaTypeAdapter(JaxbDateTimeAdapter.class) 
	private Date AAE036;//�������;
	
	


	@XmlJavaTypeAdapter(JaxbDateAdapter.class) 
    private Date  AAC006 ;//��������
    private String  AAC004	;//�Ա�
    private String  AAC005	;//����	
    private String  BAC001;//����
    private String  AAC161;//����
    private String  AAC009	;//��������	
    private String  AAC700	;//���ڲ�����
   	private String  BAC004	;//��������
   	private String  AAC069	;//�뻧����ϵ
   	private String  BAC006;//��ͥ��Ա����
   	private String  AAC010	;//�������ڵ�
    private String  BAC007	;//����������
    private String  BAC008	;//�������ڽֵ�
    private String  BAC009	;//������������
    private String  AAC017;//����״��
    private String  BAC003	;//������ò
    private String  AAC153;//�Ƿ���
    private String  AAC154	;//�Ƿ����
    private String  AAC086;//�Ƿ�¹ѻ�¶�
    private String  AAC011	;//ѧ��
   	private String  AAC155	;//����ְ��
   	private String  AAC156	;//�Ƿ����������Ա
   	private String  AAE008;//�־�ס��ַ
   	private String  AAE009	;//�־�ס��ַ�ʱ�
    private String  AAE004	;//��ϵ�绰
    private String  AAE159	;//���˵�������
    @XmlJavaTypeAdapter(JaxbDateAdapter.class) 
    private Date  AAC157	;//����ʱ��
    @XmlJavaTypeAdapter(JaxbDateAdapter.class) 
    private Date  AAC158	;//	תҵʱ��
    private String  AAC159	;//תҵʱ���β���ְ��
    
    
    
    public String getBAC010() {
		return BAC010;
	}

	public void setBAC010(String bAC010) {
		BAC010 = bAC010;
	}

	public String getAAC002() {
		return AAC002;
	}

	public void setAAC002(String aAC002) {
		AAC002 = aAC002;
	}

	public String getAAC003() {
		return AAC003;
	}

	public void setAAC003(String aAC003) {
		AAC003 = aAC003;
	}

	public String getAAC001() {
		return AAC001;
	}

	public void setAAC001(String aAC001) {
		AAC001 = aAC001;
	}


	public Date getSTARTDATE() {
		return STARTDATE;
	}

	public void setSTARTDATE(Date sTARTDATE) {
		STARTDATE = sTARTDATE;
	}

	public Date getENDDATE() {
		return ENDDATE;
	}

	public void setENDDATE(Date eNDDATE) {
		ENDDATE = eNDDATE;
	}

	public String getAAE302() {
		return AAE302;
	}

	public void setAAE302(String aAE302) {
		AAE302 = aAE302;
	}

	public Date getAAE036() {
		return AAE036;
	}

	public void setAAE036(Date aAE036) {
		AAE036 = aAE036;
	}
	
	
	public Date getAAC006() {
		return AAC006;
	}

	public void setAAC006(Date aAC006) {
		AAC006 = aAC006;
	}

	public Date getAAC157() {
		return AAC157;
	}

	public void setAAC157(Date aAC157) {
		AAC157 = aAC157;
	}

	public Date getAAC158() {
		return AAC158;
	}

	public void setAAC158(Date aAC158) {
		AAC158 = aAC158;
	}

	public String getAAC004() {
		return AAC004;
	}

	public void setAAC004(String aAC004) {
		AAC004 = aAC004;
	}

	public String getAAC005() {
		return AAC005;
	}

	public void setAAC005(String aAC005) {
		AAC005 = aAC005;
	}

	public String getBAC001() {
		return BAC001;
	}

	public void setBAC001(String bAC001) {
		BAC001 = bAC001;
	}

	public String getAAC161() {
		return AAC161;
	}

	public void setAAC161(String aAC161) {
		AAC161 = aAC161;
	}

	public String getAAC009() {
		return AAC009;
	}

	public void setAAC009(String aAC009) {
		AAC009 = aAC009;
	}

	public String getAAC700() {
		return AAC700;
	}

	public void setAAC700(String aAC700) {
		AAC700 = aAC700;
	}

	public String getBAC004() {
		return BAC004;
	}

	public void setBAC004(String bAC004) {
		BAC004 = bAC004;
	}

	public String getAAC069() {
		return AAC069;
	}

	public void setAAC069(String aAC069) {
		AAC069 = aAC069;
	}

	public String getBAC006() {
		return BAC006;
	}

	public void setBAC006(String bAC006) {
		BAC006 = bAC006;
	}

	public String getAAC010() {
		return AAC010;
	}

	public void setAAC010(String aAC010) {
		AAC010 = aAC010;
	}

	public String getBAC007() {
		return BAC007;
	}

	public void setBAC007(String bAC007) {
		BAC007 = bAC007;
	}

	public String getBAC008() {
		return BAC008;
	}

	public void setBAC008(String bAC008) {
		BAC008 = bAC008;
	}

	public String getBAC009() {
		return BAC009;
	}

	public void setBAC009(String bAC009) {
		BAC009 = bAC009;
	}

	public String getAAC017() {
		return AAC017;
	}

	public void setAAC017(String aAC017) {
		AAC017 = aAC017;
	}

	public String getBAC003() {
		return BAC003;
	}

	public void setBAC003(String bAC003) {
		BAC003 = bAC003;
	}

	public String getAAC153() {
		return AAC153;
	}

	public void setAAC153(String aAC153) {
		AAC153 = aAC153;
	}

	public String getAAC154() {
		return AAC154;
	}

	public void setAAC154(String aAC154) {
		AAC154 = aAC154;
	}

	public String getAAC086() {
		return AAC086;
	}

	public void setAAC086(String aAC086) {
		AAC086 = aAC086;
	}

	public String getAAC011() {
		return AAC011;
	}

	public void setAAC011(String aAC011) {
		AAC011 = aAC011;
	}

	public String getAAC155() {
		return AAC155;
	}

	public void setAAC155(String aAC155) {
		AAC155 = aAC155;
	}

	public String getAAC156() {
		return AAC156;
	}

	public void setAAC156(String aAC156) {
		AAC156 = aAC156;
	}

	public String getAAE008() {
		return AAE008;
	}

	public void setAAE008(String aAE008) {
		AAE008 = aAE008;
	}

	public String getAAE009() {
		return AAE009;
	}

	public void setAAE009(String aAE009) {
		AAE009 = aAE009;
	}

	public String getAAE004() {
		return AAE004;
	}

	public void setAAE004(String aAE004) {
		AAE004 = aAE004;
	}

	public String getAAE159() {
		return AAE159;
	}

	public void setAAE159(String aAE159) {
		AAE159 = aAE159;
	}

	public String getAAC159() {
		return AAC159;
	}

	public void setAAC159(String aAC159) {
		AAC159 = aAC159;
	}
    
    public String getNDID() {
		return NDID;
	}

	public void setNDID(String nDID) {
		NDID = nDID;
	}

	public String getDNDID() {
		return DNDID;
	}

	public void setDNDID(String dNDID) {
		DNDID = dNDID;
	}

	public String getPNDID() {
		return PNDID;
	}

	public void setPNDID(String pNDID) {
		PNDID = pNDID;
	}

	public String getDPNDID() {
		return DPNDID;
	}

	public void setDPNDID(String dPNDID) {
		DPNDID = dPNDID;
	}

	@XmlElementWrapper(name="ROWS")
	@XmlElement(name="ROW")
    private List<Row> ROWS;
	public String getERRMSG() {
		return ERRMSG;
	}

	public void setERRMSG(String errmsg) {
		ERRMSG = errmsg;
	}

	public String getUSERID() {
		return USERID;
	}

	public void setUSERID(String userid) {
		USERID = userid;
	}

	public String getNAME() {
		return NAME;
	}

	public void setNAME(String name) {
		NAME = name;
	}

	public String getIDNO() {
		return IDNO;
	}

	public void setIDNO(String idno) {
		IDNO = idno;
	}

	public String getDUSERID() {
		return DUSERID;
	}

	public  void setDUSERID(String duserid) {
		DUSERID = duserid;
	}

	public String getCGTYPE() {
		return CGTYPE;
	}

	public void setCGTYPE(String cgtype) {
		CGTYPE = cgtype;
	}

	public String getCGTIME() {
		return CGTIME;
	}

	public void setCGTIME(String cgtime) {
		CGTIME = cgtime;
	}

	public String getSIZE() {
		return SIZE;
	}

	public void setSIZE(String size) {
		SIZE = size;
	}

	public List<Row> getROWS() {
		return ROWS;
	}

	public void setROWS(List<Row> rows) {
		ROWS = rows;
	}

}
