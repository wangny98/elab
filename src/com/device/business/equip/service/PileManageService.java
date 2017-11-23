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

import com.agile.erms.utils.CommUtils;
import com.device.business.common.dao.CommonDao;
import com.device.business.equip.bean.PileBean;
import com.device.business.equip.bean.PileBrokenBean;
import com.device.business.equip.bean.SectionBean;
import com.device.business.equip.bean.SectionPileBean;
import com.device.business.equip.dao.PileBrokenDao;
import com.device.business.equip.dao.PileDao;
import com.device.business.equip.dao.SectionDao;
import com.device.business.equip.service.element.PileElement;
import com.device.business.equip.service.element.SectionElement;
import com.device.common.KVBean;
import com.device.util.RestResponse;
import com.device.util.TimeUtil;


@Path("/pileManager")
public class PileManageService {

    @Autowired
    PileDao pileDao;

    @Autowired
    PileBrokenDao pileBrokenDao;

    @Autowired
    CommonDao commonDao;

    @Autowired
    SectionDao sectionDao;


    @Path("/search")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> search(@FormParam("sectionNumber") String sectionNumber,
                                      @FormParam("pileDriverNumber") String pileDriverNumber, @FormParam("pileNumber") String pileNumber,
                                      @FormParam("startTime") String startStr, @FormParam("endTime") String endStr,
                                      @FormParam("start") int start, @FormParam("limit") int limit,
                                      @FormParam("projectId") String projectId) {

        RestResponse response = new RestResponse();
        List<PileBean> list = new ArrayList<PileBean>();
        int count = 0;

        try {

            PileElement element = new PileElement();
            element.setSectionNumber(sectionNumber);
            element.setPileDriverNumber(pileDriverNumber);
            element.setPileNumber(pileNumber);
            if (!CommUtils.isNullOrBlank(startStr)) {
                element.setStartTime(TimeUtil.String2Date(startStr + " 00:00:00"));
            }
            System.out.println(endStr);
            if (!CommUtils.isNullOrBlank(endStr)) {
                element.setEndTime(TimeUtil.String2Date(endStr + " 23:59:59"));
            }
            element.setProjectId(projectId);

            list = pileDao.selectByPage(element, start, limit);
            count = pileDao.count(element);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }

        response.setRetObject("piles", list);
        response.setRetObject("total", count);
        response.setSuccess(true);
        return response.returnResult();
    }

    @Path("/searchAll")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> search(@Form PileElement element) {
        RestResponse response = new RestResponse();
        List<PileBean> list = new ArrayList<PileBean>();
        try {
            list = pileDao.selectAll(element);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetObject("pile", list);
        return response.returnResult();
    }

    @Path("/searchBroken")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> searchBroken(@FormParam("sectionNumber") String sectionNumber,
                                            @FormParam("pileDriverNumber") String pileDriverNumber, @FormParam("pileNumber") String pileNumber,
                                            @FormParam("startTime") String startStr, @FormParam("endTime") String endStr,
                                            @FormParam("start") int start, @FormParam("limit") int limit) {
        RestResponse response = new RestResponse();
        List<PileBrokenBean> list = new ArrayList<PileBrokenBean>();
        int count = 0;

        try {
            PileElement element = new PileElement();
            element.setSectionNumber(sectionNumber);
            element.setPileDriverNumber(pileDriverNumber);
            element.setPileNumber(pileNumber);
            if (!CommUtils.isNullOrBlank(startStr)) {
                element.setStartTime(TimeUtil.String2Date(startStr + " 00:00:00"));
            }
            if (!CommUtils.isNullOrBlank(endStr)) {
                element.setEndTime(TimeUtil.String2Date(endStr + " 23:59:59"));
            }
            list = pileBrokenDao.selectByPage(element, start, limit);
            count = pileBrokenDao.count(element);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetObject("pile", list);
        response.setRetObject("total", count);
        return response.returnResult();
    }

    @Path("/sectionPileInfo")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> sectionPileInfo() {
        RestResponse response = new RestResponse();
        List<SectionPileBean> list = new ArrayList<SectionPileBean>();
        int count = 0;
        try {
            //list = pileDao.sectionPileInfo();
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetObject("list", list);
        response.setRetObject("total", count);
        return response.returnResult();
    }

    @Path("/reportSearchKV")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Map<String, Object> reportSearchKV() {
        RestResponse response = new RestResponse();
        String allSection = "SELECT Section_ID key1,Section_Name value1 FROM t_luding_section_info";
        String allPileEquip = "select a.Equipment_Code key1,a.Equipment_Name value1 from t_luding_pile_equipment a";

        List<KVBean> l1 = new ArrayList<KVBean>();
        List<KVBean> l2 = new ArrayList<KVBean>();
        try {
            l1 = commonDao.selectKV(allSection);
            l2 = commonDao.selectKV(allPileEquip);
        } catch (Exception e) {
            e.printStackTrace();
            response.setSuccess(false);
            return response.returnResult();
        }
        response.setSuccess(true);
        response.setRetObject("allSection", l1);
        response.setRetObject("allPileEquip", l2);
        return response.returnResult();
    }
}
