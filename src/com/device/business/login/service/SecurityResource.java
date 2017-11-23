package com.device.business.login.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.login.element.LoginElement;
import com.device.business.userManage.bean.UserBean;
import com.device.business.userManage.dao.UserDao;
import com.device.component.cache.UserInfoCache;
import com.device.component.cache.UserInfoCatcheDto;
import com.device.constant.DmConstants;
import com.device.filter.AuthorityContext;
import com.device.util.RestResponse;

@Path("/security")
public class SecurityResource {

    @Autowired
    private UserDao userDao;
    
    @Autowired
    private UserInfoCache userInfoCache;

    @GET
    @Path("/validate")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> login(@Context HttpServletRequest request, @Context HttpServletResponse response,
            @QueryParam("userName") String userName, @QueryParam("userPassword") String userPassword)
            throws IOException {
        RestResponse restResponse = new RestResponse();
        //System.out.println("userName : " + userName + " ||  userPassword : " + userPassword);
        LoginElement loginElement = new LoginElement();
        UserBean user = new UserBean();

        try {
            user = userDao.validateUser(userName);
            //System.out.println("user : " + user);

            if (!CommUtils.isNullOrBlank(user) && !CommUtils.isNullOrBlank(user.getId())) {

                if (!CommUtils.isNullOrBlank(user.getPassword()) && user.getPassword().equals(userPassword)) {

                    loginElement.setUserFullName(user.getFullname());
                    loginElement.setUserName(user.getAccount());
                    loginElement.setUserId(user.getId());
                    //request.getSession().setAttribute("session_id", user.getId());
                    //System.out.println("loginElement : " + loginElement);
                    if(userInfoCache.getUserInfo(user.getId())==null){
                    	HashMap<String,Object> map =new HashMap<String,Object>();
                    	map.put("loginElement", loginElement);
                    	UserInfoCatcheDto dto = new UserInfoCatcheDto();
                    	dto.setUserInfo(map);
                    	userInfoCache.addUserInfo(user.getId(), dto);
                    }
                    AuthorityContext.setAuthorityInfo(loginElement);
                    request.getSession().setAttribute(DmConstants.SESSION_INFO, loginElement);
                    //response.sendRedirect("/deviceManager/index.html");
                    restResponse.setSuccess(true);

                } else {
                    //response.sendRedirect("/deviceManager/index.jsp");
                    restResponse.setRetCode(102);//
                    restResponse.setSuccess(false);
                }
            } else {
                //response.sendRedirect("/deviceManager/index.jsp");
                restResponse.setRetCode(101);//�û������
                restResponse.setSuccess(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //response.sendRedirect("/deviceManager/index.jsp");
            restResponse.setSuccess(false);
        }
        return restResponse.returnResult();
    }

    @GET
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> logout(@Context HttpServletRequest request, @Context HttpServletResponse response)
    {
        RestResponse restResponse = new RestResponse();
        
        //从session获取登录用户对象
        Object sessionObject = request.getSession().getAttribute(DmConstants.SESSION_INFO);
        LoginElement sessionElement = new LoginElement();
        if (!CommUtils.isNullOrBlank(sessionObject))
        {
            
            sessionElement = (LoginElement)sessionObject;
        }
        request.getSession().removeAttribute(DmConstants.SESSION_INFO);
        AuthorityContext.clean();
        restResponse.setSuccess(true);
        return restResponse.returnResult();
    }
    
    @POST
    @Path("/systemTree")
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> systemTree() {
        RestResponse restResponse = new RestResponse();

        return restResponse.returnResult();
    }
}
