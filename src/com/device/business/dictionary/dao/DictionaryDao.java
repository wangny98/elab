package com.device.business.dictionary.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.agile.erms.utils.CommUtils;
import com.device.business.dictionary.bean.DictionaryBean;
import com.device.business.dictionary.dao.mapper.DictionaryMapper;
import com.device.business.login.element.LoginElement;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class DictionaryDao {
    @Autowired
    DictionaryMapper dictionaryMapper;

    /**
     * 记录当前登录状态
     */
    LoginElement loginElement;

    /**
     * 根据名称查询
     * @param queryJobName
     * @param rows
     * @return
     */
    public List<DictionaryBean> queryDictionary(String queryDictionaryName, RowBounds rows) {

        return dictionaryMapper.queryDictionaryByName(CommUtils.convertStringToBase(queryDictionaryName), rows);

    }

    public List<DictionaryBean> queryDetailDictionary(String attributeDic, String attributeNameDic) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attributeDic", attributeDic);
        params.put("attributeNameDic", attributeNameDic);

        return dictionaryMapper.queryDetailDictionaryByName(params);
    }

    /**
     * 根据名称查询的总个数
     * @param queryJobName
     * @return
     */
    public int queryDictionaryCount(String queryDictionaryName) {

        List<Integer> list = dictionaryMapper.queryDictionaryCountByName(CommUtils
                .convertStringToBase(queryDictionaryName));
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    /**
     * 增加
     * @param jobBean
     * @return
     */
    @Transactional
    public Boolean addDictionary(DictionaryBean dictionaryBean) {
        loginElement = AuthorityContext.getLoginElement();
        //从session中获取当前登录用户的id
        String sessionUserId = loginElement.getUserId();
        //从session中获取当前登录用户的姓名
        String sessionUserName = loginElement.getUserFullName();

        dictionaryBean.setId(PrimaryKeyUtil.getSeq());
        dictionaryBean.setDeleteflag(1);
        dictionaryBean.setCreate_time(new Date());
        /***此处创建人id和name待用户模块加上后，再获取当前用户信息***/

        dictionaryBean.setCreator_id(sessionUserId);
        dictionaryBean.setCreator_name(sessionUserName);
        return dictionaryMapper.addDictionary(dictionaryBean) > 0 ? true : false;

    }

    /**
     * 显示岗位
     * @param JobId
     * @return
     */
    public DictionaryBean displayDictionary(String dictionaryId) {
        return dictionaryMapper.queryDictionaryById(dictionaryId);
    }

    /**
     * 修改
     * @param jobBean
     * @return
     */
    @Transactional
    public boolean updateDictionary(DictionaryBean dictionaryBean) {
        dictionaryBean.setDeleteflag(1);
        return dictionaryMapper.updateDictionaryById(dictionaryBean) > 0 ? true : false;

    }

    /**
     * 删除
     * @param toDeleteIds 要删除的各个id（以,号隔开）
     * @return   返回删除是否成功的标志
     */
    @Transactional
    public boolean deleteDictionary(String deleteIds) {
        //获取以“,”隔开的id列表
        String[] ids = CommUtils.getIdFromString(deleteIds);
        int length = ids.length;

        //遍历根据Id删除用户信息
        for (int i = 0; i < length; i = i + 1) {
            //若删除异常，则返回失败
            if (dictionaryMapper.deleteDictionaryById(ids[i]) <= 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * 检查角色名是否重复
     * @param RoleId
     * @return
     */
    public int checkDictionaryByKey(String attribute, String attributeKey) {
        //用于mybatis参数映射，HashMap
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attr_name", attribute);
        params.put("attr_key", attributeKey);

        return dictionaryMapper.checkDictionaryByKey(params);
    }

    public int checkDictionaryByValue(String attribute, String attributeValue) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attr_name", attribute);
        params.put("attr_value", attributeValue);
        return dictionaryMapper.checkDictionaryByValue(params);
    }

    /**
     * 检查角色名是否重复
     * @param RoleId
     * @return
     */
    public int checkDictionaryByDescription(String attribute, String description) {
        //用于mybatis参数映射，HashMap
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attribute", attribute);
        params.put("description", description);

        return dictionaryMapper.checkDictionaryByDescription(params);
    }

    /**
     * 
     * 按数据字典属性查找属性名
     * 
     * @see [类、类#方法、类#成员]
     */
    public List<DictionaryBean> queryAttributeName(String attribute) {
        return dictionaryMapper.queryAttributeName(attribute);
    }

    /**
     * 根据名称查询岗位的总个数
     * @param queryJobName
     * @return
     */
    public List<DictionaryBean> listDictionaryAttribute() {

        return dictionaryMapper.listDictionaryAttribute();

    }

    public List<DictionaryBean> queryAllDictionary() {
        return dictionaryMapper.queryAllDictionary();
    }

    /**
     * 根据属性和属性值得到描述
     * @param attribute
     * @param attributeValue
     * @return
     * @see [类、类#方法、类#成员]
     */
    public String getValueByAttributeValue(String attribute, Integer attributeValue) {
        //用于mybatis参数映射，HashMap
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("attribute", attribute);
        params.put("attributeValue", attributeValue);

        DictionaryBean dictionaryBean = dictionaryMapper.getValueByAttributeValue(params);
        if (null != dictionaryBean) {
            return dictionaryBean.getAttr_value();
        } else {
            return "";
        }
    }

    public List<DictionaryBean> getDictionaryBeanList(String attribute) {
        return dictionaryMapper.listDictionaryByAttribute(attribute);
    }
}
