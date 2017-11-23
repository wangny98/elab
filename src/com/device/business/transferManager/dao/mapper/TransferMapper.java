package com.device.business.transferManager.dao.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.business.transferManager.bean.TransferBean;

public interface TransferMapper {

	ArrayList<TransferBean> query(@Param("searchName")String searchName, RowBounds rowBounds,@Param("states")String states);
	
	int count(@Param("searchName")String searchName,@Param("states")String states);
	
	int add(@Param("bean")TransferBean bean);
	
	int update(@Param("bean")TransferBean bean);
	
	TransferBean load(@Param("id")String id);
	
	int delete(@Param("id")String id);
	
	
	int addUnion(@Param("transfer_id")String transfer_id,@Param("table_name")String table_name,
			@Param("property_id")String property_id);
	/******************************************************************/
	ArrayList<BasePropertyBean> listBaseProperty(@Param("searchName")String searchName, RowBounds rowBounds);
	
	
	
	int countBaseProperty(@Param("searchName")String searchName);
	
	ArrayList<String> getPropertyConcat(@Param("transfer_id")String transfer_id,RowBounds rows);
	ArrayList<String> getPropertyConcatAll(@Param("transfer_id")String transfer_id);
	
	ArrayList<BasePropertyBean> getCheckBaseProperty(@Param("ids")String ids);
	
	int countCheckBaseProperty(@Param("transfer_id")String transfer_id);
	/******************************************************************/
	int updateTransferState(@Param("transfer_id")String transfer_id,@Param("state")String state);
	
	int updateExamineInfo(@Param("transfer_id")String transfer_id,@Param("date")Date date,@Param("name")String name);
	
	int updatePropertyStatue4Basic(@Param("ids")String ids,@Param("status")int status);
	
	int updatePropertyStatue4Instr(@Param("ids")String ids,@Param("status")int status);
	
	int updatePropertyStatue4House(@Param("ids")String ids,@Param("status")int status);
	
}
