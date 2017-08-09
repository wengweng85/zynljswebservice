package com.insigma.webservice.log;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.insigma.db.HBFactory;
import com.insigma.webservice.exception.AppException;
import com.insigma.webservice.infostruct.Message;
import com.insigma.webservice.infostruct.enums.TradeCode;


/**
 * �ӿڹ�����
 * @author wengsh
 *
 */
public class JkLogUtil {
	
	
	private HBFactory hbfactory;
	
	
	/**
	 * �����ӿ���ˮ��,��������ҵ��ȶ�
	 * ����Ϊ����ϵͳ���+��ǰ����+ÿ��8λ��ˮ��
	 * 
	 * @return
	 */
	public  String createJkKey(String syscode) throws AppException {
		String sqnum = "00000001";
		String ymd=new SimpleDateFormat("yyyyMMdd").format(new Date());
		Connection conn =hbfactory.getConnection();
	    CallableStatement proc=null;
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.GET_KEY(?,?) }");
			proc.setString(1, ymd);// �ӿ�����
			proc.registerOutParameter(2, Types.VARCHAR);
			proc.execute();
			sqnum = proc.getString(2);
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
		return syscode+ymd+sqnum;
		
		/*String ymd=new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())+RandomNumUtil.getRandomNum(4);
		return SysType.PXJD.getCode()+ymd;*/
	}

	/**
	 * ������־
	 * 
	 * @param dto
	 * @param sess
	 * @return
	 */
	private  String addLog(JkLogDTO dto)  throws AppException {
		Connection conn =hbfactory.getConnection();
		CallableStatement proc = null;
		String id = "";
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.ADD_LOG(?,?,?,?,?,?) }");
			proc.setString(1, dto.getJkType());// �ӿ�����
			proc.setString(2, dto.getJkSender());// �ӿڵ��÷�
			proc.setString(3, dto.getJkReciver());// �ӿڵ��÷�
			proc.setString(4, dto.getJkkey());// Ψһʶ����ˮ��
			proc.setString(5, dto.getJkSendXml());//�����xml
			proc.setString(6, id);
			proc.registerOutParameter(6, Types.VARCHAR);
			proc.execute();
			id = proc.getString(6);
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
		return id;
	}

	public HBFactory getHbfactory() {
		return hbfactory;
	}

	public void setHbfactory(HBFactory hbfactory) {
		this.hbfactory = hbfactory;
	}

	/**
	 * �޸���־
	 * 
	 * @param dto
	 * @param sess
	 * @return
	 */
	private  void updateLog(JkLogDTO dto) throws AppException {
		Connection conn =hbfactory.getConnection();
		CallableStatement proc = null;
		try {
			proc = conn.prepareCall("{ call pkg_jk_client.UPDATE_LOG(?,?,?,?,?) }");
			proc.setString(1, dto.getJkId());
			proc.setString(2, dto.getJkResult());
			proc.setString(3, dto.getJkReturnXml());
			proc.setString(4, dto.getJkReturnCode());
			proc.setString(5, dto.getJkReturnMsg());
			proc.execute();
			conn.commit();
			if(proc!=null){
				proc.close();
			}
			if(conn!=null){
				conn.close();
			}
		} catch (Exception e) {
			try {
				conn.rollback();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			e.printStackTrace();
		} 
	}
	
	/**
	 * ��������־
	 * @param head ��Ϣͷ��Ϣ
	 * @param send_xml xml��
	 * @return
	 * @throws AppException
	 */
	public  String addLog(Message message,String requestxml) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkSendXml(requestxml);//send xml
		jklogdto.setJkType(message.getMSGCODE());// �ӿ�����
		jklogdto.setJkSender(message.getSNDCODE()); // ���ͷ�
		jklogdto.setJkReciver(message.getRCVCODE()); // ���ͷ�
		jklogdto.setJkkey(message.getSNDMSGID());//��ˮ��
		String jkid = addLog(jklogdto);
		return jkid;
		
	}
	
	/**
	 * ��������־
	 * @param head ��Ϣͷ��Ϣ
	 * @param send_xml xml��
	 * @return
	 * @throws AppException
	 */
	public  String addLog(String requestxml) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkSendXml(requestxml);//send xml
		String jkid = addLog(jklogdto);
		return jkid;
		
	}
	
	
	/**
	 * ��������־ �ɹ� 
	 * @param jkId ����־��ˮ�� 
	 * @param jk_return_xml ���ص�xml
	 */
	public  void updateSuccessLog(Message message,String jkId,String jk_return_xml ) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkId(jkId);
		jklogdto.setJkResult("1");// ʧ��
		jklogdto.setJkReturnXml(jk_return_xml);//����xml��
		jklogdto.setJkReturnCode(message.getSTATUS());//������
		jklogdto.setJkReturnMsg(message.getERRMSG());//������˵��
	    updateLog(jklogdto);	
	}
	
	/**
	 * ��������־ �ɹ� 
	 * @param jkId ����־��ˮ�� 
	 * @param jk_return_xml ���ص�xml
	 */
	public  void updateSuccessLog(String jkId,String jk_return_xml ) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkId(jkId);
		jklogdto.setJkResult("1");// ʧ��
		jklogdto.setJkReturnXml(jk_return_xml);//����xml��
	    updateLog(jklogdto);	
	}
	
	/**
	 * ��������־ ����
	 * @param jkId ����־��ˮ�� 
	 * @param jk_return_xml ���ص�xml
	 * @param return_code ҵ����
	 * @param return_msg ҵ����˵��
	 */
	public  void updateErrorLog(String jkId,String return_msg) throws AppException{
		JkLogDTO jklogdto=new JkLogDTO();
		jklogdto.setJkId(jkId);
		jklogdto.setJkResult("0");// ʧ��
		jklogdto.setJkReturnXml("");//����xml��
		jklogdto.setJkReturnCode(TradeCode.SUCCESS.getCode());//������
		jklogdto.setJkReturnMsg(return_msg);//������˵��
	    updateLog(jklogdto);	
	}

}
