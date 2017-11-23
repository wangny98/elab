package com.device.business.classificationManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.classificationManager.bean.ClassTreeBean;
import com.device.business.classificationManager.bean.ClassificationBean;
import com.device.business.organizationManage.bean.OrganizationBean;

public interface ClassificationMapper {
	
	int updateClassification(@Param("bean")ClassificationBean bean);
	
	ArrayList<ClassificationBean> queryClassification(String searchName, RowBounds rowBounds);
	
	int countClassification(String searchName);

	ArrayList<ClassTreeBean> listAllTreeNode();
	
	int addClassification(@Param("bean")ClassificationBean bean);

	int addClaUnion(@Param("id")String id,@Param("parent_id")String parent_id,@Param("child_id")String child_id);

	ClassificationBean loadClassification(@Param("id")String id);
	
	int deleteClassification(@Param("id")String id);
	
	int deleteClaUnion(@Param("id")String id);
	
	String queryClassCode(String id);
	
	String queryClassName(String id);
}
