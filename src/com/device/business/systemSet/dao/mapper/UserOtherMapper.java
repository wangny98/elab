package com.device.business.systemSet.dao.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.device.business.systemSet.bean.UserBean;

public interface UserOtherMapper {
	List<UserBean> listUser(@Param("name")String name);
	
	int addUser(@Param("bean")UserBean bean);
	
	int updUser(@Param("bean")UserBean bean);
	
	int delUser(@Param("id")String id);
}
