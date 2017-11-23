/*
 * 
 *  @(#)ReadProperties.java Created on 2011-6-2
 *
 * Copyright 2004-2005 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description 
 * 
 * CopyrightVersion 
 *
 */
package com.device.util;

import java.util.List;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationFactory;

/**
 * 
 * 读取配置文件
 * 
 * @author  庄海�?
 * @version  [版本�? 2012-7-26]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public final class ReadServerParam {

    private static Configuration config;

    static {
        try {
            ConfigurationFactory factory = new ConfigurationFactory();
            factory.setConfigurationURL(ReadServerParam.class.getResource("/properties/configuration.xml"));
            config = factory.getConfiguration();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件�?
     * @param key �?
     * @return �?
     */
    public static String getMessage(String key) {

        if (!config.containsKey(key)) {
            return null;
        }
        String value = config.getString(key);
        return value;
    }

    /**
     * 读取配置的list�?
     * @param key �?
     * @return �?
     */
    @SuppressWarnings("unchecked")
    public static List getMessageList(String key) {

        if (!config.containsKey(key)) {
            return null;
        }
        List value = config.getList(key);
        return value;
    }

    private ReadServerParam() {
    }

}
