package com.device.business.roleManage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.roleManage.bean.NodeBean;
import com.device.business.roleManage.bean.RoleBean;
import com.device.business.roleManage.dao.RoleDao;
import com.device.business.roleManage.element.RoleElement;
import com.device.business.tracker.dao.UseAuditRepository;
import com.device.component.cache.UserInfoCache;
import com.device.constant.ReturnCode;
import com.device.filter.AuthorityContext;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/roleManage")
public class RoleManageService {

    @Autowired
    RoleDao roleDao;

    @Autowired
    private UserInfoCache userInfoCache;

    /**
     * 审计日志业务操作
     */
    @Autowired
    UseAuditRepository actionTracker;

    @Path("/infoFuncTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> infoFuncTree() {
        RestResponse response = new RestResponse();
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        Set<String> codes = new HashSet<String>();

        LoginElement login = AuthorityContext.getLoginElement();
        try {
            treeList = roleDao.loadFuncTree(login.getUserId());
            codes = roleDao.loadFuncCode(login.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetCode(ReturnCode.SUCCESS);
        response.setRetObject("module", treeList);
        response.setRetObject("func", codes);
        response.setRetObject("uId", login.getUserId());
        return response.returnResult();
    }

    @Path("/infoAllTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> infoAllTree() {
        RestResponse response = new RestResponse();
        List<NodeBean> treeList = new ArrayList<NodeBean>();
        Set<String> codes = new HashSet<String>();
        try {
            treeList = roleDao.loadAllTree();
            codes = roleDao.loadAllCode();
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetCode(ReturnCode.SUCCESS);
        response.setRetObject("module", treeList);
        response.setRetObject("func", codes);
        return response.returnResult();
    }

    @Path("/modifyCodes")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> modifyCodes(@FormParam("codes") String codes) {
        RestResponse response = new RestResponse();
        return response.returnResult();
    }

    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> search(@FormParam("searchName") String searchName, @FormParam("start") int start,
            @FormParam("limit") int limit) {
        RestResponse response = new RestResponse();
        List<RoleBean> list = new ArrayList<RoleBean>();
        int count = 0;
        try {
            list = roleDao.queryRole(searchName, start, limit);
            count = roleDao.countRole(searchName);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }

        response.setSuccess(true);
        response.setRetObject("roles", list);
        response.setRetObject("total", count);
        return response.returnResult();
    }

    @Path("/getAllRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> getAllRole(@FormParam("userId") String userId) {
        RestResponse response = new RestResponse();
        List<RoleBean> list = new ArrayList<RoleBean>();
        try {
            list = roleDao.queryAllRole(userId);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetObject("roles", list);
        return response.returnResult();
    }

    @Path("/addRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> addRole(@Form RoleElement role) {
        RestResponse response = new RestResponse();
        RoleBean bean = new RoleBean();

        try {
            BeanUtils.copyProperties(bean, role);
            roleDao.addRole(bean);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }

        return response.returnResult();
    }

    @Path("/loadRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> loadRole(@FormParam("id") String id) {
        RestResponse response = new RestResponse();
        try {
            RoleBean bean = roleDao.loadRole(id);
            response.setRetObject("role", bean);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
    }

    @Path("/modifyRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> modifyRole(@Form RoleElement role, @FormParam("roleId") String roleId) {
        RestResponse response = new RestResponse();
        RoleBean bean = new RoleBean();
        role.setId(roleId);
        try {
            BeanUtils.copyProperties(bean, role);
            roleDao.updRole(bean);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            actionTracker.addAuditLog("角色管理", "修改角色 ", 0);
            return response.returnResult();
        }
        return response.returnResult();
    }

    @Path("/deleteRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> deleteRole(@FormParam("id") String id) {
        RestResponse response = new RestResponse();
        try {
            roleDao.deleteRole(id);
            response.setSuccess(true);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            response.setRetCode(ReturnCode.SystemException);
            return response.returnResult();
        }
        return response.returnResult();
    }

}
