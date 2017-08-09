package com.insigma.webservice.client;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;

import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.encoding.XMLType;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.insigma.db.HBFactory;
import com.insigma.webservice.codeconvert.CodeConvertUtil;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostruct.Data;
import com.insigma.webservice.infostruct.Message;
import com.insigma.webservice.infostruct.Row;
import com.insigma.webservice.infostruct.enums.BusCode;
import com.insigma.webservice.infostruct.enums.SysType;
import com.insigma.webservice.infostructofsebao.Head;
import com.insigma.webservice.infostructofsebao.Request;
import com.insigma.webservice.infostructofsebao.Response;
import com.insigma.webservice.infostructofsebao.enums.SystemCode;
import com.insigma.webservice.log.JkLogUtil;
import com.insigma.webservice.util.DateUtil;
import com.insigma.webservice.xml.JaxbUtil;

/**
 * ���÷���
 * @author wengsh
 *
 */
public class ServiceCall  {
	
	private HBFactory hbfactory;
	
	private JkLogUtil jkLogUtil;
	
	private CodeConvertUtil codeConvertUtil;
	
	private Log log=LogFactory.getLog(ServiceCall.class);
	
	public CodeConvertUtil getCodeConvertUtil() {
		return codeConvertUtil;
	}


	public void setCodeConvertUtil(CodeConvertUtil codeConvertUtil) {
		this.codeConvertUtil = codeConvertUtil;
	}


	public JkLogUtil getJkLogUtil() {
		return jkLogUtil;
	}


	public void setJkLogUtil(JkLogUtil jkLogUtil) {
		this.jkLogUtil = jkLogUtil;
	}


