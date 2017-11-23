package com.device.business.assetManage.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFHeader;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.assetManage.bean.AssetBean;
import com.device.business.assetManage.bean.DeviceUseBean;
import com.device.business.assetManage.bean.DictionaryBean;
import com.device.business.assetManage.bean.RentBean;
import com.device.business.assetManage.bean.RepairBean;
import com.device.business.assetManage.bean.TestResultBean;
import com.device.business.assetManage.bean.UseBean;
import com.device.business.assetManage.dao.mapper.AssetMapper;
import com.device.business.assetManage.element.AssetElement;
import com.device.business.assetManage.element.DeviceUseElement;
import com.device.business.assetManage.element.RentElement;
import com.device.business.assetManage.element.RepairElement;
import com.device.business.assetManage.element.TestResultElement;
import com.device.business.assetManage.element.TroubleElement;
import com.device.business.assetManage.element.UseElement;
import com.device.business.manageQRCode.bean.manageQRCodeBean;

public class AssetDao {
	@Autowired
	AssetMapper assetMapper;

	public int insertBasicAsset(AssetElement assetElement) {
		return assetMapper.insertBasicAsset(assetElement);

	}
	
	public AssetBean getAssetByID (String id) {
		return assetMapper.getAssetByID(id);
	}

	/**
	 * @param assetBean
	 * @return
	 */
	public ArrayList<AssetBean> queryAssetSearch(AssetBean assetBean,
			int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		return assetMapper.queryAssetSearch(assetBean, rows);
	}

	/**
	 * @param assetBean
	 * @return
	 */
	public int countAssetSearch(AssetBean assetBean) {
		return assetMapper.countAssetSearch(assetBean);
	}

	/**
	 * 获取下拉框的值
	 * 
	 * @param name
	 * @param key
	 * @return
	 */
	public String getComboValue(DictionaryBean dictionaryBean) {
		return assetMapper.getComboValue(dictionaryBean);
	}

	public int insertPurchaseAsset(AssetElement assetElement) {
		return assetMapper.insertPurchaseAsset(assetElement);
	}

	public int updatePurchaseAsset(AssetElement assetElement) {
		return assetMapper.updatePurchaseAsset(assetElement);
	}

	public int isPurchaseExist(String fanumber) {
		return assetMapper.isPurchaseExist(fanumber);
	}

	public int isCheckExist(String fanumber) {
		return assetMapper.isCheckExist(fanumber);
	}

	public int insertCheckAsset(AssetElement assetElement) {
		return assetMapper.insertCheckAsset(assetElement);
	}

	public int updateCheckAsset(AssetElement assetElement) {
		return assetMapper.updateCheckAsset(assetElement);
	}

	public String getClassName(String code) {
		return assetMapper.getClassName(code);
	}

	public String getDeptName(String code) {
		return assetMapper.getDeptName(code);
	}

	public String getClassId(String code) {
		return assetMapper.getClassId(code);
	}

	public String getDeptId(String code) {
		return assetMapper.getDeptId(code);
	}

	public int insertDeviceUse(DeviceUseElement deviceUseElement) {
		return assetMapper.insertDeviceUse(deviceUseElement);
	}

	public int updateDeviceUse(DeviceUseElement deviceUseElement) {
		return assetMapper.updateDeviceUse(deviceUseElement);
	}

	public ArrayList<DeviceUseBean> queryDeviceUse(String fanumber, int start,
			int limit) {
		RowBounds rows = new RowBounds(start, limit);
		return assetMapper.queryDeviceUse(fanumber, rows);
	}

	public int countDeviceUse(String fanumber) {
		return assetMapper.countDeviceUse(fanumber);
	}

	public String queryAssetName(String fanumber) {
		return assetMapper.queryAssetName(fanumber);
	}

	public int deleteDeviceUse(String id) {
		return assetMapper.deleteDeviceUse(id);
	}
	
	public int insertUse(UseElement useElement) {
		return assetMapper.insertUse(useElement);
	}

	public int updateUse(UseElement useElement) {
		return assetMapper.updateUse(useElement);
	}

	public int deleteUse(String id) {
		return assetMapper.deleteUse(id);
	}
	
	public ArrayList<UseBean> queryUse(String fanumber, int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<UseBean> list = new ArrayList<UseBean>();
		list = assetMapper.queryUse(fanumber, rows);
		return list;
	}
	
	public int countUse(String fanumber) {
		return assetMapper.countUse(fanumber);
	}

	public ArrayList<RepairBean> queryRepair(String fanumber, int start, int limit) {
		// 分页工具
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<RepairBean> list = new ArrayList<RepairBean>();
		list = assetMapper.queryRepair(fanumber, rows);
		return list;
	}
	
