package com.device.business.transferManager.service;

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

import com.device.business.transferManager.bean.ExamineBean;
import com.device.business.transferManager.dao.ExamineManageDao;
import com.device.business.transferManager.element.TransferElement;
import com.device.constant.ReturnCode;
import com.device.util.RestResponse;
import com.sun.org.apache.commons.beanutils.BeanUtils;


@Path("/examineManage")
public class ExamineManageService {
	@Autowired
	ExamineManageDao examineManageDao;
	
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<ExamineBean> list = new ArrayList<ExamineBean>();
		int count = 0;
		try{
			list = examineManageDao.query(searchName, start, limit);
			count = examineManageDao.count(searchName);
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
	public Map<String,Object> add(@Form TransferElement element){
		RestResponse response = new RestResponse();
		ExamineBean bean = new ExamineBean();
		
		try{
			BeanUtils.copyProperties(bean, element);
			examineManageDao.add(bean);
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
			ExamineBean bean  = examineManageDao.load(id);
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
	public Map<String,Object> modify(@Form TransferElement element,@FormParam("id")String id){
		RestResponse response = new RestResponse();
		ExamineBean bean = new ExamineBean();
		bean.setId(id);
		try{
			BeanUtils.copyProperties(bean, element);
			examineManageDao.update(bean);
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
			examineManageDao.delete(id);
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
