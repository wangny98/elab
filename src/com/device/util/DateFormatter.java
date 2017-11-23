/*
 * 
 *  @(#)DateFormatter.java Created on 2011-4-11
 *
 * Copyright 2004-2005 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description 
 * 
 * CopyrightVersion 
 *
 */
package com.device.util;

import java.lang.annotation.Annotation;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jboss.resteasy.spi.StringParameterUnmarshaller;
import org.jboss.resteasy.util.FindAnnotation;

import com.device.core.util.StringTools;

public class DateFormatter implements StringParameterUnmarshaller<Date> {
    private SimpleDateFormat formatter = null;

    /**
     * 实现
     * 
     * @param annotations 注解
     */
    public void setAnnotations(Annotation[] annotations) {
        DateFormat format = FindAnnotation.findAnnotation(annotations, DateFormat.class);
        formatter = new SimpleDateFormat(format.value());
    }

    /**
     * 转化日期
     * 
     * @param str 传入需转化为日期的string
     * @return Date 返回转化的日期
     */
    public Date fromString(String str) {
        Date d = null;
        synchronized (str) {
            try {
                if (!StringTools.isNullOrEmpty(str)) {
                    d = formatter.parse(str);
                }
            } catch (ParseException e) {
                //记录日志
            }
        }
        return d;
    }

}