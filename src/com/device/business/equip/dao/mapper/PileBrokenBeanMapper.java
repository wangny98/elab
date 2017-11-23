package com.device.business.equip.dao.mapper;

import com.device.business.equip.bean.PileBrokenBean;
import com.device.business.equip.bean.PileBrokenBeanExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface PileBrokenBeanMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    long countByExample(PileBrokenBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    int deleteByExample(PileBrokenBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    int insert(PileBrokenBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    int insertSelective(PileBrokenBean record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    List<PileBrokenBean> selectByExample(PileBrokenBeanExample example,RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    int updateByExampleSelective(@Param("record") PileBrokenBean record, @Param("example") PileBrokenBeanExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_luding_pile_broken_info
     *
     * @mbg.generated Fri Jan 06 20:24:15 CST 2017
     */
    int updateByExample(@Param("record") PileBrokenBean record, @Param("example") PileBrokenBeanExample example);
}