package com.device.util;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 以静态变量保存Spring ApplicationContext, 
 * 可在任何代码任何地方任何时�?中取出ApplicaitonContext.
 * 
 * @author  庄海�?
 * @version  [版本�? 2012-7-26]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class SpringContextHolder implements ApplicationContextAware, DisposableBean
{
    
    private static ApplicationContext applicationContext = null;
    
    /**
     * 实现ApplicationContextAware接口, 注入Context到静态变量中.
     * @param applicationContext 上下�?
     */
    public void setApplicationContext(ApplicationContext applicationContext)
    {
        
        SpringContextHolder.applicationContext = applicationContext;
    }
    
    /**
     * 实现DisposableBean接口,在Context关闭时清理静态变�?
     */
    public void destroy()
    {
        
    }
    
    /**
     * 取得存储在静态变量中的ApplicationContext.
     * @return 返回上下�?
     */
    public static ApplicationContext getApplicationContext()
    {
        assertContextInjected();
        return applicationContext;
    }
    
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋�?对象的类�?
     * @param name 上下文中定义的bean的名�?
     * @return 返回对象
     */
    public static Object getBean(String name)
    {
        assertContextInjected();
        return applicationContext.getBean(name);
    }
    
    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋�?对象的类�?
     * @param requiredType 上下文中定义的bean对象类型
     * @return 返回对象
     */
    @SuppressWarnings("unchecked")
    public static Object getBean(Class requiredType)
    {
        assertContextInjected();
        return applicationContext.getBean(requiredType);
    }
    
    /**
     * �?��ApplicationContext不为�?
     */
    private static void assertContextInjected()
    {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注�?请在applicationContext.xml中定义SpringContextHolder");
        }
    }
}
