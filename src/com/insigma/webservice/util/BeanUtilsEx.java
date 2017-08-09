package com.insigma.webservice.util;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.converters.BigDecimalConverter;
import org.apache.commons.beanutils.converters.DoubleConverter;
import org.apache.commons.beanutils.converters.IntegerConverter;
import org.apache.commons.beanutils.converters.LongConverter;
import org.apache.commons.beanutils.converters.ShortConverter;

/**
 * ��չapache beanutils��
 * ֧��java.sql.Date java.util.Dateת����java.lang.String
 * @author wengsh
 *
 */
public class BeanUtilsEx extends BeanUtils {

	private BeanUtilsEx() {
	}

	static {
		/** ע��sql.date��ת������������BeanUtils.copyPropertiesʱ��ԴĿ���sql���͵�ֵ����Ϊ��*/
		ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.sql.Date.class);
	//	ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlDateConverter(null), java.util.Date.class);
		/** ע��util.date��ת������������BeanUtils.copyPropertiesʱ��ԴĿ���util���͵�ֵ����Ϊ��*/
		ConvertUtils.register(new org.apache.commons.beanutils.converters.SqlTimestampConverter(null), java.sql.Timestamp.class);
		/** ע��Long,Integer,ת�����ֶ�Ϊnull�ľͲ���Ĭ��ת����0��*/
		ConvertUtils.register(new LongConverter(null), Long.class);  
	    ConvertUtils.register(new ShortConverter(null), Short.class);  
	    ConvertUtils.register(new IntegerConverter(null), Integer.class);  
	    ConvertUtils.register(new DoubleConverter(null), Double.class);  
	    ConvertUtils.register(new BigDecimalConverter(null), BigDecimal.class); 

	}

	public static void copyProperties(Object target, Object source)throws InvocationTargetException, IllegalAccessException {
		/** ֧�ֶ�����copy*/
		org.apache.commons.beanutils.BeanUtils.copyProperties(target, source);
	}

}
