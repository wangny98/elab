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
import org.springframework.transaction.annotation.Transactional;

import com.device.business.scrapManager.bean.ScrapBean;
import com.device.business.transferManager.dao.TransferManageDao;
import com.device.business.transferManager.element.TransferElement;
import com.device.util.RestResponse;
import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.business.transferManager.bean.TransferBean;
import com.device.constant.ReturnCode;
import com.sun.org.apache.commons.beanutils.BeanUtils;


@Path("/transferManage")
public class TransferManageService {
	@Autowired
	TransferManageDao transferDao;
	
	@Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> search(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit,
			@FormParam("stateStr") String stateStr,@Form TransferElement element){
		RestResponse response = new RestResponse();
		List<TransferBean> list = new ArrayList<TransferBean>();
		String states="";
		if(stateStr.equals("transfer")){
			states="(1,2,3)";
		}else if(stateStr.equals("examine")){
			states="(3,4,5)";
		}else if(stateStr.equals("check")){
			states="(5,6,7)";
		}else if(stateStr.equals("simple")){
			states="(6)";
		}
		int count = 0;  
		try{
			list = transferDao.queryTransfer(searchName, start, limit,states);
			count = transferDao.count(searchName,states);
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
	public Map<String,Object> add(@Form TransferElement element,@FormParam("property_ids")String property_ids){
		RestResponse response = new RestResponse();
		TransferBean bean = new TransferBean();
		
		try{
			BeanUtils.copyProperties(bean, element);
			transferDao.add(bean,property_ids);
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
			TransferBean bean  = transferDao.load(id);
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
		TransferBean bean = new TransferBean();
		bean.setId(id);
		try{
			BeanUtils.copyProperties(bean, element);
			transferDao.update(bean);
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
			transferDao.delete(id);
			response.setSuccess(true);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	@Path("/searchBaseProperty")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> searchBaseProperty(@FormParam("searchName") String searchName,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		int count = 0;
		try{
			list = transferDao.listBaseProperty(searchName, start, limit);
			count = transferDao.countBaseProperty(searchName);
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
	
	@Path("/queryBaseProperty")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public  Map<String,Object> queryBaseProperty(@FormParam("transfer_id") String transfer_id,@FormParam("start") int start, @FormParam("limit") int limit){
		RestResponse response = new RestResponse();
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		int count = 0;
		try{
			list = transferDao.queryCheckProperty(transfer_id,start,limit);
			count = transferDao.countCheckProperty(transfer_id);
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
	
	

	@Path("/changTransferState")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public  Map<String,Object> changTransferState(@FormParam("transfer_id") String transfer_id,@FormParam("state") String state){
		RestResponse response = new RestResponse();
		try{
			TransferBean bean = transferDao.load(transfer_id);
			if(bean==null||state==null){
				response.setRetCode(101);//单据不存在
				response.setSuccess(false);
			}else{
				if(this.checkState(state, bean)==1){
					response.setRetCode(102);//状态被修改不存在
					response.setSuccess(false);
				}else{
					transferDao.updatePropertyStatue(transfer_id, state);
					response.setSuccess(true);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			response.setRetCode(ReturnCode.SystemException);
			return response.returnResult();
		}
		return response.returnResult();
	}
	
	public int checkState(String state,TransferBean bean){
		int num=0;
		Integer changeState = Integer.valueOf(state);
		Integer st=Integer.valueOf(bean.getState());

		switch(changeState){
			case 2:{
				if(st!=1&&st!=3){
					num=1;
				}
				break;
			}
			case 3:{
				if(st!=1){
					num=1;
				}
				break;
			}
			case 4:{
				if(st!=3){
					num=1;
				}
				break;
			}
			case 5:{
				if(st!=3){
					num=1;
				}
				break;
			}
			case 6:{
				if(st!=5){
					num=1;
				}
				break;
			}
			case 7:{
				if(st!=5){
					num=1;
				}
				break;
			}
		}
		return num;
	}
}
