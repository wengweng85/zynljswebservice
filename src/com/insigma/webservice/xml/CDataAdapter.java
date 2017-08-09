package com.insigma.webservice.xml;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class CDataAdapter extends XmlAdapter<String, String> {
	//��javabean��xml�����䷽��
    @Override
    public String marshal(String str) throws Exception {
        return "<![CDATA[" + str+ "]]>";
    }
   
    //��xml��javabean�����䷽��
    @Override
    public String unmarshal(String str) throws Exception {
        return str;
    }
}
