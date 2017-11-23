package com.device.business.organizationManage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.organizationManage.bean.OrganizationBean;
import com.device.business.organizationManage.dao.OrganizationDao;
import com.device.business.organizationManage.element.OrganizationElement;

import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/organizationManager")
public class OrganizationService {
	@Autowired
	OrganizationDao organizationDao;
	
	@Path("/orgTree")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public List<OrgTreeBean> infoOrgTree(@QueryParam("id") String versionId) {
		OrgTreeBean root = null;
		try {
            root = organizationDao.getTree(versionId);
        }catch (Exception e) {
        	e.printStackTrace();
        }
		return root.getChildren();
	}
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<OrganizationBean> list = new ArrayList<OrganizationBean>();
		int count = 0;
		
		try{
			list = organizationDao.queryOrganization(searchName, start, limit);
			count = organizationDao.countOrganization(searchName);
		}catch(Exception e){
			e.printStackTrace();
			response.setRetCode(ReturnCode.SystemException);
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("org", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SUCCESS);
		return response.returnResult();
	}
	
	@Path("/addOrganization")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addRole(@Form OrganizationElement organization,@FormParam("parent")String parent_id){
		RestResponse response = new RestResponse();
		OrganizationBean bean = new OrganizationBean();
		
		try{
			BeanUtils.copyProperties(bean, organization);
			organizationDao.addOrganization(bean,parent_id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		
		return response.returnResult();
	}
	
	@Path("/loadOrganization")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> loadRole(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		OrganizationBean bean = new OrganizationBean();
		try{
			bean = organizationDao.loadOrganization(id);
			response.setRetObject("org", bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/modifyOrganization")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyRole(@Form OrganizationElement organization){
		RestResponse response = new RestResponse();
		OrganizationBean bean = new OrganizationBean();
		try{
			BeanUtils.copyProperties(bean, organization);
			organizationDao.updOrganization(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/deleteOrganization")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> deleteRole(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			organizationDao.deleteOrganization(id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
}
