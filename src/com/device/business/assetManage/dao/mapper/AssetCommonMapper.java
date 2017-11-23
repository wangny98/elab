package com.device.business.assetManage.dao.mapper;

import com.device.business.assetManage.element.AssetElement;

public interface AssetCommonMapper {

	/**插入通用设备信息
	 * @param assetElement
	 */
	int insertCommonAsset(AssetElement assetElement);

	/**删除通用设备附加信息
	 * @param ids
	 */
	int deleteCommonAsset(String ids);
	
	/**更新通用设备信息
	 * @param assetElement
	 * @return
	 */
	int updateCommonAsset(AssetElement assetElement);
}
