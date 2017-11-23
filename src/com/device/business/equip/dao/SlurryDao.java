package com.device.business.equip.dao;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.agile.erms.utils.CommUtils;
import com.device.business.equip.bean.MixEquipBean;
import com.device.business.equip.bean.SlurryAmountBean;
import com.device.business.equip.bean.SlurryAmountBeanExample;
import com.device.business.equip.bean.SlurryPerdayBean;
import com.device.business.equip.bean.SlurryPerdayBeanExample;
import com.device.business.equip.bean.SlurryPerdayBeanExample.Criteria;
import com.device.business.equip.dao.mapper.MixEquipBeanMapper;
import com.device.business.equip.dao.mapper.SlurryAmountBeanMapper;
import com.device.business.equip.dao.mapper.SlurryPerdayBeanMapper;
import com.device.business.equip.dao.mapper.SlurryRecordBeanMapper;
import com.device.business.equip.service.element.SlurryElement;
import com.device.util.TimeUtil;

public class SlurryDao {
	@Autowired
	private SlurryRecordBeanMapper slurryRecordBeanMapper;
	

	@Autowired
	private SlurryPerdayBeanMapper slurryPerdayMapper;

	@Autowired
	private SlurryAmountBeanMapper slurryAmountMapper;
	
	
	
	public List<SlurryPerdayBean> listPerday(SlurryElement element) throws ParseException{
		SlurryPerdayBeanExample example = new SlurryPerdayBeanExample();
		
		
		
		if(!CommUtils.isNullOrBlank(element.getDateAmount())){
			String end = TimeUtil.Date2String(element.getDateAmount(), "yyyy-MM-dd")+" 23:59:59";
			String start = TimeUtil.Date2String(TimeUtil.addDay(element.getDateAmount(),-30), "yyyy-MM-dd")+" 00:00:00";
			example.or()
			.andProjectIdEqualTo(element.getProjectId())
			.andSectionIdEqualTo(element.getSectionId())
			.andDateAmountBetween(TimeUtil.String2Date(start), TimeUtil.String2Date(end));
			
		}
		//example.setOrderByClause("Date_Amount");
		
		return slurryPerdayMapper.selectByExample(example);
	}
	
	public List<SlurryPerdayBean> selectSlurry(SlurryElement element) throws ParseException{
		String end = "" ;
		String  start = "";
		
		if(!CommUtils.isNullOrBlank(element.getDateAmount())){
			end = TimeUtil.Date2String(element.getDateAmount(), "yyyy-MM-dd")+" 23:59:59";
			start = TimeUtil.Date2String(TimeUtil.addDay(element.getDateAmount(),-30), "yyyy-MM-dd")+" 00:00:00";
		}
		//example.setOrderByClause("Date_Amount");
		
		return slurryPerdayMapper.selectSlurry(element.getEquipmentId(),start,end);
	}
	
	public List<SlurryAmountBean> listSectionAmount(SlurryElement element){
		SlurryAmountBeanExample example = new SlurryAmountBeanExample();
		
		example.or().andProjectIdEqualTo(element.getProjectId()).andSectionIdEqualTo(element.getSectionId());
		
		return slurryAmountMapper.selectByExample(example);
	}
	
	public List<SlurryAmountBean> listProjectAmount(SlurryElement element){
		SlurryAmountBeanExample example = new SlurryAmountBeanExample();
		
		if(!CommUtils.isNullOrBlank(element.getProjectId())){
			example.or().andProjectIdEqualTo(element.getProjectId());
		}
		
		return slurryAmountMapper.selectByProject(example);
	}
	
	
}
