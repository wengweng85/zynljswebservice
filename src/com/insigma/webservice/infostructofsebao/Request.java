package com.insigma.webservice.infostructofsebao;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * xmlת����,�����籣��ѯ�ӿ�
 * @author wengsh
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Request implements java.io.Serializable {
	//����ͷ
	private Head head;

	public Head getHead() {
		return head;
	}

	public void setHead(Head head) {
		this.head = head;
	}
	
}
