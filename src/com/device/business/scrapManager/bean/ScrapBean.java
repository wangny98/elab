package com.device.business.scrapManager.bean;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 
 * @author I
 *
/* Table: 资产报废单                                                 */
/*
==============================================================
create table t_property_scrap  (
   id                 varchar2(32),
   scrap_no           varchar2(60),
   scrap_department   varchar2(32),
   apply_user         varchar2(32),
   scrap_reason       varchar2(200),
   remark             varchar2(200),
   org_id             varchar2(32),
   scrap_time         date,
   examine_time       date,
   check_time         date,
   apply_time         date,
   state              INTEGER,
   action             varchar2(60),
   creator_id		  varchar2(32),
   creator_name		  varchar2(60),
   create_time		  date,
   deleteflag		  INTEGER
);

 */
public class ScrapBean {
    private String id;
    private String scrap_no;
    private String scrap_department;
    
    private Integer scrap_use_type=0;
    private String scrap_user_name;
    private String scrap_use_depart;

    
    private String apply_user;
    private String scrap_reason;
    private String remark;
    private String org_id;
    
    private Date scrap_time;
    private Date examine_time;
    private Date check_time;
    private Date apply_time;
    
    private int state=1;
    private String action;
    private String creator_id;
	private String creator_name;
	private Date create_time;
	private int deleteflag;
    /**
     * 重写toString()方法
     * 
     * @return 返回对象的字符串表达
     */
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	public Integer getScrap_use_type() {
		return scrap_use_type;
	}

	public void setScrap_use_type(Integer scrap_use_type) {
		this.scrap_use_type = scrap_use_type;
	}

	public String getScrap_user_name() {
		return scrap_user_name;
	}

	public void setScrap_user_name(String scrap_user_name) {
		this.scrap_user_name = scrap_user_name;
	}

	public String getScrap_use_depart() {
		return scrap_use_depart;
	}

	public void setScrap_use_depart(String scrap_use_depart) {
		this.scrap_use_depart = scrap_use_depart;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getScrap_no() {
		return scrap_no;
	}

	public void setScrap_no(String scrap_no) {
		this.scrap_no = scrap_no;
	}

	public String getScrap_department() {
		return scrap_department;
	}

	public void setScrap_department(String scrap_department) {
		this.scrap_department = scrap_department;
	}

	public String getApply_user() {
		return apply_user;
	}

	public void setApply_user(String apply_user) {
		this.apply_user = apply_user;
	}

	public String getScrap_reason() {
		return scrap_reason;
	}

	public void setScrap_reason(String scrap_reason) {
		this.scrap_reason = scrap_reason;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getOrg_id() {
		return org_id;
	}

	public void setOrg_id(String org_id) {
		this.org_id = org_id;
	}

	public Date getScrap_time() {
		return scrap_time;
	}

	public void setScrap_time(Date scrap_time) {
		this.scrap_time = scrap_time;
	}

	public Date getExamine_time() {
		return examine_time;
	}

	public void setExamine_time(Date examine_time) {
		this.examine_time = examine_time;
	}

	public Date getCheck_time() {
		return check_time;
	}

	public void setCheck_time(Date check_time) {
		this.check_time = check_time;
	}

	public Date getApply_time() {
		return apply_time;
	}

	public void setApply_time(Date apply_time) {
		this.apply_time = apply_time;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getCreator_id() {
		return creator_id;
	}

	public void setCreator_id(String creator_id) {
		this.creator_id = creator_id;
	}

	public String getCreator_name() {
		return creator_name;
	}

	public void setCreator_name(String creator_name) {
		this.creator_name = creator_name;
	}

	public Date getCreate_time() {
		return create_time;
	}

	public void setCreate_time(Date create_time) {
		this.create_time = create_time;
	}

	public int getDeleteflag() {
		return deleteflag;
	}

	public void setDeleteflag(int deleteflag) {
		this.deleteflag = deleteflag;
	}
    
    
}
