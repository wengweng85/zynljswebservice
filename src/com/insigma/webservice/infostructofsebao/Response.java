package com.insigma.webservice.infostructofsebao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * ���Ľṹ��֮���ؽӿڣ������籣��ѯ�ӿ�
 * @author wengsh
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Response implements java.io.Serializable {
	//��Ϣͷ
	private Head head;
	//��Ϣ��
	private Body body;

	public Body getBody() {
		return body;
	}

	public void setBody(Body body) {
		this.body = body;
	}

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}
	
	
}
