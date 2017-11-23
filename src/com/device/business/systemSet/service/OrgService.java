package com.device.business.systemSet.service;


import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.systemSet.bean.DepartmentBean;
import com.device.business.systemSet.bean.UnitBean;
import com.device.business.systemSet.dao.DepartmentDao;
import com.device.business.systemSet.dao.UnitDao;
import com.device.business.systemSet.element.DepartmentElement;
import com.device.business.systemSet.element.UnitElement;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/OrgService")
public class OrgService {
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private UnitDao UnitDao;
	
	@Path("/getTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public List<OrgTreeBean> search(){
		RestResponse response = new RestResponse();
		OrgTreeBean bean = new OrgTreeBean();
		try{
			bean =departmentDao.getTreeNode();
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
		}
		return bean.getChildren();
	}
	
	@Path("/addUnit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  addUnit(@Form UnitElement element,@FormParam("parentId")String parentId){
		RestResponse response = new RestResponse();
		UnitBean bean = new UnitBean();
		try{
			BeanUtils.copyProperties(bean, element);
			UnitDao.add(bean,parentId);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/addDepart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  addDepart(@Form DepartmentElement element,@FormParam("parentId")String parentId){
		RestResponse response = new RestResponse();
		DepartmentBean bean = new DepartmentBean();
		try{
			BeanUtils.copyProperties(bean, element);
			departmentDao.add(bean,parentId);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	
	@Path("/modifyUnit")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  modifyUnit(@FormParam("orgId")String orgId,@Form UnitElement element){
		RestResponse response = new RestResponse();
		UnitBean bean = new UnitBean();
		try{
			BeanUtils.copyProperties(bean, element);
			bean.setId(orgId);
			UnitDao.modify(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/modifyDepart")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  modifyDepart(@FormParam("orgId")String orgId,@Form DepartmentElement element){
		RestResponse response = new RestResponse();
		DepartmentBean bean = new DepartmentBean();
		try{
			BeanUtils.copyProperties(bean, element);
			bean.setId(orgId);
			departmentDao.modify(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}


	@Path("/getOrg")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  getOrg(@FormParam("orgId")String orgId,@FormParam("orgType")int orgType){
		RestResponse response = new RestResponse();
		Object o = new Object();
		try{
			if(orgType==1){
				o = UnitDao.get(orgId);
			}else{
				o = departmentDao.get(orgId);
			}
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}

		response.setRetObject("org", o);
		return response.returnResult();
	}
	
	@Path("/deleteOrg")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object>  deleteOrg(@FormParam("orgId")String orgId,@FormParam("orgType")int orgType){
		RestResponse response = new RestResponse();
		try{
			if(orgType==1){
				UnitDao.delete(orgId);
			}else{
				departmentDao.delete(orgId);
			}
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		return response.returnResult();
	}
}
