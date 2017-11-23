 package com.device.business.report.dao.mapper;

import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.device.business.report.bean.CellBean;

public interface PropertyReportMapper {

	List<CellBean> groupPropertyByclassification(@Param("lastDate")Date lastDate);
	
	List<CellBean> groupPropertyByclassificationAdd(@Param("startDate")Date startDate,@Param("endDate")Date endDate);

	List<CellBean> groupPropertyByclassificationDel(@Param("startDate")Date startDate,@Param("endDate")Date endDate);
	
	
	List<CellBean> groupPropertyByUse(@Param("lastDate")Date lastDate);
	
	//资产总计
	CellBean allPropertyFirst(@Param("lastDate")Date lastDate);
	
}
