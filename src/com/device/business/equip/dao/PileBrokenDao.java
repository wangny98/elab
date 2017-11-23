package com.device.business.equip.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.equip.bean.PileBrokenBean;
import com.device.business.equip.bean.PileBrokenBeanExample;
import com.device.business.equip.bean.PileBrokenBeanExample.Criteria;
import com.device.business.equip.dao.mapper.PileBrokenBeanMapper;
import com.device.business.equip.dao.mapper.SectionBeanMapper;
import com.device.business.equip.service.element.PileElement;
import com.device.business.login.element.LoginElement;
import com.device.filter.AuthorityContext;

public class PileBrokenDao {
	@Autowired
	PileBrokenBeanMapper mapper;
	@Autowired
	SectionBeanMapper sectionMapper;
	
	public List<PileBrokenBean> selectByPage(PileElement element, int start, int limit){
		List<PileBrokenBean> list = new ArrayList<PileBrokenBean>();
		
		PileBrokenBeanExample example = new PileBrokenBeanExample();

		Criteria c = example.createCriteria();
		
		if(!CommUtils.isNullOrBlank(element.getSectionNumber()))
			c.andSectionNumberLike("%"+element.getSectionNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileNumber()))
			c.andPileNumberLike("%"+element.getPileNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileDriverNumber()))
			c.andPileDriverNumberLike("%"+element.getPileDriverNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getStartTime()))
			c.andStartTimeGreaterThanOrEqualTo(element.getStartTime());
		
		if(!CommUtils.isNullOrBlank(element.getEndTime()))
			c.andEndTimeLessThanOrEqualTo(element.getEndTime());
		
		LoginElement loginElement = AuthorityContext.getLoginElement();
		if("1".equals(loginElement.getUserId())){
			
		}else{
			List<String> sectionNumberList = sectionMapper.getCheckedACL(loginElement.getUserId());
			if(!sectionNumberList.isEmpty()){
				c.andSectionNumberIn(sectionNumberList);
			}
		}
		c.andPileStatusGreaterThan(0);
		list = mapper.selectByExample(example, new RowBounds(start,limit));
		return list;
	}
	
	public int count(PileElement element){
		PileBrokenBeanExample example = new PileBrokenBeanExample();

		Criteria c = example.createCriteria();
		
		if(!CommUtils.isNullOrBlank(element.getSectionNumber()))
			c.andSectionNumberLike("%"+element.getSectionNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileNumber()))
			c.andPileNumberLike("%"+element.getPileNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getPileDriverNumber()))
			c.andPileDriverNumberLike("%"+element.getPileDriverNumber()+"%");
		
		if(!CommUtils.isNullOrBlank(element.getStartTime()))
			c.andStartTimeGreaterThanOrEqualTo(element.getStartTime());
		
		if(!CommUtils.isNullOrBlank(element.getEndTime()))
			c.andEndTimeLessThanOrEqualTo(element.getEndTime());
		
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
}
