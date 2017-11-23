package com.device.business.innerAllocate.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.innerAllocate.bean.InnerAllocateBean;
import com.device.business.innerAllocate.bean.LinkBean;
import com.device.business.innerAllocate.element.InnerAllocateApplyElement;

public interface InnerAllocateMapper {

	/**查询列表
	 * @param innerAllocateBean
	 * @param rowBounds
	 * @return
	 */
	ArrayList<InnerAllocateBean> queryInnerAllocate(InnerAllocateBean innerAllocateBean, RowBounds rowBounds);
	
	/**查询列表
	 * @param innerAllocateBean
	 * @return
	 */
	ArrayList<InnerAllocateBean> queryInnerAllocate(InnerAllocateBean innerAllocateBean);
	
	/**列表样本个数
	 * @param searchName
	 * @return
	 */
	int countInnerAllocate(InnerAllocateBean innerAllocateBean);
	
	/**插入
	 * @param innerAllocateApplyElement
	 * @return
	 */
	int insertInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement);
	
	/**
	 * @param faid
	 * @param applyid
	 * @param tablename
	 * @return
	 */
	int insertInnerLink(LinkBean linkBean);
	
	/**删除中间表
	 * @param id
	 * @return
	 */
	int deleteInnerLink(String id);
	
	/**
	 * @return
	 */
	String queryInnerAllocateID(String fanumber);
	
	/**更新
	 * @param innerAllocateApplyElement
	 * @return
	 */
	int updateInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement);
	
	/**
	 * @param id
	 * @return
	 */
	String queryInnerAllocateAssetID(String id);
	
	/**
	 * @param id
	 * @return
	 */
	int deleteInnerAllocate(String id);
	
	/**
	 * @param innerAllocateApplyElement
	 * @return
	 */
	int submitInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement);
	
	/**
	 * @param innerAllocateApplyElement
	 * @return
	 */
	int verifyInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement);
	
	List<AssetBean> queryAllocAsset(AssetBean assetBean);
	
	List<AssetBean> queryAllocAsset(InnerAllocateBean innerAllocateBean, RowBounds rowBounds);
	
	int countAllocAsset(InnerAllocateBean innerAllocateBean);
	
	int isAllocExist(String id);
	
}
