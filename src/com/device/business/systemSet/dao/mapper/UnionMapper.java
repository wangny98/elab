package com.device.business.systemSet.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.device.business.systemSet.bean.UnionBean;


public interface UnionMapper {
	public int addUnion(@Param("bean")UnionBean bean);
	
	public int delUnion(@Param("childId")String  childId);
}
