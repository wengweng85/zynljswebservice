package com.insigma.webservice.xml;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Collection;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlAnyElement;
import javax.xml.namespace.QName;

import org.apache.commons.lang.StringUtils;


public class JaxbUtil {
	
	private static JaxbUtil jaxbutil;
	
	// ���̰߳�ȫ��Context.  
    private static  JAXBContext jaxbContext;  
	
	public JaxbUtil(Class<?>... types){
		 try {  
	            jaxbContext = JAXBContext.newInstance(types);  
	        } catch (JAXBException e) {  
	            throw new RuntimeException(e);  
	        }  
	}
	
	public static JaxbUtil getInstance(Class<?>... types){
		if(jaxbutil==null){
			jaxbutil=new JaxbUtil();
			  try {  
		            jaxbContext = JAXBContext.newInstance(types);  
		        } catch (JAXBException e) {  
		            throw new RuntimeException(e);  
		        }  
		}
		return jaxbutil;
	}

  
    /** 
     * Java Object->Xml. 
     */  
    public String toXml(Object root, String encoding) {  
        try {  
            StringWriter writer = new StringWriter(); 
            //����Լ���Ҫ��xml����ͷ
            writer.write("<?xml version=\'1.0\' encoding=\'" + encoding + "\'?>\n");
            Marshaller marshaller= createMarshaller(encoding);
            marshaller.marshal(root, writer);  
            return writer.toString();  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * Java Object->Xml, �ر�֧�ֶ�Root Element��Collection������. 
     */  
    @SuppressWarnings("unchecked")  
    public String toXml(Collection root, String rootName, String encoding) {  
        try {  
            CollectionWrapper wrapper = new CollectionWrapper();  
            wrapper.collection = root;  
  
            JAXBElement<CollectionWrapper> wrapperElement = new JAXBElement<CollectionWrapper>(new QName(rootName), CollectionWrapper.class, wrapper);  
  
            StringWriter writer = new StringWriter();  
            createMarshaller(encoding).marshal(wrapperElement, writer);  
  
            return writer.toString();  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * Xml->Java Object. 
     */  
    @SuppressWarnings("unchecked")  
    public <T> T fromXml(String xml) {  
        try {  
        	if(!xml.equals("")){
        		 StringReader reader = new StringReader(xml);  
                 return (T) createUnmarshaller().unmarshal(reader);  
        	}else{
        		return null;
        	}
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * Xml->Java Object, ֧�ִ�Сд���л�����. 
     */  
    @SuppressWarnings("unchecked")  
    public <T> T fromXml(String xml, boolean caseSensitive) {  
        try {  
            String fromXml = xml;  
            if (!caseSensitive)  
               fromXml = xml.toLowerCase();  
            StringReader reader = new StringReader(fromXml);  
            return (T) createUnmarshaller().unmarshal(reader);  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * ����Marshaller, �趨encoding(��ΪNull). 
     */  
    public Marshaller createMarshaller(String encoding) {  
        try {  
            Marshaller marshaller = jaxbContext.createMarshaller();  
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);  
            //ȥ������xml��Ĭ�ϱ���ͷ��
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);
			//ת�����е������ַ�������xmlʵ���ַ�&lt;��&gt;��xmlʵ���ַ��ںöദ��xml
			//�Ŀ�����Ǵ����˵ģ��������л���
  
            if (StringUtils.isEmpty(encoding)) {  
                marshaller.setProperty(Marshaller.JAXB_ENCODING, encoding);  
            }  
            return marshaller;  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * ����UnMarshaller. 
     */  
    public Unmarshaller createUnmarshaller() {  
        try {  
            return jaxbContext.createUnmarshaller();  
        } catch (JAXBException e) {  
            throw new RuntimeException(e);  
        }  
    }  
  
    /** 
     * ��װRoot Element �� Collection�����. 
     */  
    public static class CollectionWrapper {  
        @SuppressWarnings("unchecked")  
        @XmlAnyElement  
        protected Collection collection;  
    }  

}
