package com.device.business.docManager.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Param;

import com.device.business.docManager.bean.ContentNodeBean;

/**
 * 
 * 目录模板模块
 * 
 * @author  周晶
 * @version  [版本号, 2013-11-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface ContentManagerMapper {

    /**
     * 获取树节点
     * <功能详细描述>
     * @param typeId
     * @return
     * @see [类、类#方法、类#成员]
     */
    ArrayList<ContentNodeBean> listContentNode(@Param("typeIds") String typeIds);

    /**
     * 查看节点
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    ContentNodeBean infoContentNode(@Param("id") String id);

    ContentNodeBean queryNodeByPath(@Param("path") String path);

    /**
     * 新增节点
     * <功能详细描述>
     * @param bean
     * @return
     * @see [类、类#方法、类#成员]
     */
    int addContentNode(@Param("bean") ContentNodeBean bean);

    /**
     * 修改节点
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    int updContentNode(@Param("bean") ContentNodeBean bean);

    /**
     * 删除节点
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    int delContentNode(@Param("id") String id);
}
