package com.device.business.moniter.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.moniter.bean.PileMoniterBean;
import com.device.business.moniter.dao.PileMoniterDao;
import com.device.util.RestResponse;

@Path("/PileMoniterService")
public class PileMoniterService {
	@Autowired
	PileMoniterDao dao;
	
	@Path("/querySectionAmount")
	@GET
    @Produces(MediaType.APPLICATION_JSON)
	public Map<String,Object> querySectionAmount(@QueryParam("pileDriverNumber")String pileDriverNumber){
		RestResponse response = new RestResponse();
		List<PileMoniterBean> list = new ArrayList<PileMoniterBean>();
		try{
			list = dao.select(pileDriverNumber);
		}catch(Exception e){
			e.printStackTrace();
			response.setSuccess(false);
			return response.returnResult();
		}
		
		response.setSuccess(true);
		response.setRetObject("list", list);
		return response.returnResult();
	}
	
	
}
