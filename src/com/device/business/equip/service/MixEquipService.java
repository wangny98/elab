package com.device.business.equip.service;

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

import com.device.business.equip.bean.MixEquipBean;
import com.device.business.equip.dao.MixEquipDao;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/MixEquipService")
public class MixEquipService {
	
	@Autowired
	MixEquipDao MixEquipDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<MixEquipBean> list = new ArrayList<MixEquipBean>();
		int count = 0;
		try{
			list = MixEquipDao.selectByPage( start, limit);
			count = MixEquipDao.count();
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		response.setRetObject("total", count);
		return response.returnResult();
	}
	
	@Path("/searchBySectionId")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchBySectionId(@FormParam("sectionId") String sectionId){
		RestResponse response = new RestResponse();
		List<MixEquipBean> list = new ArrayList<MixEquipBean>();
		try{
			list = MixEquipDao.selectBySectionId(sectionId);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("list", list);
		return response.returnResult();
	}
	
	
	@Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> add(@Form MixEquipBean bean){
		RestResponse response = new RestResponse();
		int count = 0;
		try{
			bean.setEquipmentId(PrimaryKeyUtil.getSeq());
			bean.setEquipmentType("自动制浆站");
			count = MixEquipDao.add(bean);
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
	public Map<String,Object> modify(@Form MixEquipBean bean){
		RestResponse response = new RestResponse();
		int count = 0;
		try{
			count = MixEquipDao.modify(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
	
	@Path("/load")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> load(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		MixEquipBean bean = new MixEquipBean();
		try{
			bean = MixEquipDao.load(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("bean", bean);
		return response.returnResult();
	}
	
	@Path("/del")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> del(@FormParam("id")String id){
		RestResponse response = new RestResponse();
		int count = 0;
		try{
			count = MixEquipDao.del(id);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("count", count);
		return response.returnResult();
	}
}
