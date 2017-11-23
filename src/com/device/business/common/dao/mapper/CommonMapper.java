package com.device.business.common.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import com.device.common.KVBean;

public interface CommonMapper {
	List<KVBean> selectKV(@Param("sql")String sql);
}
