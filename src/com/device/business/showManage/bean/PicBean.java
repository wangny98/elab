package com.device.business.showManage.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class PicBean {
	private String id;
	private String show_id;
	private String pic_descript;
	private String pic_name;
	private String pic_path;
	/**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getShow_id() {
		return show_id;
	}
	public void setShow_id(String show_id) {
		this.show_id = show_id;
	}
	public String getPic_descript() {
		return pic_descript;
	}
	public void setPic_descript(String pic_descript) {
		this.pic_descript = pic_descript;
	}
	public String getPic_name() {
		return pic_name;
	}
	public void setPic_name(String pic_name) {
		this.pic_name = pic_name;
	}
	public String getPic_path() {
		return pic_path;
	}
	public void setPic_path(String pic_path) {
		this.pic_path = pic_path;
	}
    
    
}
