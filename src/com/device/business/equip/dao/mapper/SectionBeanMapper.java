package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.SectionBean;
import com.device.business.equip.bean.SectionBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface SectionBeanMapper {
    
    long countByExample(SectionBeanExample example);

   
    int deleteById(@Param("id") String id);

    
    int insert(SectionBean record);

    List<SectionBean> selectByExample(SectionBeanExample example,RowBounds rowBunds);
    
    SectionBean selectById(@Param("id") String id);
   
    int updateById(@Param("record") SectionBean record);
    
    List<SectionBean> getUserACL(@Param("user_id") String user_id);
    
    int deleteUserACL(@Param("user_id") String user_id);
    
    int addUserACL(@Param("user_id") String user_id,@Param("resource_id") String resource_id);
    
    List<String> getCheckedACL(@Param("user_id") String user_id);
}