package com.device.business.transferManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.transferManager.bean.ExamineBean;


public interface ExamineMapper {

	
ArrayList<ExamineBean> query(@Param("searchName")String searchName, RowBounds rowBounds);
	
	int count(@Param("searchName")String searchName);
	
	int add(@Param("bean")ExamineBean bean);
	
	int update(@Param("bean")ExamineBean bean);
	
	ExamineBean load(@Param("id")String id);
	
	int delete(@Param("id")String id);
}
