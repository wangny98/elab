package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.MixEquipBean;
import com.device.business.equip.bean.MixEquipBeanExample;
import com.device.business.equip.bean.PileEquipBean;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface MixEquipBeanMapper {
    
    long countByExample(MixEquipBeanExample example);
    List<MixEquipBean> selectByExample(MixEquipBeanExample example,RowBounds rowBounds);

    List<MixEquipBean> selectBySectionId(@Param("sectionId")String sectionId);
    
    
    int insert(MixEquipBean record);
    
    MixEquipBean selectById(@Param("id")String id);
    
    int updateById(@Param("record") MixEquipBean record);
    
    int deleteById(@Param("id")String id);
}