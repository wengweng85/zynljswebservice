package com.insigma.webservice.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.insigma.webservice.exception.AppException;

/**
 * ʱ���ʽ��ת��
 * @author ����Ρ
 */
public final class DateUtil 
{
	/**
	 * ���ڽ��ո�ʽ
	 */
	public static final String COMPACT_DATE_FORMAT = "yyyyMMdd";

	/**
	 * ������ͨ��ʽ
	 */
	public static final String NORMAL_DATE_FORMAT = "yyyy-MM-dd";

	/**
	 * ���ڸ�ʽ ������ ʱ����
	 */
	public static final String NORMAL_DATE_FORMAT_NEW = "yyyyMMdd hh:mm:ss";

	/**
	 * ���ַ�������ת����yyyyMMdd����ʽ��strDate��ʽ����"yyyy-MM-dd"��
	 * ���ַ�������ת����yyyyMM����ʽ��strDate��ʽ����"yyyy-MM"��
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum(String strDate) throws AppException{
		if(strDate==null){
			return null;
		}
		String[] date=null;
		String newDate="";
		if(strDate.indexOf("-")>=0)
		{
		     date=strDate.split("-");
		     for(int i=0;i<date.length;i++){
		    	 newDate=newDate+date[i];
		     }
		     return Long.parseLong(newDate);
		}

		return Long.parseLong(strDate);
	}
	/**
	 * ���ַ�������ת����yyyyMMdd����ʽ��strDate��ʽΪ"yyyy-MM-dd"��"yyyy-M-d"��
	 * ���ַ�������ת����yyyyMM����ʽ��strDate��ʽ����"yyyy-M"��
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Long strDateToNum1(String strDate) throws AppException{
		if(strDate==null){
			return null;
		}
		String[] date=null;
		String newDate="";
		if(strDate.indexOf("-")>=0)
		{
		     date=strDate.split("-");
		     for(int i=0;i<date.length;i++){
		    	 if(date[i].length()== 1){
		    		 newDate=newDate+"0"+date[i];
		    	 }else{
		    		 newDate=newDate+date[i];
		    	 }
		     }
		     return Long.parseLong(newDate);
		}

		return Long.parseLong(strDate);
	}
	/**
	 * ����������ת����yyyy-MM-dd���ַ�����ʽ"��
	 * @param numDate
	 * @return
	 */
	public static String numDateToStr(Long numDate)
	{
		if(numDate==null){
			return null;
		}
		String strDate=null;
		strDate=numDate.toString().substring(0, 4)+"-"+numDate.toString().substring(4, 6)+"-"+
		numDate.toString().substring(6, 8);
		return strDate;
	}

	   /**
     * ������ת��Ϊ��ͨ���ڸ�ʽ�ַ������ַ����ĸ�ʽΪ NORMAL_DATE_FORMAT_TYPE_NEW
     * @param date ��Ҫ��ʽ��������
     * @return ��ʽ���õ����ַ�
     * @author ����ǰ
     */
    public static String dateToNormalStringNew(Date date){
    	return dateToString(date, NORMAL_DATE_FORMAT_NEW);
    }

    /**
     * ��������ַ��������ݸ����ĸ�ʽת��ΪDate����
     * @param str ��ת�����ַ���
     * @param format ָ���ĸ�ʽ
     * @return ת���������
     * @throws AppException ���ת�������׳����쳣
     * @author ����ǰ
     */
    public static Date stringToDate(String str,String format) throws AppException{
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	try {
    		if ((str==null)||(str.equalsIgnoreCase(""))){
    			return null;
    		}
			return sdf.parse(str);
		} catch (ParseException e) {
			throw new AppException("���������ַ���ʱ����");
		}
    }

    /**
     * ����������ڣ����ݸ����ĸ�ʽ����ʽ��Ϊ�ַ���
     * @param date ��Ҫת��������
     * @param format ָ���ĸ�ʽ
     * @return ��ʽ������ַ���
     * @author ����ǰ
     */
    public static String dateToString(Date date,String format){
    	SimpleDateFormat sdf=new SimpleDateFormat(format);
    	if(date==null){
    		return "";
    	}
    	return sdf.format(date);
    }

    /**
     * ���ַ���ת��Ϊ�������ͣ��ַ����ĸ�ʽΪ���ո�ʽ����ʽΪ COMPACT_DATE_FORMAT
     * @param str ��Ҫת�����ַ���
     * @return ת����õ�������
     * @throws AppException ת��ʧ�ܽ��׳����쳣
     * @author ����ǰ
     */
    public static Date compactStringToDate(String str) throws AppException{
    	return stringToDate(str, COMPACT_DATE_FORMAT);
    }

    /**
     * ���������͸�ʽ��Ϊ�ַ������ַ����ĸ�ʽΪ���ո�ʽ����ʽΪ COMPACT_DATE_FORMAT
     * @param date ��Ҫ��ʽ��������
     * @return ��ʽ���õ����ַ�
     * @author ����ǰ
     */
    public static String dateToCompactString(Date date){
    	return dateToString(date, COMPACT_DATE_FORMAT);
    }

