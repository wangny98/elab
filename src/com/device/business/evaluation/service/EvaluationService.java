package com.device.business.evaluation.service;

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

import com.device.business.equip.bean.PileBean;
import com.device.business.evaluation.bean.EvalBean;
import com.device.business.evaluation.bean.EvalEntireBean;
import com.device.business.evaluation.dao.EvaluationDao;
import com.device.util.RestResponse;

@Path("/EvaluationService")
public class EvaluationService {
	@Autowired
	EvaluationDao evaluationDao;
	
	@Path("/query")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> query(){
		RestResponse response = new RestResponse();
		List<EvalBean> list = new ArrayList<EvalBean>();
		try{
			list = evaluationDao.selectAll();
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setRetObject("list",list);
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/queryEvaluationModel")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> queryEvaluationModel(){
		RestResponse response = new RestResponse();
		List<String> list = new ArrayList<String>();
		try{
			list = evaluationDao.selectModel();
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setRetObject("list",list);
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/modify")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modify(@Form EvalBean bean){
		RestResponse response = new RestResponse();
		try{
			int count = evaluationDao.updateEvalById(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/modifyEntity")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> modifyEntity(@Form EvalEntireBean bean){
		RestResponse response = new RestResponse();
		try{
			int count = evaluationDao.updateEvalEntityById(bean);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		return response.returnResult();
	}
	
	@Path("/evaluation")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> evaluation(@FormParam("ids")String ids,@FormParam("evalId")Integer evalId){
		RestResponse response = new RestResponse();
		String[] pileIds = ids.split(",");
		try{
			EvalBean bean = evaluationDao.selectById(evalId);
			List<EvalEntireBean> list = evaluationDao.getEntirsListByEvalId(evalId);
			for(int i=0;i<pileIds.length;i++){
				evaluationDao.eval(pileIds[i], bean,list);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		return response.returnResult();
	}
	

	
}
