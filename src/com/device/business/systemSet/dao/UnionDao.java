package com.device.business.systemSet.dao;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.systemSet.bean.UnionBean;
import com.device.business.systemSet.dao.mapper.UnionMapper;
import com.device.util.PrimaryKeyUtil;

public class UnionDao {
	
	@Autowired
	private UnionMapper unionMapper;
	
	public int addUnion(String parentId,String childId){
		UnionBean bean = new UnionBean();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setParent_id(parentId);
		bean.setChild_id(childId);
		return unionMapper.addUnion(bean);
	}
	
	public int delUnion(String childId){
		return unionMapper.delUnion(childId);
	}
}
