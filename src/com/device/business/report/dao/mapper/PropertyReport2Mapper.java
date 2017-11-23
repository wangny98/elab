package com.device.business.report.dao.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.device.business.report.bean.CellBean;

public interface PropertyReport2Mapper {
	
	//行：年出固定资产、年末固定资产 列：原值（非其他）、合计
	CellBean propertyChange1(@Param("lastDate")Date lastDate,@Param("className")String className);
	
	//行：年出固定资产、年末固定资产 列：数量（非其他）
	CellBean propertyChange2(@Param("lastDate")Date lastDate,@Param("className")String className);
	
	//行：年出固定资产、年末固定资产 列：原值（其他）
	CellBean propertyChange3(@Param("lastDate")Date lastDate,@Param("classNames")String classNames);
	
	//行：年出固定资产、年末固定资产 列：数量（其他）
	CellBean propertyChange4(@Param("lastDate")Date lastDate,@Param("classNames")String classNames);
	
	//////////////////
	
	//行：本年度增加固定资产 列：原值（非其他）、合计
	List<CellBean> propertyChange5(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("className")String className);
	
	//行：本年度增加固定资产 列：原值（其他）
	CellBean propertyChange6(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("classNames")String classNames);
	
	//行：本年度增加固定资产 列：数量（非其他）
	CellBean propertyChange7(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("className")String className);
	
	//行：本年度增加固定资产 列：数量（其他）
	CellBean propertyChange8(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("classNames")String classNames);
	
	//////////////////
	//行：本年度增加固定资产List 列：原值
	List<CellBean> propertyChange9(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("className")String className);
	
	//行：本年度增加固定资产List 列：原值（其他）
	List<CellBean> propertyChange10(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("classNames")String classNames);
	
	//行：本年度增加固定资产List 列：数量
	List<CellBean> propertyChange11(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("className")String className);
	
	//行：本年度增加固定资产List 列：数量（其他）
	List<CellBean> propertyChange111(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("classNames")String classNames);
	
	//行：年出固定资产、年末固定资产  列：房屋面积
	CellBean propertyChange12(@Param("lastDate")Date lastDate);
	
	//行：本年度增加固定资产  列：房屋面积
	CellBean propertyChange13(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//行：本年度增加固定资产List  列：房屋面积
	List<CellBean> propertyChange14(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//行：年出固定资产、年末固定资产  列：土地面积
	CellBean propertyChange15(@Param("lastDate")Date lastDate);
	
	//行：本年度增加固定资产  列：土地面积
	CellBean propertyChange16(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	//行：本年度增加固定资产List  列：土地面积
	List<CellBean> propertyChange17(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	
	//行：报废 列：总计
	CellBean propertyChange18(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	//行：报废 列：土地面积
	CellBean propertyChange19(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	//行：报废 列：房屋面积
	CellBean propertyChange20(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	//行：报废 列：数量
	CellBean propertyChange21(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("name")String name);
	//行：报废 列：原值
	CellBean propertyChange22(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("name")String name);
	//行：报废 列：数量（其他）
	CellBean propertyChange23(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("names")String names);
	//行：报废 列：原值（其他）
	CellBean propertyChange24(@Param("startDate")Date startDate,@Param("endDate")Date endDate,@Param("names")String names);
	
}
