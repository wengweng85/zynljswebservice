package com.insigma.webservice.infostructofsebao;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


/**
 * xmlת����,�����籣��ѯ�ӿ�
 * @author wengsh
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Responsedata implements java.io.Serializable {
	
	//������Ϣ
	private Basicform basicform;
	
	//�α���Ϣ
	@XmlElementWrapper(name="aae140ows")
	@XmlElement(name="row")
	private List<SeRow> aae140ows;
    
	//�ɷ���Ϣ
	@XmlElementWrapper(name="feeinfogrid")
	@XmlElement(name="row")
    private List<SeRow>  feeinfogrid;

	public Basicform getBasicform() {
		return basicform;
	}

	public void setBasicform(Basicform basicform) {
		this.basicform = basicform;
	}

	public List<SeRow> getAae140ows() {
		return aae140ows;
	}

	public void setAae140ows(List<SeRow> aae140ows) {
		this.aae140ows = aae140ows;
	}

	public List<SeRow> getFeeinfogrid() {
		return feeinfogrid;
	}

	public void setFeeinfogrid(List<SeRow> feeinfogrid) {
		this.feeinfogrid = feeinfogrid;
	}
}