	public int countRepair(String fanumber) {
		int i = 0;
		i = assetMapper.countRepair(fanumber);
		return i;
	}
	
	public int insertRent(RentElement rentElement){
		return assetMapper.insertRent(rentElement);
	}
	
	public ArrayList<RentBean> queryRent(String fanumber, int start, int limit ){
		RowBounds rows = new RowBounds(start, limit);
		return assetMapper.queryRent(fanumber, rows);
	}
	
	public int countRent(String fanumber){
		return assetMapper.countRent(fanumber);
	}
	
	public int updateRent(RentElement rentElement){
		return assetMapper.updateRent(rentElement);
	}
	
	public int deleteRent(String id){
		return assetMapper.deleteRent(id);
	}
	
	public int generateQRCode(manageQRCodeBean message){
		//判断是否已经存在，如果存在则进更新
		String fanumberString = message.getAsset_id();
		int r = assetMapper.isQRCodeExist(fanumberString);
		if(r != 0) {
			assetMapper.deleteQRCode(message.getAsset_id());
		}
		
		return assetMapper.generateQRCode(message);
	}

	public int updateBasicAssetStatus(AssetElement assetElement){
		return assetMapper.updateBasicAssetStatus(assetElement);
	}
	
	public 	int updateMeasure(AssetElement assetElement){
		return assetMapper.updateMeasure(assetElement);
	}
	
	public int updateMeasureLast(AssetElement assetElement){
		return assetMapper.updateMeasureLast(assetElement);
	}
	
	public ArrayList<TestResultBean> queryTestResult(
			TestResultBean testResultBean, int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<TestResultBean> list = new ArrayList<TestResultBean>();
		list = assetMapper.queryTestResult(testResultBean, rows);
		return list;
	}
	
	public int countTestResult(TestResultBean testResultBean) {
		int i = 0;
		i = assetMapper.countTestResult(testResultBean);
		return i;
	}

	public int insertTestResult(TestResultElement testResultElement) {
		return assetMapper.insertTestResult(testResultElement);
	}

	public int queryTestResultByFanumber(String fanumber){
		return assetMapper.queryTestResultByFanumber(fanumber);
	}
	
	public int queryUseByID(String id){
		return assetMapper.queryUseByID(id);
	}
	
	public String queryAssetID(String fanumber){
		return assetMapper.queryAssetID(fanumber);
	}
	// ///////////////////////////////////////////////////////////
	public List<AssetBean> queryAsset(AssetBean assetBean, int start, int limit) {
		// 分页工具
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<AssetBean> list = new ArrayList<AssetBean>();
		list = assetMapper.queryAsset(assetBean, rows);
		return list;
	}

	public int countAsset(AssetBean assetBean) {
		int i = 0;
		i = assetMapper.countAsset(assetBean);
		return i;
	}

	public int updateAssetStatus(AssetBean assetBean) {
		return assetMapper.updateAssetStatus(assetBean);
	}

	public List<AssetBean> queryAssetById(String id) {
		ArrayList<AssetBean> list = new ArrayList<AssetBean>();
		list = assetMapper.queryAssetById(id);
		return list;
	}

	public ArrayList<AssetBean> queryAssetForApply(AssetBean assetBean,
			int start, int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<AssetBean> list = new ArrayList<AssetBean>();
		list = assetMapper.queryAssetForApply(assetBean, rows);

		return list;
	}

	public int countAssetForApply(AssetBean assetBean) {
		return assetMapper.countAssetForApply(assetBean);
	}

