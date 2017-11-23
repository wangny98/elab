package com.device.util;

import java.lang.reflect.Method;

import org.springframework.beans.BeanUtils;

/**
 * 
 * <p>Title: 几个bean转换�?</p>
 * <p>Description:  </p>
 * <pre>  </pre>
 * <p>Company: 敏捷科技</p>
 * @author 庄海�?
 * @version V1.0 2012-8-11
 * @since
 */
public final class ReflectBeanUtil {
    private ReflectBeanUtil() {

    }

    /**
     * 属�?对象拷贝
     * 
     * @param source
     * @param targetClass
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static Object attributeCopy(Object source, Class targetClass) {
        try {
            Object target = targetClass.newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 反射get方法
     * @param objectClass
     * @param fieldName
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static Method getGetMethod(Class objectClass, String fieldName) {
        StringBuffer sb = new StringBuffer();
        sb.append("get");
        sb.append(fieldName.substring(0, 1).toUpperCase());
        sb.append(fieldName.substring(1));
        try {
            return objectClass.getMethod(sb.toString());
        } catch (Exception e) {
        }
        return null;
    }

    /**  
    * 执行get方法  
     *   
     * @param o执行对象  
    * @param fieldName属�?  
    */
    public static Object invokeGet(Object o, String fieldName) {
        Method method = getGetMethod(o.getClass(), fieldName);

        if (null == method) {
            return null;
        }
        try {
            return method.invoke(o, new Object[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}