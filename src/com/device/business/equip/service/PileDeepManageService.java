package com.device.business.equip.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.PperdeepBean;
import com.device.business.equip.bean.PperdeepBeanExample;
import com.device.business.equip.dao.PileDeepDao;
import com.device.util.RestResponse;

@Path("/pileDeepManager")
public class PileDeepManageService {

	@Autowired
	PileDeepDao pileDeepDao	;
	
	@Path("/queryAll")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> queryAll(@FormParam("pileNumber")String pileNumber){
		RestResponse response = new RestResponse();
		List<PperdeepBean> all = new ArrayList<PperdeepBean>();
		try{
			all = pileDeepDao.selectByAll(pileNumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("pile", all);
		return response.returnResult();
	}
	
	@Path("/queryChartData")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> queryChartData(@FormParam("pileNumber")String pileNumber){
		RestResponse response = new RestResponse();
		List<PperdeepBean> down = new ArrayList<PperdeepBean>();
		List<PperdeepBean> up = new ArrayList<PperdeepBean>();
		List<PperdeepBean> absSum = new ArrayList<PperdeepBean>();
		Map<String, Object> machineInfo = new HashMap<String, Object>();
		try{
			down = pileDeepDao.selectByDown(pileNumber);

			up = pileDeepDao.selectByUP(pileNumber);
			machineInfo = pileDeepDao.selectMachineInfo(pileNumber);
			absSum = pileDeepDao.selectByAbs(pileNumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		response.setSuccess(true);
		response.setRetObject("down", down);
		response.setRetObject("up", up);
		response.setRetObject("absSum", absSum);
		response.setRetObject("machineInfo", machineInfo);
		return response.returnResult();
	}
}
