package com.device.util;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.device.core.util.StringTools;

/**
 * 
 * 读取项目根路�?
 * 
 * @author  庄海�?
 * @version  [版本�? 2012-7-26]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public final class ReadWebrootPath extends HttpServlet {
    /**
     * 注释内容
     */
    private static final long serialVersionUID = 3082957558439782971L;

    /**
     * {@inheritDoc}
     */
    public void init(ServletConfig config) {
        try {
            super.init(config);
            String path = config.getServletContext().getRealPath("/");
            /**
             * 非空�?��
             */
            if (!StringTools.isNullOrEmpty(path)) {
                System.setProperty("webroot.key", path);
            } else {
                //记录日志
            }
        } catch (ServletException e) {
            //记录日志
        }
    }
}
