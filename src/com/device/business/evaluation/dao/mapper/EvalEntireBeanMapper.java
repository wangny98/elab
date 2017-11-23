package com.device.business.evaluation.dao.mapper;

import com.device.business.evaluation.bean.EvalEntireBean;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface EvalEntireBeanMapper {
    
	List<EvalEntireBean> selectByEvalId(@Param("evalId")Integer evalId);
	
    int insert(EvalEntireBean record);

    int updateById(@Param("record")EvalEntireBean record);


}