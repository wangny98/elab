package com.device.business.equip.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.EquipBean;
import com.device.business.equip.bean.PileEquipBean;
import com.device.business.equip.bean.PileEquipBeanExample;
import com.device.business.equip.dao.mapper.EquipBeanMapper;
import com.device.business.equip.dao.mapper.PileEquipBeanMapper;
import com.device.business.moniter.bean.EquipMoniterBean;
import com.device.business.moniter.dao.mapper.EquipMoniterBeanMapper;
import com.device.component.datasource.MultipleDataSource;

public class PileEquipDao {
	@Autowired
	private PileEquipBeanMapper mapper;
	
	@Autowired
	EquipMoniterBeanMapper moniterMapper; 

	@Autowired
	EquipBeanMapper equipBeanMapper;
	
	
	public List<PileEquipBean> selectByPage( int start, int limit){
		List<PileEquipBean> list = new ArrayList<PileEquipBean>();
		PileEquipBeanExample example = new PileEquipBeanExample();
		MultipleDataSource.setDataSourceKey("dataSource");
		list = mapper.selectByExample(example, new RowBounds(start,limit));
		
		List<PileEquipBean> ls = new ArrayList<PileEquipBean>();
		MultipleDataSource.setDataSourceKey("sqlServerDataSource");
		Map<String,String> map = new HashMap<String,String>();
		List<EquipMoniterBean> mon= moniterMapper.getEquipStatus("双向搅拌桩机");
		for(EquipMoniterBean m:mon){
			map.put(m.getEquipNo(), m.getStatus());
		}
		MultipleDataSource.setDataSourceKey("dataSource");
		for(PileEquipBean bean:list){
			if(!map.containsKey(bean.getEquipmentCode().trim())){
				bean.setEquipmentStatus("离线");
			}else{
				bean.setEquipmentStatus(map.get(bean.getEquipmentCode()));
			}
			ls.add(bean);
		}
		return ls;
	}
	
	public int count(){
		PileEquipBeanExample example = new PileEquipBeanExample();
		return (int) mapper.countByExample(example);
	}
	
	public int add(PileEquipBean bean){
		EquipBean equip = new EquipBean();
		MultipleDataSource.setDataSourceKey("sqlServerDataSource");
		equip.setEquipmentCode(bean.getEquipmentCode());
		equip.setEquipmentName(bean.getEquipmentName());
		equip.setEquipmentType("双向搅拌桩机");
		int add = equipBeanMapper.addEquip(equip);
		MultipleDataSource.setDataSourceKey("dataSource");
		add += mapper.insert(bean);
		return add;
	}
	
	public int modify(PileEquipBean bean){
		return mapper.updateById(bean);
	}
	
	public PileEquipBean load(String id){
		return mapper.selectById(id);
	}
	
	public int del(String id){
		return mapper.deleteById(id);
	}
}
