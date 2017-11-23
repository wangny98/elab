package com.device.business.assetManage.dao.mapper;

import com.device.business.assetManage.element.AssetElement;

public interface AssetHouseMapper {

	/**插入房地产信息
	 * @param assetElement
	 */
	int insertHouseAsset(AssetElement assetElement);
	
	/**删除房地产附加信息
	 * @param ids
	 */
	int deleteHouseAsset(String ids);
	
	/**更新房地产信息
	 * @param assetElement
	 * @return
	 */
	int updateHouseAsset(AssetElement assetElement);
	
	int isHouseInfoExist(String fanumber);
}