	public int submitAsset(String ids) {
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetMapper.submitAsset(tmpStrings[i]);
		}

		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}

	

	public boolean queryRepairByOrder(String rorder) {
		ArrayList<RepairBean> list = new ArrayList<RepairBean>();
		list = assetMapper.queryRepairByOrder(rorder);
		if (list.size() == 0) {// 无重复
			return false;
		} else {// 有重复
			return true;
		}
	}

	

	public List<AssetBean> queryHousehold(String searchName, int start,
			int limit) {
		// 分页工具
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<AssetBean> list = new ArrayList<AssetBean>();
		list = assetMapper.queryHousehold("%" + searchName + "%", rows);
		return list;
	}

	public int countHouseHold(String searchName) {
		int i = 0;
		i = assetMapper.countHouseHold("%" + searchName + "%");
		return i;
	}

	public List<DictionaryBean> queryDictionary(String attr_name) {
		ArrayList<DictionaryBean> list = new ArrayList<DictionaryBean>();
		list = assetMapper.queryDictionary(attr_name);

		return list;
	}

	public int updateAsset(AssetElement assetElement) {
		return assetMapper.updateAsset(assetElement);
	}

	public int deleteAsset(String ids) {
		String[] tmpStrings = ids.split(",");
		int size = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			size += assetMapper.deleteAsset(tmpStrings[i]);
		}

		if (size == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}

	public int insertRepair(RepairElement repairElement) {
		return assetMapper.insertRepair(repairElement);
	}

	public int updateRepair(RepairElement repairElement) {
		return assetMapper.updateRepair(repairElement);
	}

	public int deleteRepair(String id) {
		return assetMapper.deleteRepair("%" + id + "%");
	}

	public int insertTrouble(TroubleElement troubleElement) {
		return assetMapper.insertTrouble(troubleElement);
	}

	public int updateTrouble(TroubleElement troubleElement) {
		return assetMapper.updateTrouble(troubleElement);
	}

	public ArrayList<TroubleElement> queryTrouble(String id, int start,
			int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<TroubleElement> list = new ArrayList<TroubleElement>();
		list = assetMapper.queryTrouble("%" + id + "%", rows);
		return list;
	}

	public boolean queryTroubleByTid(String tid) {
		ArrayList<TroubleElement> list = new ArrayList<TroubleElement>();
		list = assetMapper.queryTroubleByTid(tid);
		if (list.size() == 0) {// 无重复
			return false;
		} else {// 有重复
			return true;
		}
	}

	public int countTrouble(String id) {
		int i = 0;
		i = assetMapper.countTrouble("%" + id + "%");
		return i;
	}

	public int deleteTrouble(String tid) {
		return assetMapper.deleteTrouble("%" + tid + "%");
	}

	

	

	public boolean queryUseByUseid(String useid) {
		ArrayList<UseBean> list = new ArrayList<UseBean>();
		list = assetMapper.queryUseByUseid(useid);
		if (list.size() == 0) {// 无重复
			return false;
		} else {// 有重复
			return true;
		}
	}

	
	
	public int submitTestResult(String ids) {
		String[] tmpStrings = ids.split(",");
		int sum = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetMapper.submitTestResult(tmpStrings[i]);
		}
		if (sum == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}

	

	public int insertTestResultUnion(TestResultElement testResultElement) {
		String[] tmp = testResultElement.getFanumber().split(",");
		for (int i = 0; i < tmp.length; i++) {
			testResultElement.setFanumber(tmp[i]);
			assetMapper.insertTestResultUnion(testResultElement);
		}
		return 1;
	}

	public int deleteTestResultUnion(String ids) {
		String[] tmpStrings = ids.split(",");
		int sum = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetMapper.deleteTestResultUnion(tmpStrings[i]);
		}
		if (sum == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}

	public int deleteTestResult(String ids) {
		String[] tmpStrings = ids.split(",");
		int sum = 0;
		for (int i = 0; i < tmpStrings.length; i++) {
			sum += assetMapper.deleteTestResult(tmpStrings[i]);
		}
		if (sum == tmpStrings.length) {
			return 1;
		} else {
			return 0;
		}
	}

	public int updateTestResult(TestResultElement testResultElement) {
		return assetMapper.updateTestResult(testResultElement);
	}

	public ArrayList<TestResultBean> queryTestResultById(String id) {
		ArrayList<TestResultBean> list = new ArrayList<TestResultBean>();
		list = assetMapper.queryTestResultById(id);
		return list;
	}

	public ArrayList<AssetBean> queryAssetSearch(AssetBean assetBean) {
		return assetMapper.queryAssetSearch(assetBean);
	}

	public ArrayList<AssetBean> getAssetMayTest(AssetBean assetBean, int start,
			int limit) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<AssetBean> list = new ArrayList<AssetBean>();
		list = assetMapper.getAssetMayTest(assetBean, rows);

		return list;
	}

	public int countAssetMayTest(AssetBean assetBean) {
		return assetMapper.countAssetMayTest(assetBean);
	}
	
	public HSSFWorkbook export(String[] sheet, String[] title,
			Map<Integer, List<AssetBean>> map) {
		int cellNumber = title.length;// �������
		int sheetNumber = sheet.length;// sheet�ĸ���
		HSSFWorkbook workbook = new HSSFWorkbook(); // ����һ��excel
		HSSFCell cell = null; // Excel����
		HSSFRow row = null; // Excel����
		HSSFCellStyle style = workbook.createCellStyle(); // ���ñ�ͷ������
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style1 = workbook.createCellStyle(); // �����������
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // ��������

		for (int j = 0; j < sheetNumber; j++) {
			HSSFSheet sheettmp = workbook.createSheet(sheet[j]); // ����һ��sheet
			HSSFHeader header = sheettmp.getHeader();// ����sheet��ͷ
			header.setCenter("");
			row = sheettmp.createRow(0);
			row.setHeight((short) 400);

			try {
				int  centerindex = cellNumber/2;
				cell = row.createCell((short) centerindex);// ������0�е�k��
				cell.setCellValue("资产详单");
				row = sheettmp.createRow(1);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell((short) k);// ������0�е�k��
					cell.setCellValue(title[k]);// ���õ�0�е�k�е�ֵ
					// sheet.setColumnWidth((short)k,8000);//�����еĿ��
					font.setColor(HSSFFont.COLOR_NORMAL); // ���õ�Ԫ���������ɫ.
					// font.setFontHeight((short) 350); // ���õ�Ԫ����߶�
					style1.setFont(font);// ����������
					cell.setCellStyle(style1);
				}
				// ��д
				List<AssetBean> list = map.get(j);
				for (int i = 0; i < list.size(); i++) {
					AssetBean vo = (AssetBean) list.get(i);// ��ȡvo����
					row = sheettmp.createRow((short) (i + 2));// ������i+1��
					row.setHeight((short) 400);// �����и�

					if (vo.getFanumber() != null) {
						cell = row.createCell((short) 0);// ������i+1�е�0��
						cell.setCellValue(vo.getFanumber());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaclassificationText() != null) {
						cell = row.createCell((short) 1);// ������i+1�е�0��
						cell.setCellValue(vo.getFaclassificationText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFagnumber() != null) {
						cell = row.createCell((short) 2);// ������i+1�е�0��
						cell.setCellValue(vo.getFagnumber());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaname() != null) {
						cell = row.createCell((short) 3);// ������i+1�е�0��
						cell.setCellValue(vo.getFaname());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFamodel() != null) {
						cell = row.createCell((short) 4);// ������i+1�е�0��
						cell.setCellValue(vo.getFamodel());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFamanufacturer() != null) {
						cell = row.createCell((short) 5);// ������i+1�е�0��
						cell.setCellValue(vo.getFamanufacturer());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaamount() != null) {
						cell = row.createCell((short) 6);// ������i+1�е�0��
						cell.setCellValue(vo.getFaamount());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFauomText() != null) {
						cell = row.createCell((short) 7);// ������i+1�е�0��
						cell.setCellValue(vo.getFauomText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFafnum() != null) {
						cell = row.createCell((short) 8);// ������i+1�е�0��
						cell.setCellValue(vo.getFafnum());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFameasureText() != null) {
						cell = row.createCell((short) 9);// ������i+1�е�0��
						cell.setCellValue(vo.getFameasureText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaarrivedate() != null) {
						cell = row.createCell((short) 10);// ������i+1�е�0��
						cell.setCellValue(vo.getFaarrivedate());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaaccountdate() != null) {
						cell = row.createCell((short) 11);// ������i+1�е�0��
						cell.setCellValue(vo.getFaaccountdate());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFauseage() != null) {
						cell = row.createCell((short) 12);// ������i+1�е�0��
						cell.setCellValue(vo.getFauseage());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFadeprectionText() != null) {
						cell = row.createCell((short) 13);// ������i+1�е�0��
						cell.setCellValue(vo.getFadeprectionText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFafrText() != null) {
						cell = row.createCell((short) 14);// ������i+1�е�0��
						cell.setCellValue(vo.getFafrText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFatovText() != null) {
						cell = row.createCell((short) 15);// ������i+1�е�0��
						cell.setCellValue(vo.getFatovText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFavalue() != null) {
						cell = row.createCell((short) 16);// ������i+1�е�0��
						cell.setCellValue(vo.getFavalue());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFagmText() != null) {
						cell = row.createCell((short) 17);// ������i+1�е�0��
						cell.setCellValue(vo.getFagmText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFadfuText() != null) {
						cell = row.createCell((short) 18);// ������i+1�е�0��
						cell.setCellValue(vo.getFadfuText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFausestateText() != null) {
						cell = row.createCell((short) 19);// ������i+1�е�0��
						cell.setCellValue(vo.getFausestateText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFadeptText() != null) {
						cell = row.createCell((short) 20);// ������i+1�е�0��
						cell.setCellValue(vo.getFadeptText());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFauser() != null) {
						cell = row.createCell((short) 21);// ������i+1�е�0��
						cell.setCellValue(vo.getFauser());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getFaremarks() != null) {
						cell = row.createCell((short) 22);// ������i+1�е�0��
						cell.setCellValue(vo.getFaremarks());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return workbook;
	}
}
