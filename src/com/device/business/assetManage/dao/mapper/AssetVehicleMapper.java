package com.device.business.assetManage.dao.mapper;

import com.device.business.assetManage.element.AssetElement;

public interface AssetVehicleMapper {

	/**插入车辆信息
	 * @param assetElement
	 */
	int insertAssetVehicle(AssetElement assetElement);
	
	/**删除车辆附加信息
	 * @param ids
	 */
	int deleteAssetVehicle(String ids);
	
	/**更新车辆信息
	 * @param assetElement
	 * @return
	 */
	int updateAssetVehicle(AssetElement assetElement);
	
	int isVehicleInfoExist(String fanumber);
}
