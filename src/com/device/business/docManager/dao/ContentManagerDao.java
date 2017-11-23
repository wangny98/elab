package com.device.business.docManager.dao;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.docManager.bean.ContentNodeBean;
import com.device.business.docManager.dao.mapper.ContentManagerMapper;
import com.device.business.docManager.dao.mapper.FileManagerMapper;
import com.device.business.docManager.dao.mapper.ScienceManageMapper;
import com.device.business.login.element.LoginElement;
import com.device.constant.DmConstants;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

/**
 * 目录模板
 * <功能详细描述>
 * 
 * @author  周晶
 * @version  [版本号, 2013-11-23]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ContentManagerDao {

    @Autowired
    private FileManagerMapper fileManagerMapper;

    @Autowired
    private ContentManagerMapper contentManagerMapper;

    @Autowired
    ScienceManageMapper scienceManageMapper;

    /**
     * 获取模板树
     * <功能详细描述>
     * @param id
     * @return
     * @see [类、类#方法、类#成员]
     */
    public ContentNodeBean infoContentNode(String id) {
        return contentManagerMapper.infoContentNode(id);
    }

    //新增
    public int addContentNode(ContentNodeBean bean) throws IOException {
        bean.setId(PrimaryKeyUtil.getSeq());
        LoginElement LoginElement = AuthorityContext.getLoginElement();
        bean.setCreatorId(LoginElement.getUserId());
        bean.setCreatorName(LoginElement.getUserFullName());
        bean.setCreatorTime(new Date());

        String typePath = null;
        if (bean.getTypeId().equals("1")) {
            typePath = DmConstants.scienceDir;
        } else if (bean.getTypeId().equals("2")) {
            typePath = DmConstants.noteDir;
        } else if (bean.getTypeId().equals("3")) {
            typePath = DmConstants.cnsDir;
        }
        File parent = FileUtils.getFile(DmConstants.docDir + typePath);
        File file = FileUtils.getFile(parent, bean.getId());
        FileUtils.forceMkdir(file);
        bean.setPath(file.getPath());
        if (file == null) {
            return 0;
        }
        return contentManagerMapper.addContentNode(bean);
    }

    //重命名
    public int updContentNode(ContentNodeBean bean) {
        return contentManagerMapper.updContentNode(bean);
    }

    //删除
    @Transactional
    public int delContentNode(String id) throws IOException {
        ContentNodeBean bean = contentManagerMapper.infoContentNode(id);
        File f = new File(bean.getPath());
        if (f != null && f.exists()) {
            File file = FileUtils.getFile(bean.getPath());
            if (file == null) {
            } else {
                FileUtils.forceDelete(file);
            }
        }
        if (bean.getTypeId() != null && bean.getTypeId().equals("1")) {//科研论文
            scienceManageMapper.deleteMore(id);
        }
        fileManagerMapper.delFiles(id);
        return contentManagerMapper.delContentNode(id);
    }

    public List<ContentNodeBean> getTree(String typeIds, String uiProvider) {
        String types = "(" + typeIds + ")";
        List<ContentNodeBean> list = contentManagerMapper.listContentNode(types);
        if (list.size() > 0) {
            ContentNodeBean root = new ContentNodeBean();
            root.setId("0");
            root.setLeaf(false);
            this.buildTree(root, list, uiProvider);
            return root.getChildren();
        } else {
            return null;
        }
    };

    /**
     * 构建树
     * <功能详细描述>
     * @param bean
     * @param list
     * @param uiProvider
     * @see [类、类#方法、类#成员]
     */
    public void buildTree(ContentNodeBean bean, List<ContentNodeBean> list, String uiProvider) {
        ListIterator<ContentNodeBean> it = list.listIterator();
        List<ContentNodeBean> listOther = new ArrayList<ContentNodeBean>();
        while (it.hasNext()) {
            ContentNodeBean node = (ContentNodeBean) it.next();
            //node.setIconCls(getIconCls(node.getType()));
            if (node.getParentId().equals(bean.getId()) && !node.isHasParent()) {
                //添加子节点
                bean.getChildren().add(node);
                node.setHasParent(true);

                node.setUiProvider(uiProvider); //设置tree node的uiProvider属性为col，方便在前台工作文档那边显示列表
                //标识为非叶节点
                bean.setLeaf(false);
                it.remove();
                listOther.addAll(list);
                buildTree(node, listOther, uiProvider);
            }
        }
    }

}
