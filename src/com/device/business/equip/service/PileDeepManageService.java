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
			//System.out.println("original size:"+down.size()+" pile number:"+pileNumber);
			//filter data: only show record of every 0.2 pile_deep
			for(int i = 0 ; i < down.size() ; i++) {
                //reset speed to less than 2.5
                Short maxSpeed=249;
                if(down.get(i).getSpeed()!=null) {
                    //System.out.println("original speed:"+down.get(i).getSpeed());
                    if (down.get(i).getSpeed()>=maxSpeed)
                    {
                       down.get(i).setSpeed(maxSpeed);
                        //System.out.println("new speed:"+down.get(i).getSpeed());
                    }
                }
				BigDecimal tPileDeep=down.get(i).getPileDeep();
				BigDecimal ten=new BigDecimal("10");
				tPileDeep=tPileDeep.multiply(ten);
				int t=tPileDeep.intValue();
				if(t%2!=0) down.remove(i);
			}
            //System.out.println("new size:"+down.size());

			up = pileDeepDao.selectByUP(pileNumber);
            //filter data: only show record of every 0.2 pile_deep
            for(int i = 0 ; i < up.size() ; i++) {
                //reset speed to less than 2.5
                Short maxSpeed=249;
                if(up.get(i).getSpeed()!=null) {
                    if (up.get(i).getSpeed()>= maxSpeed) up.get(i).setSpeed(maxSpeed);
                }
                BigDecimal tPileDeep=up.get(i).getPileDeep();
                BigDecimal ten=new BigDecimal("10");
                tPileDeep=tPileDeep.multiply(ten);
                int t=tPileDeep.intValue();
                if(t%2!=0) up.remove(i);
            }

			machineInfo = pileDeepDao.selectMachineInfo(pileNumber);

			absSum = pileDeepDao.selectByAbs(pileNumber);
            //filter data: only show record of every 0.2 pile_deep
            for(int i = 0 ; i < absSum.size() ; i++) {
                //reset speed to less than 2.5
                Short maxSpeed=249;
                if(absSum.get(i).getSpeed()!=null) {
                    if (absSum.get(i).getSpeed() >= maxSpeed) absSum.get(i).setSpeed(maxSpeed);
                }
                BigDecimal tPileDeep=absSum.get(i).getPileDeep();
                BigDecimal ten=new BigDecimal("10");
                tPileDeep=tPileDeep.multiply(ten);
                int t=tPileDeep.intValue();
                if(t%2!=0) absSum.remove(i);
            }

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
