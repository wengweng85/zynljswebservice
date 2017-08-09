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
 * 调用服务
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
	 *查询变更过的组织节点机构信息 211001
	 *
	 *
	目标系统通过此交易可以查询门户系统中做过变更的组织节点机构信息，包括但不限于新增组织节点机构、变更组织节点机构信息、删除组织节点机构等，按照变更类型、变更时间排序返回。
	请求报文业务体记录数为0条，响应报文业务体记录数都为多条。
	(一)	变更类型为“新增”时，目标系统节点ID为空。
	(二)	变更类型为“新增”时，目标系统需再调用交易5.1.3机构组织节点信息映射绑定211002来实现门户系统和目标系统的组织节点机构信息映射。
	(三)	目标业务系统通过此交易查询的变更过的组织机构机构机构信息，则认为已经被目标业务系统同步过，其记录不会出现在下次查询结果中。
	(四)	响应报文中父机构ID与机构ID相同，表示是根机构，即杭州市人力资源和社会保障局。
	(五)	响应报文中父机构ID和父机构映射的目标系统机构ID都是当前调用时的实时数据的，各目标系统在处理时每次都要判断其值与各自系统中的父机构ID是否一致，
	不一致时表示父机构有修改。
	 *
	 *
	 * @return
	 * @throws AppException
	 */
	public void jk_211001(int pageno) throws AppException{
		
		//第一步 通过接口将所有的数据拉取过来保存到我们的机构数据库
		log.info("当前调用接口为 查询变更过的组织节点机构信息，当前页面码为 "+pageno);
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211001.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(pageno);//页码
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			List<Data> list=returnmsg.getDATALIST();
			for(Data data:list){
				String cgtype=data.getCGTYPE();//1-新增，2-修改，3-删除
				String cgtime=data.getCGTIME();//变更时间
				//新增用户
				String ndid=data.getNDID();//节点机构ID
				String dndid=data.getDNDID() ;//目标系统用户id
				String pndid=data.getPNDID() ;//父节点机构ID
				String dpndid=data.getDPNDID() ;//父节点机构映射的目标系统节点机构父节点ID
				String size=data.getSIZE() ;//父节点机构映射的目标系统节点机构父节点ID
				String ndname="";//节点机构名称	
				String ndabbr="";//节点机构简称
				String ndlead="";//节点机构负责人	
				String ndaddr="";//节点机构所在地址	
				String contact="";//联系人	
				String phone="";//联系电话
				String priority="";//排序
				String grade="";//机构等级
				String describe="";//机构职能描述
				for(int i=0;i<data.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//NDNAME	节点机构名称	
					if(row.getCODE().equals("NDNAME")){
						ndname=row.getVALUE();
					}
					//NDABBR	节点机构简称	
					if(row.getCODE().equals("NDABBR")){
						ndabbr=row.getVALUE();
					}
					//NDLEAD	节点机构负责人
					if(row.getCODE().equals("NDLEAD")){
						ndlead=row.getVALUE();
					}
					//NDADDR	节点机构所在地址	
					if(row.getCODE().equals("NDADDR")){
						ndaddr=row.getVALUE();
					}
					//CONTACT	联系人	string	50	
					if(row.getCODE().equals("CONTACT")){
						contact=row.getVALUE();
					}
					//PHONE	联系电话	string	20	
					if(row.getCODE().equals("PHONE")){
						phone=row.getVALUE();
					}
					//PRIORITY	排序	number	8	
					if(row.getCODE().equals("PRIORITY")){
						priority=row.getVALUE();
					}
					//GRADE	机构等级	string	10	
					if(row.getCODE().equals("GRADE")){
						grade=row.getVALUE();
					}
					//DESCRIBE	机构职能描述	string	500	
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
			
			log.info("当前调用接口为 查询变更过的组织节点机构信息，是否是最后一页: "+returnmsg.getEOPSIGN()); 
			//判断是否是最后一页,如果不是最后一页，再次拉取数据
			if(returnmsg.getEOPSIGN().equals("0")){
				jk_211001(pageno++);
			}
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		
		
		//第二步
		//遍历所有的请求过的数据，根据操作类型处理数据并更新处理结果
		
		String sql="SELECT t.uuid, t.cgtype,t.ndid   from sso_group t where t.isop='0'   order by t.cgtype,t.cgtime ";
		try{
			Connection conn =hbfactory.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cgtype=rs.getString("cgtype");//操作类型
				String uuid=rs.getString("uuid");
				String ndid=rs.getString("ndid");//节点机构ID
				//String dndid=rs.getString("dndid");//目标系统节点机构ID
				//过程中有不抛出
				try{
					CallableStatement proc=null;
				    proc = conn.prepareCall("{ call PKG_SSO_PROCESS.ssoGroupUpdate(?,?) }");
					proc.setString(1, uuid);// uuid
					proc.registerOutParameter(2, Types.VARCHAR);
					proc.execute();
					conn.commit();
					//新生成的groupid
					String groupid = proc.getString(2);
					
					if(proc!=null){
						proc.close();
					}
					//如果是新增
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
	 *组织节点机构机构信息映射绑定 211002
	 *
	 *
	 * 目标系统可以通过此交易来实现门户系统和目标系统的组织节点机构信息映射。
     * 请求报文业务体记录数为多条，响应报文业务体记录数为多条。响应报文业务体仅返回映射绑定出错的记录及错误信息。
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
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(1);
		List <Data> datalist=new ArrayList<Data>();
		Data data=new Data();
		data.setNDID(ndid); //节点机构ID
		data.setDNDID(dndid);;//目标系统节点机构ID
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg=call(message);
		
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			log.info("成功");
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		return returnmsg;
	}

	
	
	/**
	 *  5.1.4	查询变更过的用户信息211021
	 * 目标系统通过此交易可以查询门户系统中做过变更的用户信息，包括但不限于新增用户、变更用户信息、删除用户信息等，按照变更类型、变更时间排序返回。
	 *	请求报文业务体记录数位0条，响应报文业务体记录数都为多条。
	 *	(一)	变更类型为“新增”时，目标系统用户ID为空。
	 *	(二)	变更类型为“新增”时，目标系统需再调用交易5.1.5用户信息映射绑定211022来实现门户系统和目标系统的用户信息映射绑定。
	 *	(三)	目标业务系统通过此交易查询的变更过的用户信息，则认为已经被目标业务系统同步过，其记录不会出现在下次查询结果中。
     *
	 *
	 *
	 * @return
	 * @throws AppException
	 */
	public void jk_211021(int pageno) throws AppException{
		//第一步 通过接口将所有的数据拉取过来保存到我们的机构数据库
		log.info("当前调用接口为 查询变更过的人员信息信息，当前页面码为 "+pageno);
		Message message=new Message();
		message.setSNDCODE(SysType.PXJD.getCode());
		message.setRCVCODE(SysType.MHXT.getCode());
		message.setMSGCODE(BusCode.JK_211021.getCode());
		message.setSNDMSGID(jkLogUtil.createJkKey(SysType.PXJD.getCode()));
		message.setUSERCODE("admin");
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) );
		message.setPAGENO(pageno);//页码
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			List<Data> list=returnmsg.getDATALIST();
			for(Data data:list){
				String cgtype=data.getCGTYPE();//1-新增，2-修改，3-删除
				String cgtime=data.getCGTIME();//变更时间
				//新增用户
				String userid=data.getUSERID ();//用户ID
				String duserid=data.getDUSERID() ;//目标系统用户id
				String size=data.getSIZE() ;//父节点机构映射的目标系统节点机构父节点ID
				String LGNAME	="" ;//登录名	string	20	
				String NAME=""	 ;//姓名	string	50	
				String GENDER	="";//性别	string	1	
				String IDNO="" ;//	身份证号码	string	20	
				String MPHONE="";//	移动电话	string	20	
				String VPHONE=""	;//移动电话（虚拟号）	string	20	
				String OPHONE="";//	办公电话	string	20	
				String DUTIES=""	;//职务	string	500	
				String LEVEL=""	;//职级	string	500	
				String POST=""	;//岗位	string	500	
				String EMAIL="";//	电子邮箱（内网）	string	50	
				String INEMAIL="";//	电子邮箱（公网）	string	50	
				String PRIORITY="";//	用户所属组织机构机构及排名	
				
				for(int i=0;i<data.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//LGNAME	登录名
					if(row.getCODE().equals("LGNAME")){
						LGNAME=row.getVALUE();
					}
					//NAME	姓名
					if(row.getCODE().equals("NAME")){
						NAME=row.getVALUE();
					}
					//GENDER	性别
					if(row.getCODE().equals("GENDER")){
						GENDER=row.getVALUE();
					}
					//IDNO	身份证号码
					if(row.getCODE().equals("IDNO")){
						IDNO=row.getVALUE();
					}
					//MPHONE	移动电话
					if(row.getCODE().equals("MPHONE")){
						MPHONE=row.getVALUE();
					}
					//VPHONE	移动电话 （虚拟号）
					if(row.getCODE().equals("VPHONE")){
						VPHONE=row.getVALUE();
					}
					//移动电话
					if(row.getCODE().equals("OPHONE")){
						OPHONE=row.getVALUE();
					}
					//办公电话
					if(row.getCODE().equals("DUTIES")){
						DUTIES=row.getVALUE();
					}
					//职级
					if(row.getCODE().equals("LEVEL")){
						LEVEL=row.getVALUE();
					}
					//岗位
					if(row.getCODE().equals("POST")){
						POST=row.getVALUE();
					}
					//电子邮箱（内网）
					if(row.getCODE().equals("EMAIL")){
						EMAIL=row.getVALUE();
					}
					//INEMAIL	 电子邮箱
					if(row.getCODE().equals("INEMAIL")){
						INEMAIL=row.getVALUE();
					}
					//PRIORITY	用户所属组织机构机构及排名	
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
			
			log.info("当前调用接口为 查询变更过的人员信息，是否是最后一页: "+returnmsg.getEOPSIGN()); 
			//判断是否是最后一页,如果不是最后一页，再次拉取数据
			if(returnmsg.getEOPSIGN().equals("0")){
				jk_211001(pageno++);
			}
		}
		
		//第二步
		//遍历所有的请求过的数据，根据操作类型处理数据并更新处理结果
		String sql="SELECT t.uuid, t.userid, t.cgtype,t.cgtime  from sso_user t where t.isop='0' order by t.cgtype,t.cgtime ";
		try{
			Connection conn =hbfactory.getConnection();
			Statement stmt=conn.createStatement();
			ResultSet rs=stmt.executeQuery(sql);
			while(rs.next()){
				String cgtype=rs.getString("cgtype");//操作类型
				String uuid=rs.getString("uuid");
				String userid=rs.getString("userid");//节点机构ID
			    CallableStatement proc=null;
			    proc = conn.prepareCall("{ call PKG_SSO_PROCESS.ssoUserUpdate(?,?) }");
			    log.info(uuid);
				proc.setString(1, uuid);// uuid
				proc.registerOutParameter(2, Types.VARCHAR);
				proc.execute();
				conn.commit();
				//新生成的groupid
				String pxjduserid = proc.getString(2);
				if(proc!=null){
					proc.close();
				}
				//如果是新增
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
	 *  新增用户信息211022
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
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date()) ); //20160809 10:28:25
		List <Data> datalist=new ArrayList<Data>();
		Data data=new Data();
		data.setUSERID(userid);//用户id
		data.setDUSERID(duserid);//目标系统id
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg=call(message);
		String status=returnmsg.getSTATUS();
		if(status.equals("0000")){
			log.info("成功");
		}else{
			String errmsg=returnmsg.getERRMSG();
			log.info(status+":"+errmsg);
		}
		return returnmsg;
	}
	
	
	/**
	 * 查询个人信息212003
	 * 
	 * 目标系统通过此交易可以查询指定目标系统个人ID或姓名或身份证件号码对应的个人信息。
	 * 如果请求报文业务体中的目标系统个人ID不为空，则按照目标系统个人ID精确查找其映射的个人基本信息，没有找到则提示错误信息。
	 * 如果请求报文业务体中的目标系统个人ID为空，则按照姓名和身份证号来查找个人基本信息，姓名和身份证号必须有一个有值。
	 * 此交易可能返回多条符合条件的个人信息
	 * ，需目标系统自行判断选择哪条个人信息；如果响应报文中某条个人信息中的目标系统个人ID为空，则表示目标系统还未与该个人信息进行个人信息映射绑定
	 * ，目标系统可以调用交易5.2.2修改个人信息212002来实现个人信息映射绑定。 请求报文业务体记录数为1条，响应报文业务体记录数为多条。
	 * 
	 * @param BAC010
	 *            目标系统个人ID
	 * @param AAC002
	 *            证件号码
	 * @param AAC003
	 *            姓名
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
			message.setUSERNAME("管理员");
			message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
			JkDTO dto = new JkDTO();
			
			//目标系统个人id
			Data data=message.getDATALIST().get(0);
			dto.setBac010(data.getBAC010());
			dto.setAac002(data.getAAC002());
			dto.setAac003(data.getAAC003());
			dto.setAac004(data.getAAC004());
			
			//清除目标系统编号及姓名查询字段，只用身份证号码查询
			message.getDATALIST().get(0) .setBAC010("");
			message.getDATALIST().get(0) .setAAC003("");
			
			//调用统一用户中心接口
			returnmsg= call(message);
			String status = returnmsg.getSTATUS();
			// 调用接口成功
			if (status.equals("0000")) {
				List<Data> datareturnlist = returnmsg.getDATALIST();
				if(datareturnlist.size()>0){
					for (Data returndata : datareturnlist) {
						//返回的统一门户个人编号
						String return_data_aac001 = returndata.getAAC001();
						String return_data_bac010 = returndata.getBAC010();
						dto.setAac001(return_data_aac001);
						
						log.info("在统一用户中心存在,用户id为"+return_data_aac001);
						
						// 将同步过来的个人信息写入到sso_ac01表
						CallableStatement proc = null;
						Connection conn = hbfactory.getConnection();
						proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoAc01(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) }");
						proc.setString(1, return_data_aac001);// 个人ID
						proc.setString(2, return_data_bac010);// 目标系统个人ID
						proc.setString(3, returndata.getAAC003());// 姓名
						proc.setString(4, returndata.getAAC002());// 社会保障号(公民身份号码)
						proc.setDate(5, returndata.getAAC006()==null?null:new java.sql.Date(returndata.getAAC006().getTime()));// 出生日期
						proc.setString(6, returndata.getAAC004());// 性别
						proc.setString(7, returndata.getAAC005());// 民族
						proc.setString(8, returndata.getBAC001());// 籍贯
						proc.setString(9, returndata.getAAC161());// 国籍
						proc.setString(10, returndata.getAAC009());// 户籍性质
						proc.setString(11, returndata.getAAC700());// 户口簿号码
						proc.setString(12, returndata.getBAC004());// 户主姓名
						proc.setString(13, returndata.getAAC069());// 与户主关系
						proc.setString(14, returndata.getBAC006());// 家庭成员人数
						proc.setString(15, returndata.getAAC010());// 户籍所在地
						proc.setString(16, returndata.getBAC007());// 户籍所在区
						proc.setString(17, returndata.getBAC008());// 户籍所在街道
						proc.setString(18, returndata.getBAC009());// 户籍所在社区
						proc.setString(19, returndata.getAAC017());// 婚姻状况
						proc.setString(20, returndata.getBAC003());// 政治面貌
						proc.setString(21, returndata.getAAC153());// 是否单亲
						proc.setString(22, returndata.getAAC154());// 是否独居
						proc.setString(23, returndata.getAAC086());// 是否孤寡或孤儿
						proc.setString(24, returndata.getAAC011());// 学历
						proc.setString(25, returndata.getAAC155());// 技术职称
						proc.setString(26, returndata.getAAC156());// 是否城镇三无人员
						proc.setString(27, returndata.getAAE008());// 现居住地址
						proc.setString(28, returndata.getAAE009());// 现居住地址邮编
						proc.setString(29, returndata.getAAE004());// 联系电话
						proc.setString(30, returndata.getAAE159());// 个人电子邮箱
						proc.execute();
						conn.commit();
						if (proc != null) {
							proc.close();
						}
						if (conn != null) {
							conn.close();
						}
					
						// 如果返回的目标系统的用户id空，调用修改接口绑定个人基本信息
						if (return_data_bac010.equals("")) {
							log.info("在统一用户中心存在这个人,但目标用户id为空，调用人员修改接口增加更新目标用户id");
							// 修改
							conn = hbfactory.getConnection();
							proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synSsoAc01Update(?,? }");
							proc.setString(1, dto.getAac001());// 个人ID
							proc.setString(2, dto.getBac010());// 目标系统个人ID
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
							//如果两者名称一致
							if(return_data_aac003.equals(dto.getAac003())){
								log.info("在统一用户中心存在这个人,且培训鉴定库中的个人姓名一致");
							}else{
								log.info("在统一用户中心存在这个人,但与培训鉴定库中的个人姓名不一致,培训鉴定库中的个人姓名为:"+dto.getAac003()+";统一用户中心中的姓名为:"+return_data_aac003+",需要更新培训鉴定库中的个人姓名");
								// 修改姓名
								conn = hbfactory.getConnection();
								proc = conn.prepareCall("{ call PKG_SSO_PROCESS.synssoac01_aac003_update(?,?,?)}");
								proc.setString(1, dto.getAac001());// 个人ID
								proc.setString(2, dto.getBac010());// 目标系统个人ID
								proc.setString(3, return_data_aac003);// 目标系统个人ID
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
				// 如果查询不到个人基本信息，调用新增接口
				else{
					log.info("在统一用户中心不存在这个人,调用新增接口增加这个人的信息");
					// 新增
					return jk_212001(dto);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return returnmsg;
	}
	
	/**
	 * 查询个人信息212003
	 * 
	 * 目标系统通过此交易可以查询指定目标系统个人ID或姓名或身份证件号码对应的个人信息。
	 * 如果请求报文业务体中的目标系统个人ID不为空，则按照目标系统个人ID精确查找其映射的个人基本信息，没有找到则提示错误信息。
	 * 如果请求报文业务体中的目标系统个人ID为空，则按照姓名和身份证号来查找个人基本信息，姓名和身份证号必须有一个有值。
	 * 此交易可能返回多条符合条件的个人信息
	 * ，需目标系统自行判断选择哪条个人信息；如果响应报文中某条个人信息中的目标系统个人ID为空，则表示目标系统还未与该个人信息进行个人信息映射绑定
	 * ，目标系统可以调用交易5.2.2修改个人信息212002来实现个人信息映射绑定。 请求报文业务体记录数为1条，响应报文业务体记录数为多条。
	 * 
	 * @param BAC010  目标系统个人ID
	 * @param AAC002 证件号码
	 * @param AAC003 姓名
	 * @return
	 * @throws AppException
	 */
	private void jk_212003(JkDTO jkac01dto) throws AppException {
		Message message = new Message();
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		// 如果目标系统个人ID不为空
		//data.setBAC010(jkac01dto.getBac010());//目标系统个人ID
		data.setAAC002(jkac01dto.getAac002());// 证件号码
		data.setAAC003(jkac01dto.getAac003());// 姓名
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = jk_212003(message);
		String status = returnmsg.getSTATUS();
		log.info(status);
			
	}

	/**
	 * 新增人员个人信息212001
	 * 
	 * 目标系统先通过证件号码查询个人信息，如果没有查询到个人信息，则通过此交易新增个人信息；否则无需调用此交易，若要变更个人信息，请调用交易5.2.2
	 * 修改个人信息212002。 调用此交易时，门户系统在保存个人信息时，同时进行个人信息映射绑定。
	 * 请求报文业务体记录数为1条，响应报文业务体记录数都为1条。
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
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setBAC010(dto.getBac010());// 目标系统个人ID
		data.setAAC003(dto.getAac003());// 姓名
		data.setAAC002(dto.getAac002());// 社会保障号(公民身份号码)
		data.setAAC004(codeConvertUtil.getRometeCodeByLocalCode("AAC004",dto.getAac004()));// 性别
		//data.setAAC006(dto.getAac006());// 出生日期
		data.setAAC005("01");// 民族 默认汉族
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			log.info("新增用户成功");
			//再次调用查询接口
			log.info("再次调用查询接口");
			jk_212003(dto);
		} else {
			String errmsg = returnmsg.getERRMSG();
			log.info(status + ":" + errmsg);
		}
		return returnmsg;
	}

	/**
	 * 修改个人信息212002
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
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setAAC001(dto.getAac001());// 门户系统个人编号
		data.setBAC010(dto.getBac010());// 目标系统个人编号(我们的系统)
		data.setSIZE("1");
		// 修改的明细项目
		List<Row> rowlist = new ArrayList<Row>();
		data.setROWS(rowlist);
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			log.info("修改绑定关系成功");
		} else {
			String errmsg = returnmsg.getERRMSG();
			log.info(status + ":" + errmsg);
		}
		return returnmsg;
	}
	
	
	
	/**
	 * 查询修改过的个人信息
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
		message.setUSERNAME("管理员");
		message.setSNDTIME(DateUtil.dateToNormalStringNew(new Date())); // 20160809
		message.setPAGENO(pageno);//页码																
		List<Data> datalist = new ArrayList<Data>();
		Data data = new Data();
		data.setSTARTDATE(new Date());
		data.setENDDATE(new Date());
		data.setSIZE("1");
		// 修改的明细项目
		List<Row> rowlist = new ArrayList<Row>();
		data.setROWS(rowlist);
		datalist.add(data);
		message.setDATALIST(datalist);
		message.setDATASIZE(1);
		Message returnmsg = call(message);
		String status = returnmsg.getSTATUS();
		if (status.equals("0000")) {
			for(Data returndata:returnmsg.getDATALIST()){
				String aae302=data.getNDID();// 发起变更的系统代码
				String aae036=data.getDNDID() ;//变更日期
				String aac001=data.getPNDID() ;//人员ID
				String bac010=data.getDPNDID() ;//目标系统人员ID
				String size=data.getSIZE() ;//size
				String aac003="";
				
				log.info("发起变更的系统代码:"+aae302+";aae036"+aae036+";size:"+size+";bac010:"+bac010+"aac001:"+aac001);
				for(int i=0;i<returndata.getROWS().size();i++){
					Row row=data.getROWS().get(i);
					log.info(row.getCODE()+":"+row.getVALUE());
					//AAC003	姓名修改
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
				
				log.info("查询修改过的个人信息，是否是最后一页: "+returnmsg.getEOPSIGN()); 
				//判断是否是最后一页,如果不是最后一页，再次拉取数据
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
	 *个人参保信息查询（22KOW6）
	 *
	 * @return
	 * @throws AppException
	 */
	public Response jk_22KOW6(Request request) {
		Response response=call(request,BusCode.JK_22KOW6);
		return response;
		
	 }
	
	
	/**
	 *社会保险缴费信息查询（Q1BQ7U）
	 *
	 * @return
	 * @throws AppException
	 */
	public Response jk_Q1BQ7U(Request request){
		Response response=call(request,BusCode.JK_Q1BQ7U);
		return response;
	 }
	

	/**
	 * webservice服务
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private  Message call(Message message) {
	  JaxbUtil jaxb =null;
	  String jkid="";
		try{
			//java对象转换为XML字符串 
	        jaxb = new JaxbUtil(Message.class);  
	        //请求的xml */
	        String requestxml=jaxb.toXml(message,"UTF-8");
	        //记录日志*/
			jkid = jkLogUtil.addLog(message,requestxml);
			log.info("server requestxml->"+requestxml);
	        //调用webservice服务 
			/** 调用webservice服务并返回xml */
			Service service = new Service();
			Call call = (Call) service.createCall();
			//测试webservice地址
			//call.setTargetEndpointAddress("http://172.16.69.155:9002/hzrssjjh/services/DataExchangeServer?wsdl");
			//正式webservice地址
			call.setTargetEndpointAddress("http://172.16.29.49:7001/hzrssjjh/services/DataExchangeServer?wsdl");
			call.setOperationName(new QName("http://local.hzrssjjh.wondersgroup.com", "execute"));
			call.addParameter("requestData", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String responsexml = (String) call.invoke(new Object[] {requestxml});
			//返回 xml转换成java对象 */
			Message returnmsg=jaxb.fromXml(responsexml);
			log.info("server responsexml->"+responsexml);
			//记录日志
			jkLogUtil.updateSuccessLog(returnmsg,jkid,responsexml);
			return returnmsg;
		}catch(Exception e){
			//程序调用发生异常
			e.printStackTrace();
			//更新主日志
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
	 * webservice服务
	 * @param message
	 * @return
	 * @throws Exception
	 */
	private  Response call(Request request,BusCode tradeCode) {
	  JaxbUtil jaxb =null;
	  String jkid="";
		try{
			//java对象转换为XML字符串 
	        jaxb = new JaxbUtil(Request.class,Response.class);  
	        //请求的xml */
	        String requestxml=jaxb.toXml(request,"UTF-8");
	        //记录日志*/
			jkid = jkLogUtil.addLog(requestxml);
			log.info("server requestxml->"+requestxml);
	        //调用webservice服务 
			/** 调用webservice服务并返回xml */
			Service service = new Service();
			Call call = (Call) service.createCall();
			//测试webservice地址
			//call.setTargetEndpointAddress("http:// 172.16.29.59:9002/nws/services/NwsServer");
			//正式webservice地址
			call.setTargetEndpointAddress("http://172.16.69.150:9001/nws/services/NwsServer");
			call.setOperationName(new QName("http://local.hzrssjjh.wondersgroup.com", "nwsReceive"));
			call.addParameter("systemCode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("tradeCode", XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter("xml", XMLType.XSD_STRING, ParameterMode.IN);
			call.setReturnType(XMLType.XSD_STRING);
			String responsexml = (String) call.invoke(new Object[] {SystemCode.PXJD.getCode(),tradeCode.getCode(),requestxml});
			
			//String responsexml="<?xml version=\"1.0\" encoding=\"utf-8\"?><response><head> <rc>响应编号（见代码表）</rc><rm>异常信息</rm></head><body><responsedata><basicform><aac003>姓名</aac003> <aac002>身份证号</aac002> <aac999>个人编号</aac999> <aac004>性别（见代码表）</aac004> <aac005>民族（见代码表）</aac005><aac006>出生日期</aac006> <aac007>参加工作日期</aac007> <aac084>离退休标识（见代码表）</aac084><akc021>医疗人员类别（见代码表）</akc021>	<aac024>政治面貌（见代码表）</aac024> <aac009>户口性质（见代码表）</aac009> <aac010>户口所在地详细地址</aac010><aac011>文化程度（见代码表）</aac011><aac013>用工形式（见代码表）</aac013></basicform><feeinfogrid><row><aae003>年月</aae003><aae140>险种类型(见代码表)</aae140><aae180>缴费基数</aae180><aae020>个人缴费金额</aae020><aab001>单位编号</aab001> <aab004>单位名称</aab004> <aaa115>缴费类型(见代码表)</aaa115></row><row><aae003>年月</aae003><aae140>险种类型(见代码表)</aae140><aae180>缴费基数</aae180><aae020>个人缴费金额</aae020><aab001>单位编号</aab001> <aab004>单位名称</aab004> <aaa115>缴费类型(见代码表)</aaa115></row><row><aae003>年月</aae003><aae140>险种类型(见代码表)</aae140><aae180>缴费基数</aae180><aae020>个人缴费金额</aae020><aab001>单位编号</aab001> <aab004>单位名称</aab004> <aaa115>缴费类型(见代码表)</aaa115></row></feeinfogrid><aae140ows><row><aae044>单位名称</aae044><aae140>险种类型（见代码表）</aae140><aac008>参保状态（见代码表）</aac008><aac030>本次参保时间</aac030><aac049>首次参保时间</aac049></row><row><aae044>单位名称</aae044><aae140>险种类型（见代码表）</aae140><aac008>参保状态（见代码表）</aac008><aac030>本次参保时间</aac030><aac049>首次参保时间</aac049></row><row><aae044>单位名称</aae044><aae140>险种类型（见代码表）</aae140><aac008>参保状态（见代码表）</aac008><aac030>本次参保时间</aac030><aac049>首次参保时间</aac049></row><row><aae044>单位名称</aae044><aae140>险种类型（见代码表）</aae140><aac008>参保状态（见代码表）</aac008><aac030>本次参保时间</aac030><aac049>首次参保时间</aac049></row></aae140ows></responsedata></body></response>";
			//返回 xml转换成java对象 */
			Response response=jaxb.fromXml(responsexml);
			log.info("server responsexml->"+responsexml);
			//记录日志
			jkLogUtil.updateSuccessLog(jkid,responsexml);
			return response;
		}catch(Exception e){
			//程序调用发生异常
			e.printStackTrace();
			Head head=new Head();
			//更新主日志
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
