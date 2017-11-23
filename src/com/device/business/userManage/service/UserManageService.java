package com.device.business.userManage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;


import com.device.business.roleManage.bean.RoleBean;
import com.device.business.userManage.bean.UserBean;
import com.device.business.userManage.dao.UserDao;
import com.device.business.userManage.element.UserElement;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/userManage")
public class UserManageService {
	
	@Autowired
	UserDao userDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<UserBean> list = new ArrayList<UserBean>();
		int count = 0;
		try{
			list = userDao.queryUser(searchName, start, limit);
			count = userDao.countUser(searchName);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("users", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	
	@Path("/addUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addUser(@Form UserElement user){
		RestResponse response = new RestResponse();
		UserBean bean = new UserBean();
		

		try{
			BeanUtils.copyProperties(bean, user);
			userDao.addUser(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	
	@Path("/loadUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadUser(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			UserBean bean = userDao.loadUser(id);
			response.setRetObject("user", bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	
	@Path("/modifyUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyUser(@Form UserElement user){
		RestResponse response = new RestResponse();
		UserBean bean = new UserBean();
		try{
			BeanUtils.copyProperties(bean, user);
			userDao.updateUser(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/deleteUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteUser(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			userDao.deleteUser(id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	
	@Path("/queryAssignRole")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> queryAssignRole(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		List<RoleBean> list = new ArrayList<RoleBean>();
		try{
			list = userDao.listAssignRole(id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setRetObject("assignRoles", list);
		return response.returnResult();
	}
	
	@Path("/assignRoleUser")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> assignRoleUser(@FormParam("userId")String userId,@FormParam("roleIds")String roleIds){
		RestResponse response = new RestResponse();
		String[] roles = roleIds.split(",");
		try{
			userDao.assignRoleUser(userId, roles);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
}
