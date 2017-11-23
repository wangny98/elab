package com.device.business.systemSet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.systemSet.bean.UnitBean;

public interface UnitMapper {
	List<UnitBean> listUnit(@Param("name")String name, RowBounds rowBounds);
	
	UnitBean getUnit(@Param("id")String id);
	
	int addUnit(@Param("bean")UnitBean bean);
	
	int updUnit(@Param("bean")UnitBean bean);
	
	int delUnit(@Param("id")String id);
	
	List<OrgTreeBean> getNodes();
}
