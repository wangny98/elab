package com.device.component.cache;

import java.io.Serializable;
import java.util.HashMap;

public class UserInfoCatcheDto implements Serializable {
	/**
     * 注释内容
     */
    private static final long serialVersionUID = -2875269216231991091L;
    
    HashMap<String,Object> userInfo = new HashMap<String,Object>();

    public HashMap<String,Object> getUserInfo() {
        return this.userInfo;
    }

    public void setUserInfo(HashMap<String,Object> userInfo) {
        this.userInfo = userInfo;
    }
}
