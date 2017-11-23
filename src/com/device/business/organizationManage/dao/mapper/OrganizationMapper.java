package com.device.business.organizationManage.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.organizationManage.bean.OrgTreeBean;
import com.device.business.organizationManage.bean.OrganizationBean;

public interface OrganizationMapper {

	ArrayList<OrganizationBean> queryOrganization(String searchName, RowBounds rowBounds);
	
	int countOrganization(String searchName);
	
	int addOrganization(@Param("bean")OrganizationBean bean);
	
	int addOrgUnion(@Param("id")String id,@Param("parent_id")String parent_id,@Param("child_id")String child_id);
	
	int updateOrganization(@Param("bean")OrganizationBean bean);
	
	OrganizationBean loadOrganization(@Param("id")String id);
	
	int deleteOrganization(@Param("id")String id);
	
	int deleteOrgUnion(@Param("id")String id);
	
	ArrayList<OrgTreeBean> listAllTreeNode();
	
	String queryDeptCode(String id);
}
