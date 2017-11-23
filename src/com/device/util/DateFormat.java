/*
 * 
 *  @(#)DateFormat.java Created on 2011-4-11
 *
 * Copyright 2004-2005 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description 
 * 
 * CopyrightVersion 
 *
 */
package com.device.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.resteasy.annotations.StringParameterUnmarshallerBinder;

@Retention(RetentionPolicy.RUNTIME)
@StringParameterUnmarshallerBinder(DateFormatter.class)
public @interface DateFormat {
    /**
     * 返回值
     */
    String value();
}
