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

import com.device.business.equip.bean.PileEquipBean;
import com.device.business.equip.dao.PileEquipDao;
import com.device.util.PrimaryKeyUtil;
import com.device.util.RestResponse;

@Path("/PileEquipService")
public class PileEquipService {
	
	@Autowired
	PileEquipDao PileEquipDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<PileEquipBean> list = new ArrayList<PileEquipBean>();
		int count = 0;
		try{
			list = PileEquipDao.selectByPage( start, limit);
			count = PileEquipDao.count();
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
	
	@Path("/add")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> add(@Form PileEquipBean bean){
		RestResponse response = new RestResponse();
		int count = 0;
		try{
			bean.setEquipmentId(PrimaryKeyUtil.getSeq());
			bean.setEquipmentType("双向搅拌桩机");
			count = PileEquipDao.add(bean);
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
	public Map<String,Object> modify(@Form PileEquipBean bean){
		RestResponse response = new RestResponse();
		int count = 0;
		try{
			count = PileEquipDao.modify(bean);
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
		PileEquipBean bean = new PileEquipBean();
		try{
			bean = PileEquipDao.load(id);
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
			count = PileEquipDao.del(id);
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
