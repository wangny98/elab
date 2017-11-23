package com.device.business.service.bean;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public class AssetBean_info {
	
	private String id;  //资产ID
	private String asset_no; //资产编号
	private String asset_name; //资产名称
	private String asset_type;  //资产类型
	private String asset_status; //资产状态
	
	private String asset_nation_no; //国资编号
	private String account_time; //入账时间
	private String vendor; //生产厂商
	private String value; //资产价值
	private String use_age; //使用年限
	private String repair_contact; //维修单位联系电话
	private String user;  //使用人
	
	private String asset_size; //规格型号
	private String product_no; //出厂编号
	private String is_measure; //计量器具(是否)
	private String use_direction; //使用方向
	private String is_checked; //是否盘点

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getAsset_no() {
		return asset_no;
	}


	public void setAsset_no(String asset_no) {
		this.asset_no = asset_no;
	}


	public String getAsset_name() {
		return asset_name;
	}


	public void setAsset_name(String asset_name) {
		this.asset_name = asset_name;
	}


	public String getAsset_type() {
		return asset_type;
	}


	public void setAsset_type(String asset_type) {
		this.asset_type = asset_type;
	}


	public String getAsset_status() {
		if("1".equals(asset_status)) 
			return "未验收";
		else if("2".equals(asset_status)) 
			return "已验收";
		else if("3".equals(asset_status)) 
			return "已调拨(部门间)";
		else if("4".equals(asset_status)) 
			return "已调拨(部门内)";
		else if("5".equals(asset_status)) 
			return "已报废";
		return "状态不明";
	}


	public void setAsset_status(String asset_status) {
		this.asset_status = asset_status;
	}

	/*	public String getFaentrydate() {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String time = null;
		if (faentrydate == null) {
			return "";
		} else {
			time = format.format(faentrydate);
		}
		return time;
	}
	
	public void setFaentrydate(Date faentrydate) {
		this.faentrydate = faentrydate;
	}*/

	public String getAsset_nation_no() {
		return asset_nation_no;
	}


	public void setAsset_nation_no(String asset_nation_no) {
		this.asset_nation_no = asset_nation_no;
	}


	public String getAccount_time() {
		return account_time;
	}


	public void setAccount_time(String account_time) {
		this.account_time = account_time;
	}


	public String getVendor() {
		return vendor;
	}


	public void setVendor(String vendor) {
		this.vendor = vendor;
	}


	public String getRepair_contact() {
		return repair_contact;
	}


	public void setRepair_contact(String repair_contact) {
		this.repair_contact = repair_contact;
	}


	/**
	 * 重写toString()方法
	 * 
	 * @return 返回对象的字符串表达
	 */
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.SHORT_PREFIX_STYLE);
	}


	public String getValue() {
		return value;
	}


	public void setValue(String value) {
		this.value = value;
	}


	public String getUser() {
		return user;
	}


	public void setUser(String user) {
		this.user = user;
	}


	public String getUse_age() {
		return use_age;
	}


	public void setUse_age(String use_age) {
		this.use_age = use_age;
	}


	public String getAsset_size() {
		return asset_size;
	}


	public void setAsset_size(String asset_size) {
		this.asset_size = asset_size;
	}


	public String getProduct_no() {
		return product_no;
	}


	public void setProduct_no(String product_no) {
		this.product_no = product_no;
	}


	public String getIs_measure() {
		return is_measure;
	}


	public void setIs_measure(String is_measure) {
		this.is_measure = is_measure;
	}


	public String getUse_direction() {
		return use_direction;
	}


	public void setUse_direction(String use_direction) {
		this.use_direction = use_direction;
	}


	public String getIs_checked() {
		return is_checked;
	}


	public void setIs_checked(String is_checked) {
		this.is_checked = is_checked;
	}
}
