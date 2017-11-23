package com.device.business.showManage.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.jboss.resteasy.annotations.Form;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.showManage.bean.ScienceShowBean;
import com.device.business.showManage.dao.ScienceShowDao;
import com.device.business.showManage.element.ScienceShowElement;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;

@Path("/scienceShow")
public class ScienceShowService {

	@Autowired
	ScienceShowDao scienceShowDao;
	
	@Path("/search")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		int count = 0;
		List<ScienceShowBean> list = new ArrayList<ScienceShowBean>();
		try{
			list = scienceShowDao.query(searchName, start, limit);
			count = scienceShowDao.count(searchName);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		response.setRetCode(ReturnCode.SystemException);
		return response.returnResult();
	}
	
	@Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> add(@Form ScienceShowElement element,@FormParam("property_ids")String property_ids){
		RestResponse response = new RestResponse();
		ScienceShowBean bean = new ScienceShowBean();
		
		try{
			BeanUtils.copyProperties(bean, element);
			scienceShowDao.add(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		
		return response.returnResult();
	}
	
	
	@Path("/load")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> load(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		try{
			ScienceShowBean bean  = scienceShowDao.load(id);
			response.setRetObject("bean", bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modify(@Form ScienceShowElement element,@FormParam("id")String id){
		RestResponse response = new RestResponse();
		ScienceShowBean bean = new ScienceShowBean();
		bean.setId(id);
		try{
			BeanUtils.copyProperties(bean, element);
			scienceShowDao.update(bean);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/delete")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> delete(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		
		try{
			scienceShowDao.delete(id);
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
