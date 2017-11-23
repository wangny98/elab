package com.device.business.common.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.common.dao.mapper.CommonMapper;
import com.device.common.KVBean;

public class CommonDao {
	@Autowired
	CommonMapper mapper;
	
	public List<KVBean> selectKV(String sql){
		return mapper.selectKV(sql);
	}
}
