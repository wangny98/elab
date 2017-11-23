package com.device.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.device.business.login.dao.mapper.SecurityMapper;
import com.device.business.login.element.LoginElement;
import com.device.business.userManage.bean.UserBean;
import com.device.business.userManage.dao.mapper.UserMapper;
import com.device.constant.DmConstants;
import com.device.core.util.CommUtils;

/**
 * URL 拦截器防止恶意访问系统
 * @author zhoujing
 *
 */
@Component
@Scope("prototype")
public class URLAllowedFilter implements Filter {

    private String forwardTo = "";

    @Autowired
    URLFilterHelper uRLFilterHelper;

    @Autowired
    SecurityMapper securityMapper;

    @Autowired
    UserMapper userMapper;

    public void destroy() {
        // TODO Auto-generated method stub
    }

    /**
     * 设置当前线程用户信息
     * <功能详细描述>
     * @param hrequest
     * @param httpServletResponse
     * @param openedRoughURL
     * @param servletPath
     * @param alarmManageURL
     * @param dictionaryManageURL
     * @throws IOException
     * @see [类、类#方法、类#成员]
     */
    private void setThreadUserInfo(HttpServletRequest hRequest, HttpServletResponse hResponse) throws IOException {
        //从session获取登录用户对象
        Object sessionObject = hRequest.getSession().getAttribute(DmConstants.SESSION_INFO);
        //如果为空则设置错误

        //System.out.println("local : "+hRequest.getRequestURL());
        String loginUrl = new String("/resource/security/validate");
        String printPdUrl = new String("/resource/checkManage/printPd");
        String printApplyUrl = new String("/resource/innerAllocate/printApply");
        String machineAccountUrl = new String("/resource/assetManage/machineAccount");
        String baseInfoReport = new String("/resource/propertyReport/reportBaseReport");
        String pringChangeInfo = new String("/resource/propertyReport/pringChangeInfo");

        List<String> list = new ArrayList<String>();
        list.add(loginUrl);
        list.add(printPdUrl);
        list.add(machineAccountUrl);
        list.add(printApplyUrl);
        list.add(baseInfoReport);
        list.add(pringChangeInfo);

        //System.out.println(hRequest.getRequestURL().toString().indexOf(loginUrl));
        if (CommUtils.isNullOrBlank(sessionObject)) {
            String loginId = hRequest.getParameter("global_id");
            if (!CommUtils.isNullOrBlank(loginId)) {
                UserBean bean = userMapper.loadUser(loginId);
                if (CommUtils.isNullOrBlank(bean)) {
                    hResponse.sendError(999);
                } else {
                    LoginElement sessionElement = new LoginElement();
                    sessionElement.setUserId(loginId);
                    sessionElement.setUserFullName(bean.getFullname());
                    AuthorityContext.setAuthorityInfo(sessionElement);
                }
            } else {
                AuthorityContext.clean();
                boolean isAllow = false;
                for (String url : list) {
                    if (hRequest.getRequestURL().toString().indexOf(url) > 0) {
                        isAllow = true;
                        break;
                    }
                }
                if (isAllow) {
                } else {
                    hResponse.sendError(999); //添加respone报错信息  
                }
            }
        } else {
            LoginElement sessionElement = (LoginElement) sessionObject;
            sessionElement.setIpAddress(getIpAddr(hRequest));
            //获取登录用户帐号
            String userAccount = sessionElement.getUserName();
            UserBean userBean = null;
            /*try {
                userBean = securityMapper.getUserByAccount(userAccount);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (null == userBean) {
                hResponse.sendError(999); //添加respone报错信息  
            } else {*/
            AuthorityContext.setAuthorityInfo(sessionElement);
        }

    }

    /**
     * 判断是否允许不登入
     * <功能详细描述>
     * @param servletPath
     * @param roughUrlList
     * @return
     * @see [类、类#方法、类#成员]
     */
    private Boolean checkRoughUrl(String servletPath, List<String> roughUrlList) {
        boolean isAllowed = false;
        for (String roughUrl : roughUrlList) {
            boolean regexResult = servletPath.matches(roughUrl);
            if (regexResult) {
                isAllowed = true;
                break;
            }
        }
        return isAllowed;
    }

    private Boolean checkLoginUrl(String servletPath, List<String> loginUrlList, HttpServletRequest hRequest,
            HttpServletResponse hResponse) throws IOException {
        boolean isAllowed = false;
        for (String loginUrl : loginUrlList) {
            boolean regexResult = servletPath.matches(loginUrl);
            if (regexResult) {
                isAllowed = true;
                this.setThreadUserInfo(hRequest, hResponse);
                break;
            }
        }
        return isAllowed;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        this.setThreadUserInfo((HttpServletRequest) request, (HttpServletResponse) response);

        //System.out.println("filter ....................................... : "+AuthorityContext.getLoginElement());
        chain.doFilter(request, response);

    }

    public void init(FilterConfig config) throws ServletException {
        // TODO Auto-generated method stub
    }

    /**
    * 取得request IP Address
    * @param request
    * @return
    */
    public String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
