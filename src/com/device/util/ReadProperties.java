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

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Properties;

/**
 * 
 * 读取配置文件
 * 
 * @author  庄海�?
 * @version  [版本�? 2012-7-26]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public final class ReadProperties {

    private static Properties properties = new Properties();

    private ReadProperties() {
    }

    private static void read(String filePath) {
        /**
         * 获取file
         */
        File file = new File(filePath);

        /**
         * 如果file存在,则加�?
         */
        if (file.exists()) {
            InputStream is = null;
            try {
                is = new FileInputStream(file);

                properties.load(is);
            } catch (FileNotFoundException e) {
            } catch (IOException e) {

            } finally {
                try {
                    is.close();
                } catch (IOException e) {

                }
            }
        }
    }

    /**
     * 读取配置文件�?
     * @param filePath 文件路径
     * @param key �?
     * @return �?
     */
    public static String getMessage(String filePath, String key) {
        String path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        try {
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {

        }
        read(path + filePath);
        String message = "";
        try {
            message = new String(properties.getProperty(key).getBytes("ISO-8859-1"), "utf-8");
        } catch (UnsupportedEncodingException e) {

        }

        return message;
    }

}
