package com.insigma.webservice.xml;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;
/**
 * 
 * @author qiand
 * ͨ��ע��ת��Data �� DataTime
 * 
 */
public class JaxbDateTimeAdapter extends XmlAdapter<String, Date> {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	
	@Override
	public String marshal(Date date) throws Exception {
		return sdf.format(date);
	}

	@Override
	public Date unmarshal(String dateStr) throws Exception {
		return sdf.parse(dateStr);
	}

}
