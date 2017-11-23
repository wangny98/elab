package com.device.business.dictionary.dao.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.device.business.dictionary.bean.DictionaryBean;

/**
 * 
 * 用Mybatis操作DictionaryBean类的接口
 * 
 * @author  盛文
 * @version  [v1.0, 2012-9-4]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public interface DictionaryMapper {

    /**
     * 从数据库中查询出对应Name的列表
     * @param
     * @param start
     * @param limit
     * @return
     */
    List<DictionaryBean> queryDictionaryByName(String queryDictionaryName, RowBounds rowBounds);

    /**
     * 从数据库中查询出对应Name的总个数
     * @param 
     * @param start
     * @param limit
     * @return
     */
    List<DictionaryBean> queryDictionaryByName(String queryDictionaryName);

    /**
     * 从数据库中查询出对应Name的总个数
     * @param 
     * @param start
     * @param limit
     * @return
     */
    List<Integer> queryDictionaryCountByName(String queryDictionaryName);

    /**
     * 从数据库中查询
     * @return
     */
    List<DictionaryBean> listDictionaryAttribute();

    /**
     * 从数据库中查询
     * @return
     */
    List<DictionaryBean> listDictionaryByAttribute(String attribute);

    /**
     * 从数据库中查询出对应
     * @param 
     * @param start
     * @param limit
     * @return
     */
    List<DictionaryBean> queryDetailDictionaryByName(Map<String, Object> params);

    /**
     * 
     * 根据属性从数据库中查询对象
     * 
     * @param attribute
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DictionaryBean> queryDictionaryByAttribute(String attribute);

    /**
     * 
     * 根据属性从数据库中查询对象
     * 
     * @param attribute
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<DictionaryBean> queryNormalDictionaryByAttribute(String attribute);

    /**
     * 增加到数据库中
     * @param 
     * @return 
     */
    int addDictionary(DictionaryBean dictionaryBean);

    /**
     * 从数据库中读取对应Id的信息
     * @param 
     * @return 类
     */
    DictionaryBean queryDictionaryById(String dictionaryId);

    /**
     * 更新数据库中对应的信息
     * @param 
     * @return 
     */
    int updateDictionaryById(DictionaryBean dictionaryBean);

    /**
     * 根据的ID删除信息
     * @param 
     * @return 
     */
    int deleteDictionaryById(String id);

    /**
     *查找对应的信息
     * @param name 
     * @return 
     */
    int checkDictionaryByKey(Map<String, Object> params);

    int checkDictionaryByValue(Map<String, Object> params);

    /**
     * 
     * 获取所有数据字典属性
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> queryDictionaryByGroup();

    /**
     * 
     * 获取所有常规数据字典属性
     * 
     * @return
     * @see [类、类#方法、类#成员]
     */
    List<String> queryNormalDictionaryByGroup();

    /**
     *查找对应的信息
     * @param name 
     * @return 
     */
    int checkDictionaryByDescription(Map<String, Object> params);

    /**
     *查找对应的信息
     * @param name 
     * @return 
     */
    List<DictionaryBean> queryAttributeName(String attribute);

    /**
     *查找对应的信息
     * @param name 
     * @return 
     */
    List<DictionaryBean> queryAllDictionary();

    /**
     * 通过属性和属性值得到数据字典
     * @param params
     * @return
     * @see [类、类#方法、类#成员]
     */
    DictionaryBean getValueByAttributeValue(Map<String, Object> params);

}
