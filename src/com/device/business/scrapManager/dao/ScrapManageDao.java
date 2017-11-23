package com.device.business.scrapManager.dao;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.device.business.login.element.LoginElement;
import com.device.business.roleManage.bean.NodeBean;
import com.device.business.scrapManager.bean.ScrapBean;
import com.device.business.scrapManager.dao.mapper.ScrapMapper;
import com.device.business.transferManager.bean.BasePropertyBean;
import com.device.business.transferManager.dao.mapper.TransferMapper;
import com.device.filter.AuthorityContext;
import com.device.util.HSSFWorkbookUtil;
import com.device.util.IdAppendUtil;
import com.device.util.PrimaryKeyUtil;

public class ScrapManageDao {
	@Autowired
	ScrapMapper scrapMapper;

	@Autowired
	TransferMapper transferMapper;

	public List<ScrapBean> queryTransfer(String searchName, int start,
			int limit, String states) {
		RowBounds rows = new RowBounds(start, limit);
		ArrayList<ScrapBean> list = new ArrayList<ScrapBean>();
		list = scrapMapper.query("%" + searchName + "%", rows, states);
		return list;
	}

	public int count(String searchName, String states) {
		int i = 0;
		i = scrapMapper.count("%" + searchName + "%", states);
		return i;
	}

	@Transactional
	public int add(ScrapBean bean, String property_ids, String property_types,int state) {

		LoginElement login = AuthorityContext.getLoginElement();
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setCreator_id(login.getUserId());
		bean.setCreator_name(login.getUserFullName());
		bean.setCreate_time(new Date());
		bean.setState(state);
		if (property_ids != null) {
			String[] ids = property_ids.split(",");
			String[] types = property_types.split(",");
			
			for (int i = 0; i < ids.length; i++) {
				String table_name = this.getTableName(types[i]);
				scrapMapper.addUnion(bean.getId(), table_name, ids[i]);
			}
			if(state==8){
				scrapMapper.updatePropertyStatue4Basic(IdAppendUtil.Array2String(ids), 5);
				//scrapMapper.updatePropertyStatue4House("("+property_ids+")", state);
				//scrapMapper.updatePropertyStatue4Instr("("+property_ids+")", state);
				scrapMapper.updatePropertyScrapTime(IdAppendUtil.Array2String(ids), new Date());
			}
		}
		return scrapMapper.add(bean);
	}

	public String getTableName(String type) {
		String tableName = "";
		if (type.equals("1")) {
			tableName = "T_FIXEDASSET_BASIC";
		} else if (type.equals("2")) {
			tableName = "T_FIXEDASSET_INSTRUMENT";
		} else if (type.equals("3")) {
			tableName = "T_FIXEDASSET_HOUSEHOLD";
		}
		return tableName;
	}

	@Transactional
	public ScrapBean load(String id) {
		return scrapMapper.load(id);
	}

	@Transactional
	public int update(ScrapBean bean,String property_ids, String property_types) {
		scrapMapper.delunion(bean.getId());
		if (property_ids != null) {
			String[] ids = property_ids.split(",");
			String[] types = property_types.split(",");

			for (int i = 0; i < ids.length; i++) {
				String table_name = this.getTableName(types[i]);
				scrapMapper.addUnion(bean.getId(), table_name, ids[i]);
			}
		}
		return scrapMapper.update(bean);
	}

	/******************************************************************/
	public List<BasePropertyBean> listBaseProperty(String searchName,
			int start, int limit,String scrap_id) {

		RowBounds rows = new RowBounds(start, limit);
		ArrayList<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		list = transferMapper.listBaseProperty("%" + searchName + "%", rows);
		
		List<String> checkPropertyIds = scrapMapper.getPropertyConcat(scrap_id);
		for(int i=0;i<list.size();i++){
			if(checkPropertyIds.contains(list.get(i).getId())){
				list.get(i).setChecked(1);
			}
		}
		
		Comparator<BasePropertyBean> comparator = new Comparator<BasePropertyBean>() {
            public int compare(BasePropertyBean o1, BasePropertyBean o2) {
                return o2.getChecked()-o1.getChecked() ;
            }
        };
        Collections.sort(list, comparator);
		return list;
	}

	public int countBaseProperty(String searchName) {
		int count = 0;
		count = transferMapper.countBaseProperty("%" + searchName + "%");
		return count;
	}

	public List<BasePropertyBean> queryCheckProperty(String scrap_id,
			int start, int limit) {
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		
		List<String> ids = scrapMapper.getPropertyConcat(scrap_id);
		System.out.println("ids : " + IdAppendUtil.Collection2String(ids));
/*		if(start==0){
			list = scrapMapper.getCheckBaseProperty(IdAppendUtil.Collection2String(ids));
		}*/
		list = scrapMapper.getCheckBaseProperty(IdAppendUtil.Collection2String(ids));

		return list;
	}

	public int countCheckProperty(String scrap_id) {
		int count = 0;
		count = scrapMapper.countCheckBaseProperty(scrap_id);
		return count;
	}

	/******************************************************************/
	/*
	 * 1 未申请 2 撤销 3 已申请未审核 4 审核不通过 5 审核通过未会签 6 会签未通过 7 已会签未批准 8 已批准 9 不批准
	 */

