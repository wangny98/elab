package com.device.business.equip.dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.equip.bean.PileBean;
import com.device.business.equip.bean.PileBeanExample;
import com.device.business.equip.bean.PileBeanExample.Criteria;
import com.device.business.equip.bean.SectionPileBean;
import com.device.business.equip.dao.mapper.PileBeanMapper;
import com.device.business.equip.dao.mapper.SectionBeanMapper;
import com.device.business.equip.service.element.PileElement;
import com.device.business.login.element.LoginElement;
import com.device.filter.AuthorityContext;
import com.device.util.TimeUtil;

public class PileDao {
	@Autowired
	PileBeanMapper mapper;
	@Autowired
	SectionBeanMapper sectionMapper;

	public List<PileBean> selectByPage(PileElement element,int start, int limit) throws ParseException{
		List<PileBean> list = new ArrayList<PileBean>();
		
		PileBeanExample example = new PileBeanExample();

		Criteria c = example.createCriteria();
		System.out.println("section number:"+element.getSectionNumber());
		if(!CommUtils.isNullOrBlank(element.getSectionNumber()))
			c.andSectionNumberEqualTo(element.getSectionNumber());
		
		if(!CommUtils.isNullOrBlank(element.getPileNumber()))
			c.andPileNumberLike("%"+element.getPileNumber()+"%");

		if(!CommUtils.isNullOrBlank(element.getProjectId()))
			c.andProjectIdEqualTo(element.getProjectId());
		
		if(!CommUtils.isNullOrBlank(element.getPileDriverNumber()))
			c.andPileDriverNumberLike("%"+element.getPileDriverNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getMixStationNumber()))
			c.andMixStationNumberLike("%"+element.getMixStationNumber()+"%");

		
		if(!CommUtils.isNullOrBlank(element.getStartTime()))
			c.andStartTimeGreaterThanOrEqualTo(TimeUtil.Date2StartTime(element.getStartTime()));
		
		if(!CommUtils.isNullOrBlank(element.getEndTime()))
			c.andEndTimeLessThanOrEqualTo(TimeUtil.Date2EndTime(element.getEndTime()));
		
		LoginElement loginElement = AuthorityContext.getLoginElement();
		if("1".equals(loginElement.getUserId())){
			
		}else{
			List<String> sectionNumberList = sectionMapper.getCheckedACL(loginElement.getUserId());
			if(!sectionNumberList.isEmpty()){
				c.andSectionNumberIn(sectionNumberList);
			}
		}
		
		list = mapper.selectByExample(example, new RowBounds(start,limit));
		return list;
	}
	
	public List<PileBean> selectByProjectId(String projectId, int start, int limit){
		return mapper.selectByProjectId(projectId, start, limit);
	}
	
	public PileBean selectById(String id){
		return mapper.selectById(id);
	}
	
	public List<PileBean> selectAll(PileElement element) throws ParseException{
		List<PileBean> list = new ArrayList<PileBean>();
		
		PileBeanExample example = new PileBeanExample();
		Criteria c = example.createCriteria();
		if(!CommUtils.isNullOrBlank(element.getStartTime()))
			c.andStartTimeGreaterThanOrEqualTo(TimeUtil.Date2StartTime(element.getStartTime()));
		if(!CommUtils.isNullOrBlank(element.getEndTime()))
			c.andEndTimeLessThanOrEqualTo(TimeUtil.Date2EndTime(element.getEndTime()));
		
		if(!CommUtils.isNullOrBlank(element.getSectionNumber()))
			c.andSectionNumberEqualTo(element.getSectionNumber());
		if(!CommUtils.isNullOrBlank(element.getPileDriverNumber()))
			c.andPileDriverNumberEqualTo(element.getPileDriverNumber());
		
		list = mapper.selectAll(example);
		return list;
	}
	
	public int count(PileElement element) throws ParseException{
		PileBeanExample example = new PileBeanExample();

		Criteria c = example.createCriteria();
		
		if(!CommUtils.isNullOrBlank(element.getSectionNumber()))
			c.andSectionNumberLike("%"+element.getSectionNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileNumber()))
			c.andPileNumberLike("%"+element.getPileNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileDriverNumber()))
			c.andPileDriverNumberLike("%"+element.getPileDriverNumber()+"%");

		if(!CommUtils.isNullOrBlank(element.getProjectId()))
			c.andProjectIdEqualTo(element.getProjectId());

		if(!CommUtils.isNullOrBlank(element.getMixStationNumber()))
			c.andMixStationNumberLike("%"+element.getMixStationNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getStartTime()))
			c.andStartTimeGreaterThanOrEqualTo(TimeUtil.Date2StartTime(element.getStartTime()));
		
		if(!CommUtils.isNullOrBlank(element.getEndTime()))
			c.andEndTimeLessThanOrEqualTo(TimeUtil.Date2EndTime(element.getEndTime()));
		
		LoginElement loginElement = AuthorityContext.getLoginElement();
		if("1".equals(loginElement.getUserId())){
			
		}else{
			List<String> sectionNumberList = sectionMapper.getCheckedACL(loginElement.getUserId());
			if(!sectionNumberList.isEmpty()){
				c.andSectionNumberIn(sectionNumberList);
			}
		}
		
		return (int) mapper.countByExample(example);
	}

	public List<SectionPileBean> sectionPileInfo(){
		String today = TimeUtil.Date2String(new Date(), "yyyy-MM-dd");
		String userId = AuthorityContext.getLoginElement().getUserId();
		return mapper.sectionPileInfo(userId,today);
	}
}
