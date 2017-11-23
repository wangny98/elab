package com.device.business.scrapManager.dao.mapper;

import java.util.ArrayList;
import java.util.Date;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.device.business.scrapManager.bean.ScrapBean;
import com.device.business.transferManager.bean.BasePropertyBean;

public interface ScrapMapper {
    /**********************报废操作**********************/
    ArrayList<ScrapBean> query(@Param("searchName") String searchName, RowBounds rowBounds,
            @Param("states") String states);

    int count(@Param("searchName") String searchName, @Param("states") String states);

    ScrapBean load(@Param("id") String id);

    int add(@Param("bean") ScrapBean bean);

    int update(@Param("bean") ScrapBean bean);

    int addUnion(@Param("scrap_id") String scrap_id, @Param("table_name") String table_name,
            @Param("property_id") String property_id);
    
    int delunion(@Param("scrap_id") String scrap_id);

    /**********************报废资产操作***********************************/
    ArrayList<BasePropertyBean> listBaseProperty(@Param("searchName") String searchName, RowBounds rowBounds);

    int countBaseProperty(@Param("searchName") String searchName);

    ArrayList<String> getPropertyConcat(@Param("scrap_id") String scrap_id);

    ArrayList<BasePropertyBean> getCheckBaseProperty(@Param("ids") String ids);

    int countCheckBaseProperty(@Param("scrap_id") String scrap_id);

    /*****************************报废资产状态更新*************************************/
    int updateScrapState(@Param("scrap_id") String scrap_id, @Param("state") String state);
    
    int updateExamineInfo(@Param("scrap_id") String scrap_id, @Param("name") String name);

    int updatePropertyStatue4Basic(@Param("ids") String ids, @Param("status") int status);

    int updatePropertyStatue4Instr(@Param("ids") String ids, @Param("status") int status);

    int updatePropertyStatue4House(@Param("ids") String ids, @Param("status") int status);
    
    int updatePropertyScrapTime(@Param("ids") String ids,@Param("scrapData") Date scrapData);

    int updateScrapTime(@Param("scrap_id") String scrap_id,@Param("scrapTime") String scrapTime, @Param("data") Date data);
}
