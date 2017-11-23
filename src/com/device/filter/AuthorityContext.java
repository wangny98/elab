package com.device.filter;

import com.device.business.login.element.LoginElement;
import com.device.constant.DmConstants;
import com.device.core.util.CommUtils;

/**
 * 
 * 存放当前线程的用户信息
 * <功能详细描述>
 * 
 * @author  fuwenbin
 * @version  [版本号, 2012-12-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class AuthorityContext {

    public static final ThreadLocal<LoginElement> infoLocal = new ThreadLocal<LoginElement>();

    public static void setAuthorityInfo(LoginElement info) {
        infoLocal.set(info);
    }

    public static LoginElement getLoginElement() {

        /*LoginElement loginElement = new LoginElement();
        loginElement.setUserFullName("11");
        loginElement.setUserId("1");
        loginElement.setUserPWD("adminPass");
        setAuthorityInfo(loginElement);
        return loginElement;*/

        return infoLocal.get();
    }

    /**
     * 获取用户当前登录选择语言
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getCurrLanguage() {

        LoginElement loginElement = getLoginElement();
        if (CommUtils.isNullOrBlank(loginElement.getLanguage())) {
            return DmConstants.INTERNATION_CHINESE;
        }
        return loginElement.getLanguage();

    }

    public static String getCurrentUserAccount() {
        return getLoginElement().getUserName();
    }

    public static void clean() {
        infoLocal.set(null);
    }

}
