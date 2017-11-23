package com.device.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
	

	public static SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	
	public static String Date2String(Date time){
		String str=sdf.format(time);
		return str;
	}
	
	public static String Date2String(Date time,String format){
	    SimpleDateFormat f=new SimpleDateFormat(format);
        String str=f.format(time);
        return str;
    }
	
	public static Date Date2StartTime(Date time) throws ParseException{
		return String2Date(Date2String(time,"yyyy-MM-dd")+" 00:00:00");
	}
	
	public static Date Date2EndTime(Date time) throws ParseException{
		return String2Date(Date2String(time,"yyyy-MM-dd")+" 23:59:59");
	}
	
	public static Date String2Date(String str) throws ParseException{
		if(CommUtils.isNullOrBlank(str)){
			return null;
		}
		java.util.Date date=sdf.parse(str); 
		return date;
	}
	
	public static Date addDay(int day){
		Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	
	public static Date addDay(Date date,int day){
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, day);
		return c.getTime();
	}
	
	/**
	 * @param args
	 */
	public static void main(String args[]){
		Date d = new Date();
		String temp = Date2String(d,"yyyy-MM-dd");
		System.out.println(temp);
		
		System.out.println(Date2String(addDay(d,-30)));
		
		/*Calendar c = Calendar.getInstance();
		c.add(Calendar.DATE, -30);//计算30天后的时间
		String str2=Date2String(c.getTime(),"yyyy-MM-dd");
		System.out.println("30天的时间是："+str2);*/
	}
}
