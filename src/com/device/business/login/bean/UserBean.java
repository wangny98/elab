package com.device.business.login.bean;

import java.io.Serializable;
import java.util.Date;

import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 用户element类，与前台交互的字段
 * 
 * @author geek
 * @version [版本号, 2012-8-10]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public class UserBean implements Serializable {

    /**
     * 序列号版本
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户账号
     */
    @FormParam("account")
    private String account;

    /**
     * 家庭住址
     */
    @FormParam("address")
    private String address;

    /**
     * 用户别名
     */
    @FormParam("alias")
    private String alias;

    /**
     * 创建该人员的时间
     */
    @FormParam("createTime")
    private Date createTime;

    /**
     * 创建者的id
     */
    @FormParam("creatorId")
    private String creatorId;
    /**
     * 创建者的名字
     */
    @FormParam("creatorName")
    private String creatorName;
    /**
     * 删除标记
     */
    @FormParam("deleteflag")
    private int deleteflag;

    /**
     * 用户所属部门的id
     */
    @FormParam("departmentId")
    private String departmentId;

    /**
     * 电子邮箱
     */
    @FormParam("email")
    private String email;
    /**
     * 用户姓名
     */
    @FormParam("fullname")
    private String fullname;

    /**
     * 用户的id
     */
    @FormParam("id")
    private String id;
    /**
     * 手机号码
     */
    @FormParam("mobilphone")
    private String mobilphone;

    /**
     * 固定电话
     */
    @FormParam("phone")
    private String phone;
    /**
     * 备注
     */
    @FormParam("remark")
    private String remark;
    /**
     * 用户性别
     */
    @FormParam("sex")
    private int sex;

    /**
     * 用户状态
     */
    @FormParam("state")
    private int state;
    /**
     * 邮政编码
     */

    private String zipcode;
    /**
     * 密级
     */
    private int securitylevel;

    private Integer isfromad;

    public int getSecuritylevel() {
        return this.securitylevel;
    }

    public void setSecuritylevel(int securitylevel) {
        this.securitylevel = securitylevel;
    }

    public String getAccount() {
        return account;
    }

    public String getAddress() {
        return address;
    }

    public String getAlias() {
        return alias;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public int getDeleteflag() {
        return deleteflag;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public String getEmail() {
        return email;
    }

    public String getFullname() {
        return fullname;
    }

    public String getMobilphone() {
        return mobilphone;
    }

    public String getPhone() {
        return phone;
    }

    public String getRemark() {
        return remark;
    }

    public int getSex() {
        return sex;
    }

    public int getState() {
        return state;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setAccount(String username) {
        this.account = username;
    }

    public void setAddress(String adress) {
        this.address = adress;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setCreatorName(String creator) {
        this.creatorName = creator;
    }

    public void setDeleteflag(int deleteflag) {
        this.deleteflag = deleteflag;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setMobilphone(String mobilphone) {
        this.mobilphone = mobilphone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

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

    public String getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }

    public Integer getIsfromad() {
        return this.isfromad;
    }

    public void setIsfromad(Integer isfromad) {
        this.isfromad = isfromad;
    }
}
