package com.device.business.moniter.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.device.business.moniter.bean.EquipMoniterBean;

//import com.device.business.moniter.bean.EquipMoniterBean;

public interface EquipMoniterBeanMapper {
	List<EquipMoniterBean> getEquipStatus(@Param("type")String type);
}
