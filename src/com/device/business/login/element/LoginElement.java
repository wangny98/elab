package com.device.business.login.element;

import java.io.Serializable;
import java.util.HashMap;


import javax.ws.rs.FormParam;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * 首页登录element类
 * 
 * @author  Fu Wen Bin
 * @version  [v1.0, 2012-9-3]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class LoginElement implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 用户名称
     */
    @FormParam("user.username")
    private String userName;

    /**
     * 用户密码
     */
    @FormParam("user.password")
    private String userPWD;

    /**
     * 用户验证码
     */
    @FormParam("user.verificationcode")
    private String userVerificationCode;

    /**
     * IP地址
     */
    private String ipAddress;
    
    /**
     * 客户端用户MAC地址
     */
    private String macAddress;

    /**
     * 用户UUID
     */
    private String userId;

    /**
     * 语言
     */
    private String language;
    
    private HashMap<String, Object> map = new HashMap<String, Object>();

    public HashMap<String, Object> getMap() {
		return map;
	}

	public void setMap(HashMap<String, Object> map) {
		this.map = map;
	}

	public void setLanguage(String language) {
        this.language = language;
    }

    public String getLanguage() {
        return this.language;
    }

    /**
     * 用户全名
     */
    private String userFullName;

    /**
     * 登录时间
     */
    private String loginTime;
    

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPWD() {
        return userPWD;
    }

    public void setUserPWD(String userPWD) {
        this.userPWD = userPWD;
    }

    public String getUserVerificationCode() {
        return userVerificationCode;
    }

    public void setUserVerificationCode(String userVerificationCode) {
        this.userVerificationCode = userVerificationCode;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        if ("0:0:0:0:0:0:0:1".equals(ipAddress)) {
            this.ipAddress = "127.0.0.1";
        } else {
            this.ipAddress = ipAddress;
        }

    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserFullName() {
        return this.userFullName;
    }

    public void setUserFullName(String userFullName) {
        this.userFullName = userFullName;
    }

    public String getLoginTime() {
        return this.loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

    public String getMacAddress() {
        return this.macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }



}
