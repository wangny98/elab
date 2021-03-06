package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.SlurryPerdayBean;
import com.device.business.equip.bean.SlurryPerdayBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface SlurryPerdayBeanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    long countByExample(SlurryPerdayBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    int deleteByExample(SlurryPerdayBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    int insert(SlurryPerdayBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    int insertSelective(SlurryPerdayBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    List<SlurryPerdayBean> selectByExample(SlurryPerdayBeanExample example);
    
    List<SlurryPerdayBean> selectSlurry(@Param("equipmentId")String equipmentId,
    		@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    int updateByExampleSelective(@Param("record") SlurryPerdayBean record, @Param("example") SlurryPerdayBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_slurry_perday
     *
     * @mbg.generated Sat Jan 14 13:41:07 CST 2017
     */
    int updateByExample(@Param("record") SlurryPerdayBean record, @Param("example") SlurryPerdayBeanExample example);
}