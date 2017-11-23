package com.device.business.report.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;


/**
 * 
 * 自定义列表metaData
 * <功能详细描述>
 * 
 * @author  周晶
 * @version  [版本号, 2013-4-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ComponentJsonReaderMetaDataBean {
    /**
     * 序列化
     */
    private static final long serialVersionUID = -5773519692913681516L;

    private Object[] fields;
    private String root = "data";
    //private String totalProperty = "total";

    public String getRoot() {
        return this.root;
    }

    public void setRoot(String root) {
        this.root = root;
    }

    /**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public Object[] getFields() {
        return this.fields;
    }

    public void setFields(Object[] fields) {
        this.fields = fields;
    }

    /*public void setTotalProperty(String totalProperty) {
        this.totalProperty = totalProperty;
    }

    public String getTotalProperty() {
        return totalProperty;
    }*/

}
