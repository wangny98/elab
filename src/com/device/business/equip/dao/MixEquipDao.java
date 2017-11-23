package com.device.business.equip.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.equip.bean.EquipBean;
import com.device.business.equip.bean.MixEquipBean;
import com.device.business.equip.bean.MixEquipBeanExample;
import com.device.business.equip.dao.mapper.EquipBeanMapper;
import com.device.business.equip.dao.mapper.MixEquipBeanMapper;
import com.device.business.moniter.bean.EquipMoniterBean;
import com.device.business.moniter.dao.mapper.EquipMoniterBeanMapper;
import com.device.component.datasource.MultipleDataSource;

public class MixEquipDao {

	@Autowired
	private MixEquipBeanMapper mapper;
	
	@Autowired
	EquipMoniterBeanMapper moniterMapper; 
	
	@Autowired
	EquipBeanMapper equipBeanMapper;
	
	public List<MixEquipBean> selectByPage( int start, int limit){
		List<MixEquipBean> list = new ArrayList<MixEquipBean>();
		MixEquipBeanExample example = new MixEquipBeanExample();
		MultipleDataSource.setDataSourceKey("dataSource");
		list = mapper.selectByExample(example, new RowBounds(start,limit));
		
		List<MixEquipBean> ls = new ArrayList<MixEquipBean>();
		MultipleDataSource.setDataSourceKey("sqlServerDataSource");
		Map<String,String> map = new HashMap<String,String>();
		List<EquipMoniterBean> mon= moniterMapper.getEquipStatus("自动制浆站");
		for(EquipMoniterBean m:mon){
			map.put(m.getEquipNo(), m.getStatus());
		}
		MultipleDataSource.setDataSourceKey("dataSource");
		for(MixEquipBean bean:list){
			if(!map.containsKey(bean.getEquipmentCode())){
				bean.setEquipmentStatus("离线");
			}else{
				bean.setEquipmentStatus(map.get(bean.getEquipmentCode()));
			}
			ls.add(bean);
		}
		return list;
	}
	
	public int count(){
		MixEquipBeanExample example = new MixEquipBeanExample();
		return (int) mapper.countByExample(example);
	}
	
	public List<MixEquipBean> selectBySectionId(String sectionId){
		return mapper.selectBySectionId(sectionId);
	}
	
	public int add(MixEquipBean bean){
		EquipBean equip = new EquipBean();
		MultipleDataSource.setDataSourceKey("sqlServerDataSource");
		equip.setEquipmentCode(bean.getEquipmentCode());
		equip.setEquipmentName(bean.getEquipmentName());
		equip.setEquipmentType("自动制浆站");
		int add = equipBeanMapper.addEquip(equip);
		MultipleDataSource.setDataSourceKey("dataSource");
		add += mapper.insert(bean);
		return add;
	}
	
	public int modify(MixEquipBean bean){
		return mapper.updateById(bean);
	}
	
	public MixEquipBean load(String id){
		return mapper.selectById(id);
	}
	
	public int del(String id){
		return mapper.deleteById(id);
	}
}
