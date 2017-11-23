package com.device.business.roleManage.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ws.rs.FormParam;
import javax.ws.rs.QueryParam;
//import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.login.element.LoginElement;
import com.device.business.roleManage.bean.CheckNodeBean;
import com.device.business.roleManage.dao.FunctionDao;
import com.device.constant.ReturnCode;
import com.device.filter.AuthorityContext;
import com.device.util.RestResponse;

@Path("/functionManage")
public class FunctionManageService {

    @Autowired
    FunctionDao functionDao;

    @Path("/loadCheckTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public List<CheckNodeBean> loadCheckTree(@QueryParam("roleId") String roleId) {
        //RestResponse response = new RestResponse();
        List<CheckNodeBean> treeList = new ArrayList<CheckNodeBean>();
        try {
            //treeList = functionDao.loadAssignFunc(roleId);
        	treeList=functionDao.loadAssignModule(roleId);
        } catch (Exception e) {
            e.printStackTrace();
            //response.setRetCode(ReturnCode.SystemException);
            //response.setSuccess(false);
            return treeList;
        }

        return treeList;
    }

    @Path("/modiftyFunc")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> modiftyFunc(@FormParam("roleId") String roleId, @FormParam("codes") String codes) {
        RestResponse response = new RestResponse();
        int count=0;
        try {
        	count=functionDao.updateFuncCode(roleId, codes);
        	if(count==0){
        		response.setSuccess(false);
        	}else{
                response.setSuccess(true);
        	}
        } catch (Exception e) {
            e.printStackTrace();
            response.setRetCode(ReturnCode.SystemException);
            response.setSuccess(false);
            return response.returnResult();
        }
        return response.returnResult();
    }
    
    @Path("/infoFuncTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> infoFuncTree() {
        RestResponse response = new RestResponse();
        List<Map<String, Object>> treeList = new ArrayList<Map<String, Object>>();
        //	Set<String> codes = new HashSet<String>();

        LoginElement login = AuthorityContext.getLoginElement();
        if(login==null){
        	response.setSuccess(false);
        	return response.returnResult();
        }
        try {
            treeList = functionDao.loadFuncTree(login.getUserId());
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetCode(ReturnCode.SUCCESS);
        response.setRetObject("module", treeList);
        response.setRetObject("uId", login.getUserId());
        return response.returnResult();
    }
}