	public HBFactory getHbfactory() {
		return hbfactory;
	}


	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}


	/**
	 *��ѯ���������֯�ڵ������Ϣ 211001
	 *
	 *
	Ŀ��ϵͳͨ���˽��׿��Բ�ѯ�Ż�ϵͳ�������������֯�ڵ������Ϣ��������������������֯�ڵ�����������֯�ڵ������Ϣ��ɾ����֯�ڵ�����ȣ����ձ�����͡����ʱ�����򷵻ء�
	������ҵ�����¼��Ϊ0������Ӧ����ҵ�����¼����Ϊ������
	(һ)	�������Ϊ��������ʱ��Ŀ��ϵͳ�ڵ�IDΪ�ա�
	(��)	�������Ϊ��������ʱ��Ŀ��ϵͳ���ٵ��ý���5.1.3������֯�ڵ���Ϣӳ���211002��ʵ���Ż�ϵͳ��Ŀ��ϵͳ����֯�ڵ������Ϣӳ�䡣
	(��)	Ŀ��ҵ��ϵͳͨ���˽��ײ�ѯ�ı��������֯��������������Ϣ������Ϊ�Ѿ���Ŀ��ҵ��ϵͳͬ���������¼����������´β�ѯ����С�
	(��)	��Ӧ�����и�����ID�����ID��ͬ����ʾ�Ǹ���������������������Դ����ᱣ�Ͼ֡�
	(��)	��Ӧ�����и�����ID�͸�����ӳ���Ŀ��ϵͳ����ID���ǵ�ǰ����ʱ��ʵʱ���ݵģ���Ŀ��ϵͳ�ڴ���ʱÿ�ζ�Ҫ�ж���ֵ�����ϵͳ�еĸ�����ID�Ƿ�һ�£�
	��һ��ʱ��ʾ���������޸ġ�
	 *
	 *
	 * @return
	 * @throws AppException
	 */
	public void jk_211001(int pageno) throws AppException{
		
		//��һ�� ͨ���ӿڽ����е�������ȡ�������浽���ǵĻ������ݿ�
		log.info("��ǰ���ýӿ�Ϊ ��ѯ���������֯�ڵ������Ϣ����ǰҳ����Ϊ "+pageno);
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211001.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(pageno);//ҳ��
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			List<Data> list=returnmsg.getDATALIST();
			for(Data data:list){
				String cgtype=data.getCGTYPE();//1-������2-�޸ģ�3-ɾ��
				String cgtime=data.getCGTIME();//���ʱ��
				//�����û�
				String ndid=data.getNDID();//�ڵ����ID
				String dndid=data.getDNDID() ;//Ŀ��ϵͳ�û�id
				String pndid=data.getPNDID() ;//���ڵ����ID
				String dpndid=data.getDPNDID() ;//���ڵ����ӳ���Ŀ��ϵͳ�ڵ�������ڵ�ID
				String size=data.getSIZE() ;//���ڵ����ӳ���Ŀ��ϵͳ�ڵ�������ڵ�ID
				String ndname="";//�ڵ��������	
				String ndabbr="";//�ڵ�������
				String ndlead="";//�ڵ����������	
				String ndaddr="";//�ڵ�������ڵ�ַ	
				String contact="";//��ϵ��	
				String phone="";//��ϵ�绰
				String priority="";//����
				String grade="";//�����ȼ�
				String describe="";//����ְ������
				for(int i=0;i<data.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//NDNAME	�ڵ��������	
					if(row.getCODE().equals("NDNAME")){
						ndname=row.getVALUE();
					}
					//NDABBR	�ڵ�������	
					if(row.getCODE().equals("NDABBR")){
						ndabbr=row.getVALUE();
					}
					//NDLEAD	�ڵ����������
					if(row.getCODE().equals("NDLEAD")){
						ndlead=row.getVALUE();
					}
					//NDADDR	�ڵ�������ڵ�ַ	
					if(row.getCODE().equals("NDADDR")){
						ndaddr=row.getVALUE();
					}
					//CONTACT	��ϵ��	string	50	
					if(row.getCODE().equals("CONTACT")){
						contact=row.getVALUE();
					}
					//PHONE	��ϵ�绰	string	20	
					if(row.getCODE().equals("PHONE")){
						phone=row.getVALUE();
					}
					//PRIORITY	����	number	8	
					if(row.getCODE().equals("PRIORITY")){
						priority=row.getVALUE();
					}
					//GRADE	�����ȼ�	string	10	
					if(row.getCODE().equals("GRADE")){
						grade=row.getVALUE();
					}
					//DESCRIBE	����ְ������	string	500	
					if(row.getCODE().equals("DESCRIBE")){
						describe=row.getVALUE();
					}
				}
			    CallableStatement proc=null;
			    Connection conn =hbfactory.getConnection();
				try {
					proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoGroup(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
					proc.setString(1, cgtype);// cgtype
					proc.setString(2, cgtime);// cgtime
					proc.setString(3, ndid);// ndid
					proc.setString(4, dndid);// dndid
					proc.setString(5, pndid);// pndid
					proc.setString(6, dpndid);// dpndid
					proc.setString(7, size);// size
					proc.setString(8, ndname);// ndname
					proc.setString(9, ndabbr);//ndabbr
					proc.setString(10, ndlead);// ndlead
					proc.setString(11, ndaddr);// ndaddr
					proc.setString(12, contact);//contact
					proc.setString(13, phone);// phone
					proc.setString(14, priority);// priority
					proc.setString(15, grade);// grade
					proc.setString(16, describe);// describe
					proc.execute();
					conn.commit();
					if(proc!=null){
						proc.close();
					}
					if(conn!=null){
						conn.close();
					}
				} catch (Exception e) {
					e.printStackTrace();
					try {
						conn.rollback();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} 
			}
			
			log.info("��ǰ���ýӿ�Ϊ ��ѯ���������֯�ڵ������Ϣ���Ƿ������һҳ: "+returnmsg.getEOPSIGN()); 
			//�ж��Ƿ������һҳ,����������һҳ���ٴ���ȡ����
			if(returnmsg.getEOPSIGN().equals("0")){
				jk_211001(pageno++);
			}
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		
		
		//�ڶ���
		//�������е�����������ݣ����ݲ������ʹ������ݲ����´�����
		
		String sql="SELECT t.uuid, t.cgtype,t.ndid   from sso_group t where t.isop='0'   order by t.cgtype,t.cgtime ";
		try{
			Connection conn =hbfactory.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cgtype=rs.getString("cgtype");//��������
				String uuid=rs.getString("uuid");
				String ndid=rs.getString("ndid");//�ڵ����ID
				//String dndid=rs.getString("dndid");//Ŀ��ϵͳ�ڵ����ID
				//�������в��׳�
				try{
					CallableStatement proc=null;
				    proc = conn.prepareCall("{ call PKG_SSO_PROCESS.ssoGroupUpdate(?,?) }");
					proc.setString(1, uuid);// uuid
					proc.registerOutParameter(2, Types.VARCHAR);
					proc.execute();
					conn.commit();
					//�����ɵ�groupid
					String groupid = proc.getString(2);
					
					if(proc!=null){
						proc.close();
					}
					//���������
					if(cgtype.equals("1")){
						jk_211002(ndid,groupid);
					}
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
			
		   if(rs!=null){
			 rs.close();
		   }
		 
		   if(stmt!=null){
			 stmt.close();
		   }
		   
		   if(conn!=null){
			   conn.close();
		   }
			
			
		}catch(SQLException e){
			e.printStackTrace();
		}
		
		
	 }
	
	
	/**
	 *��֯�ڵ����������Ϣӳ��� 211002
	 *
	 *
	 * Ŀ��ϵͳ����ͨ���˽�����ʵ���Ż�ϵͳ��Ŀ��ϵͳ����֯�ڵ������Ϣӳ�䡣
     * ������ҵ�����¼��Ϊ��������Ӧ����ҵ�����¼��Ϊ��������Ӧ����ҵ���������ӳ��󶨳���ļ�¼��������Ϣ��
     *
	 *
	 * @return
	 * @throws AppException
	 */
	public Message jk_211002(String ndid,String dndid) throws AppException{
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211002.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(1);
		List <Data> datalist=new ArrayList<Data>();
		Data data=new Data();
		data.setNDID(ndid); //�ڵ����ID
		data.setDNDID(dndid);;//Ŀ��ϵͳ�ڵ����ID
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg=call(message);
		
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			log.info("�ɹ�");
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		return returnmsg;
	}

	
	
	/**
	 *  5.1.4	��ѯ��������û���Ϣ211021
	 * Ŀ��ϵͳͨ���˽��׿��Բ�ѯ�Ż�ϵͳ������������û���Ϣ�������������������û�������û���Ϣ��ɾ���û���Ϣ�ȣ����ձ�����͡����ʱ�����򷵻ء�
	 *	������ҵ�����¼��λ0������Ӧ����ҵ�����¼����Ϊ������
	 *	(һ)	�������Ϊ��������ʱ��Ŀ��ϵͳ�û�IDΪ�ա�
	 *	(��)	�������Ϊ��������ʱ��Ŀ��ϵͳ���ٵ��ý���5.1.5�û���Ϣӳ���211022��ʵ���Ż�ϵͳ��Ŀ��ϵͳ���û���Ϣӳ��󶨡�
	 *	(��)	Ŀ��ҵ��ϵͳͨ���˽��ײ�ѯ�ı�������û���Ϣ������Ϊ�Ѿ���Ŀ��ҵ��ϵͳͬ���������¼����������´β�ѯ����С�
     *
	 *
	 *
	 * @return
	 * @throws AppException
	 */
	public void jk_211021(int pageno) throws AppException{
		//��һ�� ͨ���ӿڽ����е�������ȡ�������浽���ǵĻ������ݿ�
		log.info("��ǰ���ýӿ�Ϊ ��ѯ���������Ա��Ϣ��Ϣ����ǰҳ����Ϊ "+pageno);
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211021.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(pageno);//ҳ��
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			List<Data> list=returnmsg.getDATALIST();
			for(Data data:list){
				String cgtype=data.getCGTYPE();//1-������2-�޸ģ�3-ɾ��
				String cgtime=data.getCGTIME();//���ʱ��
				//�����û�
				String userid=data.getUSERID ();//�û�ID
				String duserid=data.getDUSERID() ;//Ŀ��ϵͳ�û�id
				String size=data.getSIZE() ;//���ڵ����ӳ���Ŀ��ϵͳ�ڵ�������ڵ�ID
				String LGNAME	="" ;//��¼��	string	20	
				String NAME=""	 ;//����	string	50	
				String GENDER	="";//�Ա�	string	1	
				String IDNO="" ;//	���֤����	string	20	
				String MPHONE="";//	�ƶ��绰	string	20	
				String VPHONE=""	;//�ƶ��绰������ţ�	string	20	
				String OPHONE="";//	�칫�绰	string	20	
				String DUTIES=""	;//ְ��	string	500	
				String LEVEL=""	;//ְ��	string	500	
				String POST=""	;//��λ	string	500	
				String EMAIL="";//	�������䣨������	string	50	
				String INEMAIL="";//	�������䣨������	string	50	
				String PRIORITY="";//	�û�������֯��������������	
				
				for(int i=0;i<data.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//LGNAME	��¼��
					if(row.getCODE().equals("LGNAME")){
						LGNAME=row.getVALUE();
					}
					//NAME	����
					if(row.getCODE().equals("NAME")){
						NAME=row.getVALUE();
					}
					//GENDER	�Ա�
					if(row.getCODE().equals("GENDER")){
						GENDER=row.getVALUE();
					}
					//IDNO	���֤����
					if(row.getCODE().equals("IDNO")){
						IDNO=row.getVALUE();
					}
					//MPHONE	�ƶ��绰
					if(row.getCODE().equals("MPHONE")){
						MPHONE=row.getVALUE();
					}
					//VPHONE	�ƶ��绰 ������ţ�
					if(row.getCODE().equals("VPHONE")){
						VPHONE=row.getVALUE();
					}
					//�ƶ��绰
					if(row.getCODE().equals("OPHONE")){
						OPHONE=row.getVALUE();
					}
					//�칫�绰
					if(row.getCODE().equals("DUTIES")){
						DUTIES=row.getVALUE();
					}
					//ְ��
					if(row.getCODE().equals("LEVEL")){
						LEVEL=row.getVALUE();
					}
					//��λ
					if(row.getCODE().equals("POST")){
						POST=row.getVALUE();
					}
					//�������䣨������
					if(row.getCODE().equals("EMAIL")){
						EMAIL=row.getVALUE();
					}
					//INEMAIL	 ��������
					if(row.getCODE().equals("INEMAIL")){
						INEMAIL=row.getVALUE();
					}
					//PRIORITY	�û�������֯��������������	
					if(row.getCODE().equals("PRIORITY")){
						PRIORITY=row.getVALUE();
					}
				}
			   CallableStatement proc=null;
			   Connection conn =hbfactory.getConnection();
				try {
					proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoUser(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
					proc.setString(1, cgtype);// cgtype
					proc.setString(2, cgtime);// cgtime
					proc.setString(3, userid);// userid
					proc.setString(4, duserid);// duserid
					proc.setString(5, size);// size
					proc.setString(6, LGNAME);// LGNAME
					proc.setString(7, NAME);// NAME
					proc.setString(8, GENDER);// GENDER
					proc.setString(9, IDNO);// IDNO
					proc.setString(10, MPHONE);//MPHONE
					proc.setString(11, VPHONE);// VPHONE
					proc.setString(12, OPHONE);// OPHONE
					proc.setString(13, DUTIES);//DUTIES
					proc.setString(14, LEVEL);// LEVEL
					proc.setString(15, POST);// POST
					proc.setString(16, EMAIL);// EMAIL
					proc.setString(17, INEMAIL);// INEMAIL
					proc.setString(18, PRIORITY);// PRIORITY
					proc.execute();
					conn.commit();
					if(proc!=null){
						proc.close();
					}
				    if(conn!=null){
					   conn.close();
				    }
				} catch (Exception e) {
					e.printStackTrace();
					try {
						conn.rollback();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
				} 
			}
			
			log.info("��ǰ���ýӿ�Ϊ ��ѯ���������Ա��Ϣ���Ƿ������һҳ: "+returnmsg.getEOPSIGN()); 
			//�ж��Ƿ������һҳ,����������һҳ���ٴ���ȡ����
			if(returnmsg.getEOPSIGN().equals("0")){
				jk_211001(pageno++);
			}
		}
		
		//�ڶ���
		//�������е�����������ݣ����ݲ������ʹ������ݲ����´�����
		String sql="SELECT t.uuid, t.userid, t.cgtype,t.cgtime  from sso_user t where t.isop='0' order by t.cgtype,t.cgtime ";
		try{
			Connection conn =hbfactory.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cgtype=rs.getString("cgtype");//��������
				String uuid=rs.getString("uuid");
				String userid=rs.getString("userid");//�ڵ����ID
			    CallableStatement proc=null;
			    proc = conn.prepareCall("{ call PKG_SSO_PROCESS.ssoUserUpdate(?,?) }");
			    log.info(uuid);
				proc.setString(1, uuid);// uuid
				proc.registerOutParameter(2, Types.VARCHAR);
				proc.execute();
				conn.commit();
				//�����ɵ�groupid
				String pxjduserid = proc.getString(2);
				if(proc!=null){
					proc.close();
				}
				//���������
				if(cgtype.equals("1")){
					jk_211022(userid,pxjduserid);
				}
			}
			
		   if(rs!=null){
			 rs.close();
		   }
		 
		   if(stmt!=null){
			 stmt.close();
		   }
		   if(conn!=null){
				conn.close();
			}
		}catch(SQLException e){
			
		}
	 }
	
	/**
	 *  �����û���Ϣ211022
	 * @return
	 * @throws AppException
	 */
	public Message jk_211022(String  userid,String duserid ) throws AppException{
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211022.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) ); //20160809 10:28:25
		List <Data> datalist=new ArrayList<Data>();
		Data data=new Data();
		data.setUSERID(userid);//�û�id
		data.setDUSERID(duserid);//Ŀ��ϵͳid
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			log.info("�ɹ�");
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		return returnmsg;
	}
	
	
	/**
	 * ��ѯ������Ϣ212003
	 * 
	 * Ŀ��ϵͳͨ���˽��׿��Բ�ѯָ��Ŀ��ϵͳ����ID�����������֤�������Ӧ�ĸ�����Ϣ��
	 * ���������ҵ�����е�Ŀ��ϵͳ����ID��Ϊ�գ�����Ŀ��ϵͳ����ID��ȷ������ӳ��ĸ��˻�����Ϣ��û���ҵ�����ʾ������Ϣ��
	 * ���������ҵ�����е�Ŀ��ϵͳ����IDΪ�գ��������������֤�������Ҹ��˻�����Ϣ�����������֤�ű�����һ����ֵ��
	 * �˽��׿��ܷ��ض������������ĸ�����Ϣ
	 * ����Ŀ��ϵͳ�����ж�ѡ������������Ϣ�������Ӧ������ĳ��������Ϣ�е�Ŀ��ϵͳ����IDΪ�գ����ʾĿ��ϵͳ��δ��ø�����Ϣ���и�����Ϣӳ���
	 * ��Ŀ��ϵͳ���Ե��ý���5.2.2�޸ĸ�����Ϣ212002��ʵ�ָ�����Ϣӳ��󶨡� ������ҵ�����¼��Ϊ1������Ӧ����ҵ�����¼��Ϊ������
	 * 
	 * @param BAC010
	 *            Ŀ��ϵͳ����ID
	 * @param AAC002
	 *            ֤������
	 * @param AAC003
	 *            ����
	 * @return
	 * @throws AppException
	 */
	public Message jk_212003(Message message)  {
		Message returnmsg=null;
		try{
			message.setSNDCODE(SysType.PXJD.getCode());
			message.setRCVCODE(SysType.MHXT.getCode());
			message.setMSGCODE(BusCode.JK_212003.getCode());
			message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
			message.setUSERCODE("admin");
			message.setUSERNAME("����Ա");
			message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
			JkDTO dto = new JkDTO();
			
			//Ŀ��ϵͳ����id
			Data data=message.getDATALIST().get(0);
			dto.setBac010(data.getBAC010());
			dto.setAac002(data.getAAC002());
			dto.setAac003(data.getAAC003());
			dto.setAac004(data.getAAC004());
			
			//���Ŀ��ϵͳ��ż�������ѯ�ֶΣ�ֻ�����֤�����ѯ
			message.getDATALIST().get(0) .setBAC010("");
			message.getDATALIST().get(0) .setAAC003("");
			
			//����ͳһ�û����Ľӿ�
			returnmsg= call(message);
			String status = returnmsg.getSTATUS();
			// ���ýӿڳɹ�
			if (status.equals("0000")) {
				List<Data> datareturnlist = returnmsg.getDATALIST();
				if(datareturnlist.size()>0){
					for (Data returndata : datareturnlist) {
						//���ص�ͳһ�Ż����˱��
						String return_data_aac001 = returndata.getAAC001();
						String return_data_bac010 = returndata.getBAC010();
						dto.setAac001(return_data_aac001);
						
						log.info("��ͳһ�û����Ĵ���,�û�idΪ"+return_data_aac001);
						
						// ��ͬ�������ĸ�����Ϣд�뵽sso_ac01��
						CallableStatement proc = null;
						Connection conn = hbfactory.getConnection();
						proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoAc01(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
						proc.setString(1, return_data_aac001);// ����ID
						proc.setString(2, return_data_bac010);// Ŀ��ϵͳ����ID
						proc.setString(3, returndata.getAAC003());// ����
						proc.setString(4, returndata.getAAC002());// ��ᱣ�Ϻ�(������ݺ���)
						proc.setDate(5, returndata.getAAC006()==null?null:new java.sql.Date(returndata.getAAC006().getTime()));// ��������
						proc.setString(6, returndata.getAAC004());// �Ա�
						proc.setString(7, returndata.getAAC005());// ����
						proc.setString(8, returndata.getBAC001());// ����
						proc.setString(9, returndata.getAAC161());// ����
						proc.setString(10, returndata.getAAC009());// ��������
						proc.setString(11, returndata.getAAC700());// ���ڲ�����
						proc.setString(12, returndata.getBAC004());// ��������
						proc.setString(13, returndata.getAAC069());// �뻧����ϵ
						proc.setString(14, returndata.getBAC006());// ��ͥ��Ա����
						proc.setString(15, returndata.getAAC010());// �������ڵ�
						proc.setString(16, returndata.getBAC007());// ����������
						proc.setString(17, returndata.getBAC008());// �������ڽֵ�
						proc.setString(18, returndata.getBAC009());// ������������
						proc.setString(19, returndata.getAAC017());// ����״��
						proc.setString(20, returndata.getBAC003());// ������ò
						proc.setString(21, returndata.getAAC153());// �Ƿ���
						proc.setString(22, returndata.getAAC154());// �Ƿ����
						proc.setString(23, returndata.getAAC086());// �Ƿ�¹ѻ�¶�
						proc.setString(24, returndata.getAAC011());// ѧ��
						proc.setString(25, returndata.getAAC155());// ����ְ��
						proc.setString(26, returndata.getAAC156());// �Ƿ����������Ա
						proc.setString(27, returndata.getAAE008());// �־�ס��ַ
						proc.setString(28, returndata.getAAE009());// �־�ס��ַ�ʱ�
						proc.setString(29, returndata.getAAE004());// ��ϵ�绰
						proc.setString(30, returndata.getAAE159());// ���˵�������
						proc.execute();
						conn.commit();
						if (proc != null) {
							proc.close();
						}
						if (conn != null) {
							conn.close();
						}
					
						// ������ص�Ŀ��ϵͳ���û�id�գ������޸Ľӿڰ󶨸��˻�����Ϣ
						if (return_data_bac010.equals("")) {
							log.info("��ͳһ�û����Ĵ��������,��Ŀ���û�idΪ�գ�������Ա�޸Ľӿ����Ӹ���Ŀ���û�id");
							// �޸�
							conn = hbfactory.getConnection();
							proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoAc01Update(?,? }");
							proc.setString(1, dto.getAac001());// ����ID
							proc.setString(2, dto.getBac010());// Ŀ��ϵͳ����ID
							proc.execute();
							conn.commit();
							if (proc != null) {
								proc.close();
							}
							if (conn != null) {
								conn.close();
							}
							jk_212002(dto);
						}else{
							String return_data_aac003=returndata.getAAC003();
							//�����������һ��
							if(return_data_aac003.equals(dto.getAac003())){
								log.info("��ͳһ�û����Ĵ��������,����ѵ�������еĸ�������һ��");
							}else{
								log.info("��ͳһ�û����Ĵ��������,������ѵ�������еĸ���������һ��,��ѵ�������еĸ�������Ϊ:"+dto.getAac003()+";ͳһ�û������е�����Ϊ:"+return_data_aac003+",��Ҫ������ѵ�������еĸ�������");
								// �޸�����
								conn = hbfactory.getConnection();
								proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synssoac01_aac003_update(?,?,?)}");
								proc.setString(1, dto.getAac001());// ����ID
								proc.setString(2, dto.getBac010());// Ŀ��ϵͳ����ID
								proc.setString(3, return_data_aac003);// Ŀ��ϵͳ����ID
								proc.execute();
								conn.commit();
								if (proc != null) {
									proc.close();
								}
								if (conn != null) {
									conn.close();
								}
							}
						}
					}
				}
				// �����ѯ�������˻�����Ϣ�����������ӿ�
				else{
					log.info("��ͳһ�û����Ĳ����������,���������ӿ���������˵���Ϣ");
					// ����
					return jk_212001(dto);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnmsg;
	}
	
	/**
	 * ��ѯ������Ϣ212003
	 * 
	 * Ŀ��ϵͳͨ���˽��׿��Բ�ѯָ��Ŀ��ϵͳ����ID�����������֤�������Ӧ�ĸ�����Ϣ��
	 * ���������ҵ�����е�Ŀ��ϵͳ����ID��Ϊ�գ�����Ŀ��ϵͳ����ID��ȷ������ӳ��ĸ��˻�����Ϣ��û���ҵ�����ʾ������Ϣ��
	 * ���������ҵ�����е�Ŀ��ϵͳ����IDΪ�գ��������������֤�������Ҹ��˻�����Ϣ�����������֤�ű�����һ����ֵ��
	 * �˽��׿��ܷ��ض������������ĸ�����Ϣ
	 * ����Ŀ��ϵͳ�����ж�ѡ������������Ϣ�������Ӧ������ĳ��������Ϣ�е�Ŀ��ϵͳ����IDΪ�գ����ʾĿ��ϵͳ��δ��ø�����Ϣ���и�����Ϣӳ���
	 * ��Ŀ��ϵͳ���Ե��ý���5.2.2�޸ĸ�����Ϣ212002��ʵ�ָ�����Ϣӳ��󶨡� ������ҵ�����¼��Ϊ1������Ӧ����ҵ�����¼��Ϊ������
	 * 
	 * @param BAC010  Ŀ��ϵͳ����ID
	 * @param AAC002 ֤������
	 * @param AAC003 ����
	 * @return
	 * @throws AppException
	 */
	private void jk_212003(JkDTO jkac01dto) throws AppException {
		Message message = new Message();
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		// ���Ŀ��ϵͳ����ID��Ϊ��
		//data.setBAC010(jkac01dto.getBac010());//Ŀ��ϵͳ����ID
		data.setAAC002(jkac01dto.getAac002());// ֤������
		data.setAAC003(jkac01dto.getAac003());// ����
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = jk_212003(message);
		String status = returnmsg.getSTATUS();
		log.info(status);
			
	}

	/**
	 * ������Ա������Ϣ212001
	 * 
	 * Ŀ��ϵͳ��ͨ��֤�������ѯ������Ϣ�����û�в�ѯ��������Ϣ����ͨ���˽�������������Ϣ������������ô˽��ף���Ҫ���������Ϣ������ý���5.2.2
	 * �޸ĸ�����Ϣ212002�� ���ô˽���ʱ���Ż�ϵͳ�ڱ��������Ϣʱ��ͬʱ���и�����Ϣӳ��󶨡�
	 * ������ҵ�����¼��Ϊ1������Ӧ����ҵ�����¼����Ϊ1����
	 *
	 * @return
	 * @throws AppException
	 */
	private Message jk_212001(JkDTO dto) throws AppException {
		Message message = new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_212001.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setBAC010(dto.getBac010());// Ŀ��ϵͳ����ID
		data.setAAC003(dto.getAac003());// ����
		data.setAAC002(dto.getAac002());// ��ᱣ�Ϻ�(������ݺ���)
		data.setAAC004(codeConvertUtil.getRometeCodeByLocalCode("AAC004",dto.getAac004()));// �Ա�
		//data.setAAC006(dto.getAac006());// ��������
		data.setAAC005("01");// ���� Ĭ�Ϻ���
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			log.info("�����û��ɹ�");
			//�ٴε��ò�ѯ�ӿ�
			log.info("�ٴε��ò�ѯ�ӿ�");
			jk_212003(dto);
		} else {
			String errmsg = returnmsg.getERRMSG();
			log.info(status + ":" + errmsg);
		}
		return returnmsg;
	}

	/**
	 * �޸ĸ�����Ϣ212002
	 * 
	 * @return
	 * @throws AppException
	 */
	private Message jk_212002(JkDTO dto) throws AppException {
		Message message = new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_212002.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setAAC001(dto.getAac001());// �Ż�ϵͳ���˱��
		data.setBAC010(dto.getBac010());// Ŀ��ϵͳ���˱��(���ǵ�ϵͳ)
		data.setSIZE("1");
		// �޸ĵ���ϸ��Ŀ
		List<Row> rowlist = new ArrayList<Row>();
		data.setROWS(rowlist);
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			log.info("�޸İ󶨹�ϵ�ɹ�");
		} else {
			String errmsg = returnmsg.getERRMSG();
			log.info(status + ":" + errmsg);
		}
		return returnmsg;
	}
	
	
	
	/**
	 * ��ѯ�޸Ĺ��ĸ�����Ϣ
	 * 
	 * @return
	 * @throws AppException
	 */
	public Message jk_212004(int pageno) throws AppException {
		Message message = new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_212004.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("����Ա");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		message.setPAGENO(pageno);//ҳ��																
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setSTARTDATE(new Date());
		data.setENDDATE(new Date());
		data.setSIZE("1");
		// �޸ĵ���ϸ��Ŀ
		List<Row> rowlist = new ArrayList<Row>();
		data.setROWS(rowlist);
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			for(Data returndata:returnmsg.getDATALIST()){
				String aae302=data.getNDID();// ��������ϵͳ����
				String aae036=data.getDNDID() ;//�������
				String aac001=data.getPNDID() ;//��ԱID
				String bac010=data.getDPNDID() ;//Ŀ��ϵͳ��ԱID
				String size=data.getSIZE() ;//size
				String aac003="";
				
				log.info("��������ϵͳ����:"+aae302+";aae036"+aae036+";size:"+size+";bac010:"+bac010+"aac001:"+aac001);
				for(int i=0;i<returndata.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//AAC003	�����޸�
					if(row.getCODE().equals("AAC003")){
						aac003=row.getVALUE();
					}
				}
			    if(null!=aac003&&!aac003.equals("")){
			    	CallableStatement proc=null;
					Connection conn =hbfactory.getConnection();
					 try {
							proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synssoac01_aac003_update(?,?,?)}");
							proc.setString(1, aac001);
							proc.setString(2, bac010);
							proc.setString(3, aac003);
							proc.execute();
							conn.commit();
							if(proc!=null){
								proc.close();
							}
						    if(conn!=null){
							   conn.close();
						    }
						} catch (Exception e) {
							e.printStackTrace();
							try {
								conn.rollback();
							} catch (Exception ex) {
								ex.printStackTrace();
							}
						} 
					}
			    }
				
				log.info("��ѯ�޸Ĺ��ĸ�����Ϣ���Ƿ������һҳ: "+returnmsg.getEOPSIGN()); 
				//�ж��Ƿ������һҳ,����������һҳ���ٴ���ȡ����
				if(returnmsg.getEOPSIGN().equals("0")){
					jk_211001(pageno++);
				}
				
			
		} else {
			String errmsg = returnmsg.getERRMSG();
			log.info(status + ":" + errmsg);
		}
		return returnmsg;
	}
	
	
	/**
	 *���˲α���Ϣ��ѯ��22KOW6��
	 *
	 * @return
	 * @throws AppException
	 */
	public Response jk_22KOW6(Request request) {
		Response response=call(request,BusCode.JK_22KOW6);
		return response;
		
	 }
	
	
	/**
	 *��ᱣ�սɷ���Ϣ��ѯ��Q1BQ7U��
	 *
	 * @return
	 * @throws AppException
	 */
	public Response jk_Q1BQ7U(Request request){
		Response response=call(request,BusCode.JK_Q1BQ7U);
		return response;
	 }
	

	/**
	 * webservice����
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private  Message call(Message message) {
	  JaxbUtil jaxb =null;
	  String jkid="";
		try{
			//java����ת��ΪXML�ַ��� 
	        jaxb = new JaxbUtil(Message.class);  
	        //�����xml */
	        String requestxml=jaxb.toXml(message,"UTF-8");
	        //��¼��־*/
			jkid = jkLogUtil.addLog(message,requestxml);
			log.info("server requestxml->"+requestxml);
	        //����webservice���� 
			/** ����webservice���񲢷���xml */
			Service service = new Service();
			Call call = (Call) service.createCall();
			//����webservice��ַ
			//call.setTargetEndpointAddress("http://172.16.69.155:9002/hzrssjjh/services/DataExchangeServer?wsdl");
			//��ʽwebservice��ַ
			call.setTargetEndpointAddress("http://172.16.29.49:7001/hzrssjjh/services/DataExchangeServer?wsdl");
			call.setOperationName(new QName("http://local.hzrssjjh.wondersgroup.com", "execute"));
			call.addParameter("requestData", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String responsexml = (String) call.invoke(new Object[] {requestxml});
			//���� xmlת����java���� */
			Message returnmsg=jaxb.fromXml(responsexml);
			log.info("server responsexml->"+responsexml);
			//��¼��־
			jkLogUtil.updateSuccessLog(returnmsg,jkid,responsexml);
			return returnmsg;
		}catch(Exception e){
			//������÷����쳣
			e.printStackTrace();
			//��������־
			try{
				jkLogUtil.updateErrorLog(jkid,e.getMessage());
			}catch(AppException ex){
				message.setSTATUS("9999");
			    message.setERRMSG(ex.getMessage());
			    return message;
			}
		    message.setSTATUS("9999");
		    message.setERRMSG(e.getMessage());
		    return message;
			//throw new AppException(e.getMessage());
		}
	}
	
	/**
	 * webservice����
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private  Response call(Request request,BusCode tradeCode) {
	  JaxbUtil jaxb =null;
	  String jkid="";
		try{
			//java����ת��ΪXML�ַ��� 
	        jaxb = new JaxbUtil(Request.class,Response.class);  
	        //�����xml */
	        String requestxml=jaxb.toXml(request,"UTF-8");
	        //��¼��־*/
			jkid = jkLogUtil.addLog(requestxml);
			log.info("server requestxml->"+requestxml);
	        //����webservice���� 
			/** ����webservice���񲢷���xml */
			Service service = new Service();
			Call call = (Call) service.createCall();
			//����webservice��ַ
			//call.setTargetEndpointAddress("http:// 172.16.29.59:9002/nws/services/NwsServer");
			//��ʽwebservice��ַ
			call.setTargetEndpointAddress("http://172.16.69.150:9001/nws/services/NwsServer");
			call.setOperationName(new QName("http://local.hzrssjjh.wondersgroup.com", "nwsReceive"));
			call.addParameter("systemCode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("tradeCode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("xml", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String responsexml = (String) call.invoke(new Object[] {SystemCode.PXJD.getCode(),tradeCode.getCode(),requestxml});
			
			//String responsexml="<?xml version=\"1.0\" encoding=\"utf-8\"?><response><head> <rc>��Ӧ��ţ��������</rc><rm>�쳣��Ϣ</rm></head><body><responsedata><basicform><aac003>����</aac003> <aac002>���֤��</aac002> <aac999>���˱��</aac999> <aac004>�Ա𣨼������</aac004> <aac005>���壨�������</aac005><aac006>��������</aac006> <aac007>�μӹ�������</aac007> <aac084>�����ݱ�ʶ���������</aac084><akc021>ҽ����Ա��𣨼������</akc021>	<aac024>������ò���������</aac024> <aac009>�������ʣ��������</aac009> <aac010>�������ڵ���ϸ��ַ</aac010><aac011>�Ļ��̶ȣ��������</aac011><aac013>�ù���ʽ���������</aac013></basicform><feeinfogrid><row><aae003>����</aae003><aae140>��������(�������)</aae140><aae180>�ɷѻ���</aae180><aae020>���˽ɷѽ��</aae020><aab001>��λ���</aab001> <aab004>��λ����</aab004> <aaa115>�ɷ�����(�������)</aaa115></row><row><aae003>����</aae003><aae140>��������(�������)</aae140><aae180>�ɷѻ���</aae180><aae020>���˽ɷѽ��</aae020><aab001>��λ���</aab001> <aab004>��λ����</aab004> <aaa115>�ɷ�����(�������)</aaa115></row><row><aae003>����</aae003><aae140>��������(�������)</aae140><aae180>�ɷѻ���</aae180><aae020>���˽ɷѽ��</aae020><aab001>��λ���</aab001> <aab004>��λ����</aab004> <aaa115>�ɷ�����(�������)</aaa115></row></feeinfogrid><aae140ows><row><aae044>��λ����</aae044><aae140>�������ͣ��������</aae140><aac008>�α�״̬���������</aac008><aac030>���βα�ʱ��</aac030><aac049>�״βα�ʱ��</aac049></row><row><aae044>��λ����</aae044><aae140>�������ͣ��������</aae140><aac008>�α�״̬���������</aac008><aac030>���βα�ʱ��</aac030><aac049>�״βα�ʱ��</aac049></row><row><aae044>��λ����</aae044><aae140>�������ͣ��������</aae140><aac008>�α�״̬���������</aac008><aac030>���βα�ʱ��</aac030><aac049>�״βα�ʱ��</aac049></row><row><aae044>��λ����</aae044><aae140>�������ͣ��������</aae140><aac008>�α�״̬���������</aac008><aac030>���βα�ʱ��</aac030><aac049>�״βα�ʱ��</aac049></row></aae140ows></responsedata></body></response>";
			//���� xmlת����java���� */
			Response response=jaxb.fromXml(responsexml);
			log.info("server responsexml->"+responsexml);
			//��¼��־
			jkLogUtil.updateSuccessLog(jkid,responsexml);
			return response;
		}catch(Exception e){
			//������÷����쳣
			e.printStackTrace();
			Head head=new Head();
			//��������־
			try{
				jkLogUtil.updateErrorLog(jkid,e.getMessage());
			}catch(AppException ex){
				Response response=new Response();
				head.setRc("2");
				head.setRm(ex.getMessage());
				response.setHead(head);
			    return response;
			}
			Response response=new Response();
		    head.setRc("2");
			head.setRm(e.getMessage());
			response.setHead(head);
		    return response;
		}
	}
}
