package com.device.business.assetManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.element.AssetElement;

public interface AssetInstrumentMapper {

	/**插入检验设备
	 * @param assetElement
	 */
	int insertInstrumentAsset(AssetElement assetElement);
	
	/**查询列表
	 * @param searchName
	 * @param rowBounds
	 * @return
	 */
	ArrayList<AssetBean> queryInstrumentAsset(AssetBean assetBean, RowBounds rowBounds);
	
	/**
	 * 查询数目
	 */
	int countInstrumentAsset(AssetBean assetBean);
	
	/**删除
	 * @param id
	 */
	int deleteInstrumentAsset(String id);
	
	/**根据id查询
	 * @param id
	 * @return
	 */
	ArrayList<AssetBean> queryInstrumentAssetById(String id);
	
	/**更新检验设备
	 * @param assetElement
	 * @return
	 */
	int updateInstrumentAsset(AssetElement assetElement);
	
	/**提交
	 * @param id
	 * @return
	 */
	int submitInstrumentAsset(String id);
}
