/*
 * 
 *  @(#)CookieUtil.java Created on 2011-4-20
 *
 * Copyright 2004-2011 Huawei Tech. Co. Ltd. All Rights Reserved.
 * 
 * Description�?
 * 
 * Version�?          1.0
 *
 */
package com.device.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.device.core.util.StringTools;

/**
 * 
 * 用于进行Cookie的各类操�?
 * 
 * @author  庄海�?
 * @version  [版本�? 2012-7-26]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public final class CookieUtil
{
    private static final int DEFAULT_COOKIE_AGE = 0x278d00;
    
    private CookieUtil()
    {
    }
    
    /**
     * 从请求中获取某一个指定名称的Cookie
     * @param request HTTP请求
     * @param name 指定的Cookie名称
     * @return Cookie信息，如果为 null则表示没有这个�?
     */
    public static Cookie getCookie(HttpServletRequest request, String name)
    {
        Cookie[] cookies = request.getCookies();
        
        if (cookies == null || name == null || name.length() == 0)
        {
            return null;
        }
        for (int i = 0; i < cookies.length; i++)
        {
            /* TODO:暂时去掉了Domain的验�?*/
            if (name.equals(cookies[i].getName()))
            {
                if (!StringTools.isNullOrEmpty(cookies[i].getValue()))
                {
                    return cookies[i];
                }
                
            }
        }
        return null;
    }
    
    /**
     * 删除在一个响应中的Cookie
     * @param request 用户的请求消息，用于获取Path路径
     * @param response 给用户的响应信息
     * @param cookie �?��删除的Cookie
     */
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, Cookie cookie)
    {
        if (cookie != null)
        {
            cookie.setPath(getPath(request));
            cookie.setValue("");
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }
    
    /**
     * 设置Cookie的相关的�?
     * @param request HTTP请求
     * @param response HTTP响应
     * @param name Cookie名称
     * @param value  Cookie�?
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value)
    {
        /* 默认的Cookie存在时间�?x278d00 */
        setCookie(request, response, name, value, DEFAULT_COOKIE_AGE);
    }
    
    /**
     * 设置Cookie的相关�?，增加一个Cookie有消息参�?
     * @param request HTTP请求
     * @param response HTTP响应
     * @param name Cookie名称
     * @param value Cookie的相关�?
     * @param maxAge Cookie的生命周�?
     */
    public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
        int maxAge)
    {
        Cookie cookie = new Cookie(name, value == null ? "" : value);
        cookie.setMaxAge(maxAge);
        cookie.setPath(getPath(request));
        response.addCookie(cookie);
    }
    
    /**
     * 获得请求中的Path路径
     * @param request HTTP请求
     * @return 路径如果为空，则返回 /，否则返回全部路�?
     */
    private static String getPath(HttpServletRequest request)
    {
        String path = request.getContextPath();
        return (path == null || path.length() == 0) ? "/" : path;
    }
    
}
