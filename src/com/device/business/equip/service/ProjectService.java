package com.device.business.equip.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.ProjectBean;
import com.device.business.equip.dao.ProjectDao;
import com.device.business.equip.service.element.ProjectElement;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/ProjectService")
public class ProjectService {
	@Autowired
	ProjectDao projectDao;
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@Form ProjectElement element,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<ProjectBean> list = new ArrayList<ProjectBean>();
		int count = 0;
		try{
			list = projectDao.selectByPage(element, start, limit);
			count = projectDao.count(element);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("project", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/searchAll")
	@POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchAll(){
		RestResponse response = new RestResponse();
		List<ProjectBean> list = new ArrayList<ProjectBean>();
		try{
			list = projectDao.selectAll();
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("project", list);
		return response.returnResult();
	}
	
	@Path("/addProject")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> addProject(@Form ProjectElement element,@Context HttpServletRequest servletRequest){
		RestResponse response = new RestResponse();
		int count=0;
		ProjectBean bean = new ProjectBean();
		try{
		BeanUtils.copyProperties(bean, element);
		count = projectDao.add(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modify(@Form ProjectBean bean){
		RestResponse response = new RestResponse();
		int count=0;
		try{
			count = projectDao.modify(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/remove")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> remove(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		int count=0;
		try{
			count = projectDao.del(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/loadProject")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> load(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		ProjectBean bean=null;
		try{
			bean = projectDao.selectById(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("projectBean", bean);
		return response.returnResult();
	}
	
}
