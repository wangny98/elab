package com.device.business.login.dao;

import java.util.HashMap;
import java.util.Map;

import com.device.business.login.bean.UserBean;
import com.device.business.login.element.LoginElement;
import com.device.business.login.dao.mapper.SecurityMapper;
import org.springframework.beans.factory.annotation.Autowired;

public class SecurityRepository {

    @Autowired
    private SecurityMapper securityMapper;

    public Map<String, ?> validateUser(LoginElement loginElement) {
        Map<String, ?> validate = new HashMap<String, Object>();
        UserBean userBean = securityMapper.getUserByAccount(loginElement.getUserName());
        return validate;
    }
}
