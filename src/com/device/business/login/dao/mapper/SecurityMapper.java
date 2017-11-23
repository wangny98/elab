package com.device.business.login.dao.mapper;

import com.device.business.login.bean.UserBean;

public interface SecurityMapper {
    /**
     * <一句话功能简述>
     * <功能详细描述>
     * @param account
     * @return
     * @see [类、类#方法、类#成员]
     */
    UserBean getUserByAccount(String account);
}
