package com.device.business.assetManage.dao.mapper;

import com.device.business.assetManage.element.AssetElement;

public interface AssetComputerMapper {

	/**插入计算机信息
	 * @param assetElement
	 */
	int insertComputerAsset(AssetElement assetElement);
	
	/**删除计算机附加信息
	 * @param ids
	 */
	int deleteComputerAsset(String ids);
	
	/**更新计算机信息
	 * @param assetElement
	 * @return
	 */
	int updateComputerAsset(AssetElement assetElement);
	
	int isComputerInfoExist(String fanumber);
}
