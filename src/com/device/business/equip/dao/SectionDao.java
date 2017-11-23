package com.device.business.equip.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.agile.erms.utils.CommUtils;
import com.device.business.equip.bean.SectionBean;
import com.device.business.equip.bean.SectionBeanExample;
import com.device.business.equip.bean.SectionBeanExample.Criteria;
import com.device.business.equip.dao.mapper.SectionBeanMapper;
import com.device.business.equip.service.element.SectionElement;

public class SectionDao {
	@Autowired
	private SectionBeanMapper SectionBeanMapper;
	
	public List<SectionBean> selectByPage(SectionElement element, int start, int limit){
		List<SectionBean> list = new ArrayList<SectionBean>();
		SectionBeanExample example = new SectionBeanExample();
		Criteria c = example.createCriteria();
		
		if(!CommUtils.isNullOrBlank(element.getProjectId()))
			c.andProjectIdEqualTo(element.getProjectId());
		
		if(0==start&&0==limit){
			list = SectionBeanMapper.selectByExample(example, new RowBounds());
		}else{
			list = SectionBeanMapper.selectByExample(example, new RowBounds(start,limit));
		}
		return list;
	}
	
	public int count(SectionElement element){
		SectionBeanExample example = new SectionBeanExample();
		return (int) SectionBeanMapper.countByExample(example);
	}

	public int add(SectionBean record){
		return SectionBeanMapper.insert(record);
	}
	
	public SectionBean load(String id){
		return SectionBeanMapper.selectById(id);
	}
	
	public int modify(SectionBean record){
		return SectionBeanMapper.updateById(record);
	}
	
	public int del(String id){
		return SectionBeanMapper.deleteById(id);
	}
	
	public List<SectionBean> getUserFunc(String user_id){
		return SectionBeanMapper.getUserACL(user_id);
	}
	
	@Transactional
	public int modifyUserFunc(String user_id,String resourceIds){
		int num = 0;
		SectionBeanMapper.deleteUserACL(user_id);
		if(!CommUtils.isNullOrBlank(resourceIds)){
			List<String> res = new ArrayList<String>();
			
			res = Arrays.asList(resourceIds.split(","));
			for(String resource_id:res){
				int i = SectionBeanMapper.addUserACL(user_id, resource_id);
				num = num+i;
			}
		}
		return num;
	}
	
	public String getCheckedACL(String user_id){
		List<String> list = new ArrayList<String>();
		list = SectionBeanMapper.getCheckedACL(user_id);
		String str="";
		if(list.size()>0){
			for(String s:list){
				str=s+",";
			}
			str=str.substring(0, str.length());
		}
		return str;
	}
}
