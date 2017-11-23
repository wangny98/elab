package com.device.business.assetManage.dao.mapper;

import java.util.ArrayList;

import org.apache.ibatis.session.RowBounds;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.bean.DeviceUseBean;
import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.bean.RentBean;
import com.device.business.assetManage.bean.RepairBean;
import com.device.business.assetManage.bean.TestResultBean;
import com.device.business.assetManage.bean.UseBean;
import com.device.business.assetManage.element.AssetElement;
import com.device.business.assetManage.element.DeviceUseElement;
import com.device.business.assetManage.element.RentElement;
import com.device.business.assetManage.element.RepairElement;
import com.device.business.assetManage.element.TestResultElement;
import com.device.business.assetManage.element.TroubleElement;
import com.device.business.assetManage.element.UseElement;
import com.device.business.innerAllocate.bean.InnerAllocateBean;
import com.device.business.manageQRCode.bean.manageQRCodeBean;

public interface AssetMapper {
	/**插入基本信息
	 * @param assetElement
	 */
	int insertBasicAsset(AssetElement assetElement);
	
	/**
	 * 根据资产ID获取资产信息
	 * @param id
	 * @return
	 */
	public AssetBean getAssetByID (String id);
	
	/**
	 * @param assetBean
	 * @return
	 */
	ArrayList<AssetBean> queryAssetSearch(AssetBean assetBean, RowBounds rows);
	
	/**
	 * @param assetBean
	 * @return
	 */
	int countAssetSearch(AssetBean assetBean);
	
	/**查询字典
	 * @param attr_name
	 * @return
	 */
	ArrayList<DictionaryBean> queryDictionary(String attr_name);
	
	/**获取下拉框的值
	 * @param name
	 * @param key
	 * @return
	 */
	String getComboValue(DictionaryBean dictionaryBean);
	
	int insertPurchaseAsset(AssetElement assetElement);
	
	int updatePurchaseAsset(AssetElement assetElement);
	
	int isPurchaseExist(String fanumber);
	
	int isCheckExist(String fanumber);
	
	int insertCheckAsset(AssetElement assetElement);
	
	int updateCheckAsset(AssetElement assetElement);
	
	String getClassName(String code);
	
	String getDeptName(String code);
	
	String getClassId(String code);
	
	String getDeptId(String code);
	
	int insertDeviceUse(DeviceUseElement deviceUseElement);
	
	int updateDeviceUse(DeviceUseElement deviceUseElement);
	
	ArrayList<DeviceUseBean> queryDeviceUse(String fanumber, RowBounds rows);
	
	int countDeviceUse(String fanumber);
	
	String queryAssetName(String fanumber);
	
	int deleteDeviceUse(String fanumber);

	int insertUse(UseElement useElement);
	
	int updateUse(UseElement useElement);
	
	int deleteUse(String id);
	
	ArrayList<UseBean> queryUse(String fanumber, RowBounds rowBounds);
	
	int countUse(String fanumber);
	
	ArrayList<RepairBean> queryRepair(String fanumber, RowBounds rowBounds);
	
	int countRepair(String fanumber);
	
	int insertRent(RentElement rentElement);
	
	ArrayList<RentBean> queryRent(String fanumber, RowBounds rowBounds);
	
	int countRent(String fanumber);
	
	int updateRent(RentElement rentElement);
	
	int deleteRent(String id);
	
	int generateQRCode(manageQRCodeBean message);
	
	int isQRCodeExist(String fanumber);
	
	void deleteQRCode(String bean);
	
	void updateQRCode(manageQRCodeBean bean);
	
	int updateBasicAssetStatus(AssetElement assetElement);
	
	int updateMeasure(AssetElement assetElement);
	
	/**查询检定结果
	 * @param rowBounds
	 * @return
	 */
	ArrayList<TestResultBean> queryTestResult(TestResultBean testResultBean,RowBounds rowBounds);
	
	/**计算鉴定结果数目
	 * @return
	 */
	int countTestResult(TestResultBean testResultBean);
	
	/**插入鉴定结果
	 * @param testResultElement
	 */
	int insertTestResult(TestResultElement testResultElement);
	
	int updateMeasureLast(AssetElement assetElement);
	
	int queryTestResultByFanumber(String fanumber);
	
	int queryUseByID(String id);
	
	String queryAssetID(String fanumber);
	//////////////////////////////////////////////////////////////////