	@Transactional
	public void updatePropertyStatue(String scrap_id, String state) {

		List<String> ids = scrapMapper.getPropertyConcat(scrap_id);
		//System.out.println("ids : " + IdAppendUtil.Collection2String(ids));
		scrapMapper.updateScrapState(scrap_id, state);
		this.updateScrapTime(scrap_id, state);
		if(state.equals("4") || state.equals("5")){
			LoginElement login = AuthorityContext.getLoginElement();
			scrapMapper.updateExamineInfo(scrap_id,login.getUserFullName());
		}
		int status = 2;

		if (state.equals("2") || state.equals("9"))// 撤销
		{
			status = 2;
		} else if (state.equals("3")) {// 提交申请
			status = 3;
		} else if (state.equals("8")) {// 批准
			status = 5;
			scrapMapper.updatePropertyScrapTime(IdAppendUtil.Collection2String(ids), new Date());
		}

		scrapMapper.updatePropertyStatue4Basic(IdAppendUtil.Collection2String(ids), status);
		//scrapMapper.updatePropertyStatue4House(IdAppendUtil.Collection2String(ids), status);
		//scrapMapper.updatePropertyStatue4Instr(IdAppendUtil.Collection2String(ids), status);

	}

	public void updateScrapTime(String scrap_id, String state) {
		if (state.equals("3")) {
			scrapMapper.updateScrapTime(scrap_id, "scrap_time", new Date());
		} else if (state.equals("4") || state.equals("5")) {
			scrapMapper.updateScrapTime(scrap_id, "examine_time", new Date());
		} else if (state.equals("6") || state.equals("7")) {
			scrapMapper.updateScrapTime(scrap_id, "check_time", new Date());
		} else if (state.equals("8") || state.equals("9")) {
			scrapMapper.updateScrapTime(scrap_id, "apply_time", new Date());
		}
	}

	public HSSFWorkbook exportEXL(String scrap_id)
			throws FileNotFoundException, IOException {
		int sheetNo = 0;
		int cellOffset = 0;
		int rowNo = 1;
		HSSFWorkbookUtil hSSFWorkbookUtil = new HSSFWorkbookUtil("");
		HSSFSheet sheet = hSSFWorkbookUtil.createSheet();
		HSSFWorkbook wb = hSSFWorkbookUtil.getWorkbook();
		HSSFCellStyle cs = wb.createCellStyle();
		HSSFFont fCol = wb.createFont(); // 字体对象，表头
		fCol.setFontHeightInPoints((short) 10);
		fCol.setBoldweight(fCol.BOLDWEIGHT_BOLD);
		cs.setFont(fCol);
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs.setFillBackgroundColor((short) 16);
		// request.setCharacterEncoding("utf-8");
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 0, 2000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 1, 5000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 2, 5000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 3, 5000);
		// ------------------------------------------------
		HSSFCellStyle cs1 = wb.createCellStyle();
		cs1.setFont(fCol);
		cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs1.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 上下居中
		cs1.setFillBackgroundColor(HSSFColor.GREY_50_PERCENT.index);
		
		//hSSFWorkbookUtil.setStringValue(sheetNo, 0, 0, "资产报废列表", cs1);
		//hSSFWorkbookUtil.setRowHeightInPoints(sheetNo, 0, 35);

		// --------------第二行---------------------------------
		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset, "报废单号", cs);
		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset + 1, "报废单位", cs);
		hSSFWorkbookUtil
				.setStringValue(sheetNo, 1, cellOffset + 2, "报废申请人", cs);
		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset + 3, "报废时间", cs);

		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset + 4, "资产类别", cs);
		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset + 5, "资产名称", cs);

		hSSFWorkbookUtil.setStringValue(sheetNo, 1, cellOffset + 6, "操作", cs);

		rowNo++;

		HSSFFont fCol2 = wb.createFont(); // 字体对象，表头
		fCol2.setFontHeightInPoints((short) 10);
		fCol2.setColor(HSSFFont.COLOR_RED);
		HSSFCellStyle cs2 = wb.createCellStyle();
		cs2.setFont(fCol2);
		cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);

		ScrapBean scrapBean = this.load(scrap_id);
		List<BasePropertyBean> list = new ArrayList<BasePropertyBean>();
		List<String> ids = scrapMapper.getPropertyConcat(scrap_id);
		System.out.println("ids : " + IdAppendUtil.Collection2String(ids));
		list = scrapMapper.getCheckBaseProperty(IdAppendUtil.Collection2String(ids));
		
		
		for (int i = 0; i < list.size(); i++) {

			BasePropertyBean propertyBean = list.get(i);
			System.out.println("propertyBean : " + propertyBean);
			hSSFWorkbookUtil.setIntValue(sheetNo, rowNo, cellOffset, Integer
					.valueOf(rowNo - 1).toString(), cs2);

			hSSFWorkbookUtil.setStringValue(
					sheetNo,
					rowNo,
					cellOffset + 1,
					scrapBean.getScrap_no() == null ? "" : scrapBean
							.getScrap_no(), cs2);
			
			hSSFWorkbookUtil.setStringValue(
					sheetNo,
					rowNo,
					cellOffset + 2,
					scrapBean.getScrap_department() == null ? "" : scrapBean
							.getScrap_department(), cs2);
			
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, cellOffset + 3,
					String.valueOf(scrapBean.getScrap_time()) == null ? ""
							: String.valueOf(scrapBean.getScrap_time()), cs2);
			
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, cellOffset + 4,
					propertyBean.getFaclassification() == null ? ""
							: propertyBean.getFaclassification(),cs2);
			
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, cellOffset + 5,
					propertyBean.getFaname() == null ? ""
							: propertyBean.getFaname(),cs2);

			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, cellOffset + 6,
					scrapBean.getAction() == null ? ""
							: scrapBean.getAction(),cs2);
			rowNo++;
		}
		hSSFWorkbookUtil.setSheetName(sheetNo, "资产审核");
		return hSSFWorkbookUtil.getWorkbook();
	}
	
	
}
