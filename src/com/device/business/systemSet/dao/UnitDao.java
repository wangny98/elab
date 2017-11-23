package com.device.business.systemSet.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.systemSet.bean.UnitBean;
import com.device.business.systemSet.dao.mapper.UnitMapper;
import com.device.util.PrimaryKeyUtil;

public class UnitDao {
	@Autowired
	private UnitMapper unitMapper;
	
	@Autowired
	private UnionDao unionDao;
	
	public List<UnitBean> list(String name,int start,int limit){
		List<UnitBean> list = new ArrayList<UnitBean>();
		RowBounds rowBounds = new RowBounds(start, limit);
		list = unitMapper.listUnit("%"+name+"%", rowBounds);
		return list;
	}
	
	public UnitBean get(String id){
		return unitMapper.getUnit(id);
	}
	
	@Transactional
	public int add(UnitBean bean,String parentId){
		String id = PrimaryKeyUtil.getSeq();
		unionDao.addUnion(parentId, id);
		bean.setId(id);
		return unitMapper.addUnit(bean);
	}
	
	public int modify(UnitBean bean){
		return unitMapper.updUnit(bean);
	}
	
	@Transactional
	public int delete(String id){
		unionDao.delUnion(id);
		return unitMapper.delUnit(id);
	}
}
