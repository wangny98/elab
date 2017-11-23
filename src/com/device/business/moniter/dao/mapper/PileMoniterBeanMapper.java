package com.device.business.moniter.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.device.business.moniter.bean.PileMoniterBean;

public interface PileMoniterBeanMapper {
    
	List<PileMoniterBean> select(@Param("pileDriverNumber")String pileDriverNumber);
}