package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.PperdeepBean;
import com.device.business.equip.bean.PperdeepBeanExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PperdeepBeanMapper {
    int countByExample(PperdeepBeanExample example);

    int deleteByExample(PperdeepBeanExample example);

    int deleteByPrimaryKey(String id);

    int insert(PperdeepBean record);

    int insertSelective(PperdeepBean record);
    
    List<Map<String,Object>> selectMachineInfo(@Param("Pile_Number")String pileNumber,RowBounds rowBounds);

    List<PperdeepBean> selectByExample(PperdeepBeanExample example);
    
    List<PperdeepBean> selectByABS(PperdeepBeanExample example);
    
    List<PperdeepBean> selectAll();

    PperdeepBean selectByPrimaryKey(String id);

    int updateByExampleSelective(@Param("record") PperdeepBean record, @Param("example") PperdeepBeanExample example);

    int updateByExample(@Param("record") PperdeepBean record, @Param("example") PperdeepBeanExample example);

    int updateByPrimaryKeySelective(PperdeepBean record);

    int updateByPrimaryKey(PperdeepBean record);
}