	/**查询列表
	 * @param searchName
	 * @param rowBounds
	 * @return
	 */
	ArrayList<AssetBean> queryAsset(AssetBean assetBean, RowBounds rowBounds);
	
	/**列表样本个数
	 * @param searchName
	 * @return
	 */
	int countAsset(AssetBean assetBean);
	
	/**提交
	 * @param id
	 * @return
	 */
	int submitAsset(String id);
	
	/**g更新资产状态
	 * @param tablename
	 * @param fanumber
	 * @return
	 */
	int updateAssetStatus(AssetBean assetBean);
	
	/**更新信息
	 * @param assetElement
	 * @return
	 */
	int updateAsset(AssetElement assetElement);
	
	/**根据id查询
	 * @param id
	 * @return
	 */
	ArrayList<AssetBean> queryAssetById(String id);
	
	/**
	 * @return
	 */
	ArrayList<AssetBean> queryAssetForApply(AssetBean assetBean,RowBounds rowBounds);
	
	/**
	 * @return
	 */
	int countAssetForApply(AssetBean assetBean);
	
	/**查询家居用具列表
	 * @param searchName
	 * @param rowBounds
	 * @return
	 */
	ArrayList<AssetBean> queryHousehold(String searchName, RowBounds rowBounds);
	
	/**列表家具用具个数
	 * @param searchName
	 * @return
	 */
	int countHouseHold(String searchName);
	
	
	
	
	/**删除
	 * @param ids
	 */
	int deleteAsset(String ids);
	
	/**查询修理记录
	 * @param id
	 * @return
	 */
	
	
	/**根据维修单号查询记录
	 * @param rorder
	 * @return
	 */
	ArrayList<RepairBean> queryRepairByOrder(String rorder);
	
	/**查询修理记录个数
	 * @param id
	 * @return
	 */
	
	
	/**插入维修信息
	 * @param repairElement
	 */
	int insertRepair(RepairElement repairElement);
	
	/**更新维修信息
	 * @param repairElement
	 * @return
	 */
	int updateRepair(RepairElement repairElement);
	
	/**删除维修信息
	 * @param id
	 */
	int deleteRepair(String id);
	
	/**插入违章信息
	 * @param troubleElement
	 */
	int insertTrouble(TroubleElement troubleElement);
	
	/**更新违章信息
	 * @param troubleElement
	 * @return
	 */
	int updateTrouble(TroubleElement troubleElement);
	
	/**查询违章记录
	 * @param id
	 * @param rowBounds
	 * @return
	 */
	ArrayList<TroubleElement> queryTrouble(String id, RowBounds rowBounds);
	
	/**根据tid查询违章记录
	 * @param tid
	 * @return
	 */
	ArrayList<TroubleElement> queryTroubleByTid(String tid);
	
	/**查询违章记录个数
	 * @param id
	 * @return
	 */
	int countTrouble(String id);
	
	/**删除违章
	 * @param tid
	 */
	int deleteTrouble(String tid);
	
	
	
	/**查询使用月报
	 * @param id
	 * @return
	 */
	
	
	/**根据useid查询使用月报
	 * @param useid
	 * @return
	 */
	ArrayList<UseBean> queryUseByUseid(String useid);
	
	/**查询使用月报个数
	 * @param id
	 * @return
	 */

	
	
	
	/**根据id查询
	 * @param id
	 * @return
	 */
	ArrayList<TestResultBean> queryTestResultById(String id);
	
	/**提交
	 * @param id
	 * @return
	 */
	int submitTestResult(String id);
	
	
	
	/**插入鉴定结果lianjie
	 * @param testResultElement
	 */
	int insertTestResultUnion(TestResultElement testResultElement);
	
	/**删除鉴定结果
	 * @param id
	 */
	int deleteTestResult(String id);
	
	/**删除鉴定结果lianjie
	 * @param id
	 */
	int deleteTestResultUnion(String id);
	
	/**更新检定结果
	 * @param assetElement
	 * @return
	 */
	int updateTestResult(TestResultElement testResultElement);
	
	
	
	/**
	 * @param assetBean
	 * @return
	 */
	ArrayList<AssetBean> queryAssetSearch(AssetBean assetBean);
	
	
	
	/**
	 * @return
	 */
	ArrayList<AssetBean> getAssetMayTest(AssetBean assetBean,RowBounds rowBounds);
	
	/**
	 * @return
	 */
	int countAssetMayTest(AssetBean assetBean);
	
}
