package com.device.util;

import java.io.BufferedReader;
import java.io.Reader;
import java.lang.reflect.InvocationTargetException;
import java.sql.Clob;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.PropertyUtilsBean;

import com.device.core.util.DateConverter;

/**
 * 常用工具类
 * 处理类型转换和判空
 * 
 * @author  庄海泉
 * @version  [版本号, 2012-7-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class CommUtils {

    private static final String regEx_script = "<script[^>]*?>[\\s\\S]*?<\\/script>"; // 定义script的正则表达式
    private static final String regEx_style = "<style[^>]*?>[\\s\\S]*?<\\/style>"; // 定义style的正则表达式
    private static final String regEx_html = "<[^>]+>"; // 定义HTML标签的正则表达式
   // private static final String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]"; 
    //private static final String   regEx  =  "[^a-zA-Z0-9]"; // 只允许字母和数字
    private static final String   regEx  =  "[n b t f r \n  \b  \t   \f  \r ]"; //
    
    /**
     * 将要进行模糊搜索的字符串转化为MayBatis识别的字符格式
     * @param str  要转化的字符串
     * @return  转化后的字符串
     */
    public static String convertStringToBase(String str) {
        // 如果str为null值时
        if ("null".equals(str) || CommUtils.isNullOrBlank(str)) {
            str = "";
        }
        return "%" + str + "%";
    }

    /**
     * 去除字符串中的html标签
     * @param htmlStr
     * @return
     */
    public static String delHTMLTag(String htmlStr) {
        Pattern p_script = Pattern.compile(regEx_script, Pattern.CASE_INSENSITIVE);
        Matcher m_script = p_script.matcher(htmlStr);
        htmlStr = m_script.replaceAll(""); // 过滤script标签

        Pattern p_style = Pattern.compile(regEx_style, Pattern.CASE_INSENSITIVE);
        Matcher m_style = p_style.matcher(htmlStr);
        htmlStr = m_style.replaceAll(""); // 过滤style标签

        Pattern p_html = Pattern.compile(regEx_html, Pattern.CASE_INSENSITIVE);
        Matcher m_html = p_html.matcher(htmlStr);
        htmlStr = m_html.replaceAll(""); // 过滤html标签

        return htmlStr.trim(); // 返回文本字符串
    }
    /**
     * 去除字符串中所有特殊字符
     * @param str
     * @return
     */
    public static String delTag(String str) {
        Pattern p_script = Pattern.compile(regEx);
        Matcher m_script = p_script.matcher(str);
        str = m_script.replaceAll(""); // 过滤script标签
        return str.trim(); // 返回文本字符串
    }
    /**
       * null(String)值转化为空串
       * @param value
       * @return String
       */
    public static String null2Blank(String value) {
        return null == value ? "" : value;
    }

    public static String obtainYear(String year) {
        String flag = year.indexOf("Y") >= 0 ? "Y" : "年";
        year = year.substring(0, year.indexOf(flag)).trim();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(new Date());
        int count = Integer.parseInt(year);
        return "" + (calendar.get(calendar.YEAR) + count);
    }

    /**
     * 将字符串转成满足mybatis的SQL格式
     * @param str
     * @return String
     */
    public static String convertStringToStore(String str) {
        return "%" + str + "%";
    }

    /**
     * null(Object)值转化为空串
     * @param obj
     * @return Object
     */
    public static Object null2Blank(Object obj) {
        return null == obj ? "" : obj;
    }

    /**
     * 转化Clob型为String
     * @param clob
     * @return String
     * @throws Exception
     */
    public static String getStrForClob(Clob clob) throws Exception {
        String ret = null;
        if (null != clob) {
            Reader is = clob.getCharacterStream();
            BufferedReader br = new BufferedReader(is);
            String s = br.readLine();
            StringBuffer sb = new StringBuffer();

            while (null != s) {
                sb.append(s);
                s = br.readLine();
            }
            ret = sb.toString();
        }
        return ret;
    }

    /**
     * 转化String型为Clob
     * @param str
     * @return Clob
     * @throws Exception
     */
    public static Clob getClobForString(String str) throws Exception {
        return new javax.sql.rowset.serial.SerialClob(str.toCharArray());
    }

    /**
     * 对象转化为String型
     * @param value
     * @return String
     */
    public static String object2String(Object value) {
        if (null == value) {
            return null;
        } else {
            return value.toString();
        }
    }

    /**
     * 获取List中的第一个对象
     * @param list
     * @return Object
     */
    public static Object getFirstOfList(List list) {
        if (null == list || list.isEmpty()) {
            return null;
        } else {
            return list.get(0);
        }
    }

    /**
     * 判断对象是否为空
     * @param value
     * @return boolean
     */
    public static boolean isNullOrBlank(Object value) {
        if (null == value || value.equals("")) {
            return true;
        } else {
            return false;
        }
    }
    public static boolean isAllNullOrBlank(String... args) {
    	for(String temp:args){
    		if (null != temp && !temp.equals("")) {
                return false;
            }
    	}
    	return true;
    }

    /**
     * 判断对个对象是否都是为空
     * @param objs
     * @return boolean
     */
    public static boolean isAllNullOrBlank(Object[] objs) {
        //Object[] objects=new Object[]{arg0,arg1,........};
        if (null == objs || objs.length == 0) {
            return true;
        } else {
            boolean isNull = true;
            for (Object obj : objs) {
                if (obj != null && !obj.equals("")) {
                    isNull = false;
                }
            }
            return isNull;
        }
    }

    /**
     * 判断List集合是否为空
     * @param value
     * @return boolean
     */
    public static boolean isNullOrBlank(List value) {
        if (null == value || value.isEmpty()) {
            return true;
        } else {
            boolean isNull = true;
            for (Object obj : value) {
                if (obj != null && !obj.equals("")) {
                    isNull = false;
                }
            }
            return isNull;
        }
    }

    /**
     * @description 字符转换为长整型 非法字符默认返回为0
     * @date 2008-4-17
     * @author 
     * @param s
     * @return}
     */
    public static Long parseLong(int i) {
        return new Long(i);
    }

    private CommUtils() {

    }

    /**
    * 处理字符串中的单引号  zdr
    * @param value
    * @return
    */
    public static String inSql(String value) {
        if (null == value) {
            return "";
        } else {
            return value.replace("'", "''");
        }
    }

    /**
     * 以“,”隔开的数字字符串转化为数字数组 
     * @param ids
     * @return
     */
    public static long[] getNumFromString(String ids) {

        if (null == ids || "".equals(ids)) {
            return null;
        }
        String[] str = ids.split(",");
        long[] idList = new long[str.length];
        for (int i = 0; i < str.length; i++) {
            idList[i] = Long.parseLong(str[i]);
        }
        return idList;
    }

    /**
     * 以“,”隔开的数字字符串转化为id数组 
     * @param ids
     * @return
     */
    public static String[] getIdFromString(String ids) {

        if (null == ids || "".equals(ids)) {
            return null;
        }
        String[] str = ids.split(",");
        return str;
    }

    /**
     * 对象转化为String型(当使用hql的like关键字时)
     * @param value
     * @return
     */
    public static String object2StringForHqlLike(Object value) {
        if (null == value) {
            return null;
        } else {
            return "%" + inSql(value.toString()) + "%";
        }
    }

    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param toBean   拷贝到的对象
     * @param srcBean  被拷贝的对象
     * @throws InvocationTargetException 
     * @throws IllegalAccessException 
     * @see [类、类#方法、类#成员]
     */
    public static void copyProperties(Object toBean, Object srcBean) throws IllegalAccessException,
            InvocationTargetException {

        ConvertUtilsBean convertUtils = new ConvertUtilsBean();// java自动转换的工具类
        DateConverter dateConverter = new DateConverter();// 实力㈠个日期转换类
        convertUtils.register(dateConverter, Date.class);// 注册㈠个日期类
        convertUtils.register(dateConverter, String.class);// 注册㈠个字符类
        BeanUtilsBean beanUtils = new BeanUtilsBean(convertUtils, new PropertyUtilsBean());
        beanUtils.copyProperties(toBean, srcBean);
    }

    /**
     * 返回正确的日期加上年数（规避闰年）
     * @param yymmdd 传入的年月日
     * @param years 相加的年数
     * @see [类、类#方法、类#成员]
     */
    public static String returnRightDate(String yymmdd, String years) {
        years = obtainYear(years);
        int intYears = Integer.parseInt(years);
        String[] date = yymmdd.split("-");
        StringBuilder strBuilder = new StringBuilder();
        int year = Integer.parseInt(date[0]);
        int month = Integer.parseInt(date[1]);
        int day = Integer.parseInt(date[2]);
        if (year % 4 != 0 || month != 2 || day != 29 || (year + intYears) % 4 == 0) {
            strBuilder.append(year + intYears);
            strBuilder.append("-");
            if (month <= 9)
                strBuilder.append("0" + month);
            else
                strBuilder.append(month);
            strBuilder.append("-");
            if (day <= 9)
                strBuilder.append("0" + day);
            else
                strBuilder.append(day);
            return strBuilder.toString();
        }
        strBuilder.append(year + intYears);
        strBuilder.append("-02-28");
        return strBuilder.toString();
    }

    /**
     * 获取保管期限
     * @param retention
     * @return
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("static-access")
    public static String getRetentionYear(String retention) {
        int yearIndex = retention.indexOf("Y") >= 0 ? retention.indexOf("Y") : retention.indexOf("年");
        int monthIndex = retention.indexOf("M") >= 0 ? retention.indexOf("M") : retention.indexOf("月");
        int dayIndex = retention.indexOf("D") >= 0 ? retention.indexOf("D") : retention.indexOf("日");
        int year = 0;
        int month = 0;
        int day = 0;
        if (yearIndex != -1) {
            if (!CommUtils.isNullOrBlank(retention.substring(0, yearIndex).trim())) {
                year = Integer.parseInt(retention.substring(0, yearIndex).trim());
            }
        }
        if (monthIndex != -1) {
            if (!CommUtils.isNullOrBlank(retention.substring(yearIndex + 1, monthIndex).trim())) {
                month = Integer.parseInt(retention.substring(yearIndex + 1, monthIndex).trim());
            }
        }
        if (dayIndex != -1) {
            if (!CommUtils.isNullOrBlank(retention.substring(monthIndex + 1, dayIndex).trim())) {
                day = Integer.parseInt(retention.substring(monthIndex + 1, dayIndex).trim());
            }
        }
        Calendar calendar = new GregorianCalendar();

        calendar.setTime(new Date());
        calendar.add(calendar.YEAR, year);
        calendar.add(calendar.MONTH, month);
        calendar.add(calendar.DAY_OF_MONTH, day);

        String dudate = calendar.get(calendar.YEAR) + "-" + (calendar.get(calendar.MONTH) + 1) + "-"
                + calendar.get(calendar.DAY_OF_MONTH);
        return dudate;
    }

    /**
     * 过滤特殊字符  
     * @param str
     * @return
     * @throws PatternSyntaxException
     * @see [类、类#方法、类#成员]
     */
    public static String StringFilter(String str) throws PatternSyntaxException {
        // 只允许字母和数字       
        // String    regEx   =   "[^a-zA-Z0-9]";                     
        // 清除掉所有特殊字符  
        String regEx = "[\r\n]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

}
