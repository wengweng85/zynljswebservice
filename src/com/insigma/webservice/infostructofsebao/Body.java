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
public class Body  implements java.io.Serializable {
	//������Ϣ��
	private Responsedata responsedata;

	public Responsedata getResponsedata() {
		return responsedata;
	}

	public void setResponsedata(Responsedata responsedata) {
		this.responsedata = responsedata;
	}
	
	
}
