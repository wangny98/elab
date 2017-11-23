
package com.device.util;

import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.annotation.XmlElement;


public class RestResponse
{
    /**
     * 结果集，MAP�?retcode"定义结果码，其余键�?可以自定�?
     */
    public static final int ARRAY_SIZE = 10;
    
    private Map<String, Object> retObjects = new HashMap<String, Object>(ARRAY_SIZE);
    
    /**
     * 设置结果�?
     * @param retCode  结果码，具体定义见com.huawei.ecc.rest.constant  RetCode.java
     */
    public void setRetCode(int retCode)
    {
        this.retObjects.put("retcode", Integer.valueOf(retCode));
    }
    
    /**
     * 获取结果�?
     * @return  结果�?
     */
    public int getRetCode()
    {
        Object retcode = this.retObjects.get("retcode");
        
        if (retcode == null)
        {
            return 0;
        }
        else
        {
            return Integer.parseInt(retcode.toString());
        }
    }
    
    /**
     * 设置自定义返回�?
     * @param key        键，�?��返回给客户端的某些自定义�?
     * @param object     值，自定义键的�?
     */
    public void setRetObject(String key, Object object)
    {
        this.retObjects.put(key, object);
    }
    
    /**
     * 获取自定义返回�?
     * @param key   自定义键
     * @return   自定义键对应的�?
     */
    public Object getRetObject(String key)
    {
        return this.retObjects.get(key);
    }
    
    /**
     * 设置返回给前台的success�?
     * @param value  返回给前台的success值：true,false
     */
    public void setSuccess(Boolean value)
    {
        this.retObjects.put("success", value);
    }
    
    /**
     * 获取返回给前台的success�?
     * @return  success�?
     */
    public Boolean getSuccess()
    {
        boolean rtn = false;
        if (null != this.retObjects.get("success")
            && "true".equalsIgnoreCase(this.retObjects.get("success").toString()))
        {
            rtn = true;
        }
        else
        {
            rtn = false;
        }
        return rtn;
    }
    
    /**
     * 获取�?��返回�?
     * @return  结果集， MAP形式，框驾后续会封装成JSON
     */
    @XmlElement(name = "EccJsonReturnResponse")
    public Map<String, Object> returnResult()
    {
        return this.retObjects;
    }
    
}