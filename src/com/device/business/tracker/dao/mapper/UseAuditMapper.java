package com.device.business.tracker.dao.mapper;

import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.device.business.tracker.bean.UseAuditBean;
import com.device.business.tracker.element.UseAuditElement;

/**
 * 
 * 业务操作跟踪接口
 * 
 * @author  guoke
 * @version  [版本号, 2013-8-7]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface UseAuditMapper {

    /**
     * 查询所有的业务操作,并分页
     * @param bean
     * @param rows
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UseAuditElement> queryAudits_ZH(com.device.business.tracker.element.UseAuditElement bean, RowBounds rows);

    /**
     * 查询所有的业务操作
     * @param bean
     * @param rows
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UseAuditElement> queryAudits_ZH(UseAuditElement bean);

    /**
     * 查询所有的业务操作,并分页
     * @param bean
     * @param rows
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UseAuditElement> queryAudits_EN(UseAuditElement bean, RowBounds rows);

    /**
     * 查询所有的业务操作,并分页
     * @param bean
     * @param rows
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<UseAuditElement> queryAudits_EN(UseAuditElement bean);

    /**
     * 查询满足条件的业务操作总数
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    Integer countAudits_ZH(UseAuditElement bean);

    /**
     * 查询满足条件的业务操作总数
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    Integer countAudits_EN(UseAuditElement bean);

    /**
     * 添加业务操作
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    int addAudit(UseAuditBean bean);

    /**
     * 根据主键获取业务操作(中文)
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    UseAuditElement infoUseAuditById_ZH(String id);

    /**
     * 根据主键获取业务操作(英文)
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    UseAuditElement infoUseAuditById_EN(String id);

    /**
     * 获取电子文档的业务操作
     * <功能详细描述>
     * @param docId 电子文档的主键
     * @param rows 分页工具
     * @return 电子文档的业务操作
     * @see [类、类#方法、类#成员]
     */
    List<UseAuditElement> queryDocAuditLogs_ZH(String docId, RowBounds rows);

    /**
     * 获取电子文档的业务操作总数
     * @param docId 电子文档的主键
     * @return 电子文档的业务操作总数
     * @see [类、类#方法、类#成员]
     */
    Integer countDocAuditLogs_ZH(String docId);
}
