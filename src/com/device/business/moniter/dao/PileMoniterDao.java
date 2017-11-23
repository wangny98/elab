package com.device.business.moniter.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.moniter.bean.PileMoniterBean;
import com.device.business.moniter.dao.mapper.PileMoniterBeanMapper;
import com.device.component.datasource.MultipleDataSource;

public class PileMoniterDao {
	@Autowired
	PileMoniterBeanMapper mapper;
	
	public List<PileMoniterBean> select(String pileDriverNumber){
		List<PileMoniterBean> list = new ArrayList<PileMoniterBean>();
		//MultipleDataSource.setDataSourceKey("sqlServerDataSource");
		MultipleDataSource.setDataSourceKey("dataSource");
		list = mapper.select(pileDriverNumber);
		MultipleDataSource.setDataSourceKey("dataSource");
		return list;
	}
	
}
