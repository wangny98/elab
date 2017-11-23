package com.device.business.assetManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.element.AssetElement;

public interface AssetHouseholdMapper {

	/**查询列表
	 * @param searchName
	 * @param rowBounds
	 * @return
	 */
	ArrayList<AssetBean> queryAssetHousehold(AssetBean assetBean, RowBounds rowBounds);
	
	/**根据id查询
	 * @param id
	 * @return
	 */
	ArrayList<AssetBean> queryAssetHouseholdById(String id);
	
	/**插入
	 * @param assetElement
	 */
	int insertAssetHousehold(AssetElement assetElement);
	
	/**
	 * 查询数目
	 */
	int countAssetHousehold(AssetBean assetBean);
	
	/**删除
	 * @param id
	 */
	int deleteAssetHousehold(String id);
	
	/**更新
	 * @param assetElement
	 * @return
	 */
	int updateAssetHousehold(AssetElement assetElement);
	
	/**提交
	 * @param id
	 * @return
	 */
	int submitAssetHousehold(String id);
}
