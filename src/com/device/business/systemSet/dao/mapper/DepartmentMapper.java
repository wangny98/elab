package com.device.business.systemSet.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.systemSet.bean.DepartmentBean;

public interface DepartmentMapper {
	List<DepartmentBean> listDepartment(@Param("name")String name, RowBounds rowBounds);
	
	DepartmentBean getDepartment(@Param("id")String id);
	
	int addDepartment(@Param("bean")DepartmentBean bean);
	
	int updDepartment(@Param("bean")DepartmentBean bean);
	
	int delDepartment(@Param("id")String id);
	
	List<OrgTreeBean> getNodes();
}