    /**
     * ������ת��Ϊ��ͨ���ڸ�ʽ�ַ������ַ����ĸ�ʽΪ NORMAL_DATE_FORMAT
     * @param date ��Ҫ��ʽ��������
     * @return ��ʽ���õ����ַ�
     * @author ����ǰ
     */
    public static String dateToNormalString(Date date){
    	return dateToString(date, NORMAL_DATE_FORMAT);
    }

    /**
     * �����ո�ʽ�����ַ���ת��Ϊ��ͨ���ڸ�ʽ�ַ���
     * @param str ���ո�ʽ�����ַ���
     * @return ��ͨ���ڸ�ʽ�ַ���
     * @throws AppException ���ת�����ɹ����׳����쳣
     * @author ����ǰ
     */
    public static String compactStringDateToNormal(String str) throws AppException{
    	return dateToNormalString(compactStringToDate(str));
    }

    /**
     * ȡ��������֮�������
     * @param date_str ��ʼ����
     * @param date_end ��ֹ����
     * @return ���ڼ�����
     * @author ���
     */
    public static int getDaysBetween(Date date_str, Date date_end) throws AppException{
		Calendar d1 = Calendar.getInstance();
		Calendar d2 = Calendar.getInstance();
		d1.setTime(date_str);
		d2.setTime(date_end);
		if (d1.after(d2)) { // swap dates so that d1 is start and d2 is end
			throw new AppException("��ʼ����С����ֹ����!");
		}
		int days = d2.get(java.util.Calendar.DAY_OF_YEAR)
				- d1.get(java.util.Calendar.DAY_OF_YEAR);
		int y2 = d2.get(java.util.Calendar.YEAR);
		if (d1.get(java.util.Calendar.YEAR) != y2) {
			d1 = (java.util.Calendar) d1.clone();
			do {
				days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
				d1.add(java.util.Calendar.YEAR, 1);
			} while (d1.get(java.util.Calendar.YEAR) != y2);
		}
		return days;
	}

    /**
     * ���ڼ�N��(��������)
     * @param date ����
     * @param days ����
     * @return ���ڼ�����
     * @author ���
     */
    public static Date addDays(Date date, int days) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();

	}

    /**
     * �ַ������ڼ�N��(��������)
     * @param str �ַ�������
     * @param format �ַ��͸�ʽ(ʵ�ʵ��ַ������ڸ�ʽ��yyyyMMdd yyyy-MM-dd)
     * @param days ����
     * @return ���ڼ�����
     * @author ���
     */
    public static Date addDays(String str,String format, int days) throws AppException{
		Calendar calendar = Calendar.getInstance();
		Date date=stringToDate(str, format);
		calendar.setTime(date);
		int days1 = calendar.get(Calendar.DAY_OF_YEAR);
		calendar.set(Calendar.DAY_OF_YEAR, days1 + days);
		return calendar.getTime();

	}

    /**
     * @$comment ��java.util.Date ת�� java.sql.Date
     * @param date java.util.Date
     * @return java.sql.Date
     * @throws AppException
     */
    public static java.sql.Date getSqlDate(Date date) throws AppException{
        java.sql.Date sqlDate= new java.sql.Date(date.getTime());
		return sqlDate;
    }
    
    
	/**
	 * ������ǰ����仯������
	 * @param date
	 * @param i
	 * @return
	 */
	
	public static Integer addMonth(Date date,int i) {
		if(date==null){
			return null;
		}
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH,i);
		int yearno=cal.get(Calendar.YEAR);
		int monthno=cal.get(Calendar.MONTH)+1;
		return new Integer(yearno*100+monthno);
	}
    
	/**
	 * ������ǰ����仯������
	 * @param idate
	 * @param i
	 * @return
	 * @throws Exception
	 */
	
	public static Integer addMonth(Integer idate,int i) throws Exception{
		if(idate==null){
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Date date = sdf.parse(idate+"01");
		return addMonth(date,i);
		
	}
	/**
	 * ת������
	 * @param date
	 * @return
	 * @throws Exception
	 */
	public static String formatDate(Date date) throws Exception{
		return dateToString(date,"yyyy-MM-dd");
	}
	/**
	 * �õ�ϵͳʱ��
	 * @return
	 */
	public static String getSysDate() {
		return dateToString(new Date(),"yyyy-MM-dd HH:mm:ss");
	}
	/**
	 * ������ת������-��-�� ʱ:��:���ʽ���ַ���
	 * @param date
	 * @return
	 */	
	public static String formatDateTime(Date date){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	/**
	 * ������ת����������ʱ�����ʽ���ַ���
	 * @param date
	 * @return
	 */	
	public static String formatDateTime2(Date date){
		if(date==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return sdf.format(date);
	}
	
	
	/**
	 * ����ʽΪ��-��-�� ʱ:��:���ʽ���ַ���ת��������
	 * @param strDate
	 * @return
	 * @throws Exception
	 */
	public static Date parseDateTime(String strDate) throws Exception{
		if(strDate==null){
			return null;
		}
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.parse(strDate);
	}
}
