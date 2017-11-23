package com.device.business.equip.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.ProjectBean;
import com.device.business.equip.bean.ProjectBeanExample;
import com.device.business.equip.dao.mapper.ProjectBeanMapper;
import com.device.business.equip.service.element.ProjectElement;

public class ProjectDao {
	@Autowired
	private ProjectBeanMapper mapper;
	
	public List<ProjectBean> selectByPage(ProjectElement element, int start, int limit){
		List<ProjectBean> list = new ArrayList<ProjectBean>();
		ProjectBeanExample example = new ProjectBeanExample();
		list = mapper.selectByExample(example, new RowBounds(start,limit));
		return list;
	}
	
	public List<ProjectBean> selectAll(){
		return mapper.selectAll();
	}
	
	public int count(ProjectElement element){
		ProjectBeanExample example = new ProjectBeanExample();
		return (int) mapper.countByExample(example);
	}
	
	public int add(ProjectBean bean){
		return mapper.insert(bean);
	}
	
	public int del(String id){
		ProjectBeanExample example = new ProjectBeanExample();
		example.or().andProjectIdEqualTo(id);
		return mapper.deleteByExample(example);
	}
	
	public ProjectBean selectById(String id){
		return mapper.selectById(id);
	}
	
	public int modify(ProjectBean record){
		return mapper.updateByExample(record);
	}
}
