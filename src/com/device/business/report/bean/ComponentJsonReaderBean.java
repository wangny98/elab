package com.device.business.report.bean;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class ComponentJsonReaderBean {

    /**
     * 序列化
     */
    private static final long serialVersionUID = -5773519692913681516L;
    private ComponentJsonReaderMetaDataBean metaData;
    //private int total;
    private List<Map<String, Object>> data;

    public ComponentJsonReaderMetaDataBean getMetaData() {
        return this.metaData;
    }

    public void setMetaData(ComponentJsonReaderMetaDataBean metaData) {
        this.metaData = metaData;
    }

    public List<Map<String, Object>> getData() {
        return this.data;
    }

    public void setData(List<Map<String, Object>> data) {
        this.data = data;
    }

    /**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    /*public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal() {
        return total;
    }*/

}
