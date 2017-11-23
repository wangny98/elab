package com.device.business.innerAllocate.dao;

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
import com.device.business.innerAllocate.bean.InnerAllocateBean;
import com.device.business.innerAllocate.bean.LinkBean;
import com.device.business.innerAllocate.dao.mapper.InnerAllocateMapper;
import com.device.business.innerAllocate.element.InnerAllocateApplyElement;

public class InnerAllocateDao {
	@Autowired
	InnerAllocateMapper innerAllocateMapper;
	
	public List<InnerAllocateBean> queryInnerAllocate(InnerAllocateBean innerAllocateBean, int start, int limit){
		//分页工具
        RowBounds rows = new RowBounds(start, limit);
        ArrayList<InnerAllocateBean> list = new  ArrayList<InnerAllocateBean>();
        list = innerAllocateMapper.queryInnerAllocate(innerAllocateBean, rows);
        return list;
	}
	
	public List<InnerAllocateBean> queryInnerAllocate(InnerAllocateBean innerAllocateBean){
		//分页工具
        ArrayList<InnerAllocateBean> list = new  ArrayList<InnerAllocateBean>();
        list = innerAllocateMapper.queryInnerAllocate(innerAllocateBean);
        return list;
	}
	
	public int countInnerAllocate(InnerAllocateBean innerAllocateBean){
		int i=0;
		i= innerAllocateMapper.countInnerAllocate(innerAllocateBean);
		return i;
	}
	
	public int insertInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement){
		return innerAllocateMapper.insertInnerAllocate(innerAllocateApplyElement);
	}
	
	public int insertInnerLink(LinkBean linkBean){
		return innerAllocateMapper.insertInnerLink(linkBean);
	}
	
	public int deleteInnerLink(String id){
		return innerAllocateMapper.deleteInnerLink(id);
	}

	public String queryInnerAllocateID(String fanumber){
		return innerAllocateMapper.queryInnerAllocateID(fanumber);
	}
	
	public int updateInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement){
		return innerAllocateMapper.updateInnerAllocate(innerAllocateApplyElement);
	}
	
	public String queryInnerAllocateAssetID(String id){
		return innerAllocateMapper.queryInnerAllocateAssetID(id);
	}
	
	public int deleteInnerAllocate(String id){
		return innerAllocateMapper.deleteInnerAllocate(id);
	}
	
	public int submitInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement){
		return innerAllocateMapper.submitInnerAllocate(innerAllocateApplyElement);
	}
	
	public int verifyInnerAllocate(InnerAllocateApplyElement innerAllocateApplyElement){
		return innerAllocateMapper.verifyInnerAllocate(innerAllocateApplyElement);
	}
	
	public List<AssetBean> queryAllocAsset(AssetBean assetBean){
		return innerAllocateMapper.queryAllocAsset(assetBean);
	}
	
	public int countAllocAsset(InnerAllocateBean innerAllocateBean){
		return innerAllocateMapper.countAllocAsset(innerAllocateBean);
	}
	
	public List<AssetBean> queryAllocAsset(InnerAllocateBean innerAllocateBean, int start, int limit){
		RowBounds rows = new RowBounds(start, limit);
		return innerAllocateMapper.queryAllocAsset(innerAllocateBean, rows);
	}
	
	public int isAllocExist(String id){
		return innerAllocateMapper.isAllocExist(id);
	}
	
	public HSSFWorkbook export(String[] sheet, String[] title,
			Map<Integer, List<InnerAllocateBean>> map) {
		int cellNumber = title.length;// 表的列数
		int sheetNumber = sheet.length;// sheet的个数
		HSSFWorkbook workbook = new HSSFWorkbook(); // 创建一个excel
		HSSFCell cell = null; // Excel的列
		HSSFRow row = null; // Excel的行
		HSSFCellStyle style = workbook.createCellStyle(); // 设置表头的类型
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle style1 = workbook.createCellStyle(); // 设置数据类型
		style1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFFont font = workbook.createFont(); // 设置字体

		for (int j = 0; j < sheetNumber; j++) {
			HSSFSheet sheettmp = workbook.createSheet(sheet[j]); // 创建一个sheet
			HSSFHeader header = sheettmp.getHeader();// 设置sheet的头
			header.setCenter("");
			row = sheettmp.createRow(0);
			row.setHeight((short) 400);

			try {
				int  centerindex = cellNumber/2;
				cell = row.createCell((short) centerindex);// 创建第0行第k列
				cell.setCellValue("内部资产调拨申请");
				row = sheettmp.createRow(1);
				for (int k = 0; k < cellNumber; k++) {
					cell = row.createCell((short) k);// 创建第0行第k列
					cell.setCellValue(title[k]);// 设置第0行第k列的值
					// sheet.setColumnWidth((short)k,8000);//设置列的宽度
					font.setColor(HSSFFont.COLOR_NORMAL); // 设置单元格字体的颜色.
					// font.setFontHeight((short) 350); // 设置单元字体高度
					style1.setFont(font);// 设置字体风格
					cell.setCellStyle(style1);
				}
				// 填写
				List<InnerAllocateBean> list = map.get(j);
				for (int i = 0; i < list.size(); i++) {
					InnerAllocateBean vo = (InnerAllocateBean) list.get(i);// 获取vo对象
					row = sheettmp.createRow((short) (i + 2));// 创建第i+1行
					row.setHeight((short) 400);// 设置行高

					if (vo.getId() != null) {
						cell = row.createCell((short) 0);// 创建第i+1行第0列
						cell.setCellValue(vo.getId());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAsset_id() != null) {
						cell = row.createCell((short) 1);// 创建第i+1行第0列
						cell.setCellValue(vo.getAsset_id());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getFaname() != null) {
						cell = row.createCell((short) 2);// 创建第i+1行第0列
						cell.setCellValue(vo.getFaname());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_user() != null) {
						cell = row.createCell((short) 3);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_user());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_holder() != null) {
						cell = row.createCell((short) 4);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_holder());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_value() != null) {
						cell = row.createCell((short) 5);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_value());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_type() != null) {
						cell = row.createCell((short) 6);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_typeText());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_date() != null) {
						cell = row.createCell((short) 7);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_date());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_reason() != null) {
						cell = row.createCell((short) 8);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_reason());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_depart() != null) {
						cell = row.createCell((short) 9);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_departText());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getAlloc_memo() != null) {
						cell = row.createCell((short) 10);// 创建第i+1行第0列
						cell.setCellValue(vo.getAlloc_memo());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getApply_date() != null) {
						cell = row.createCell((short) 11);// 创建第i+1行第0列
						cell.setCellValue(vo.getApply_date());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getApplyer() != null) {
						cell = row.createCell((short) 12);// 创建第i+1行第0列
						cell.setCellValue(vo.getApplyer());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getVerify_date() != null) {
						cell = row.createCell((short) 13);// 创建第i+1行第0列
						cell.setCellValue(vo.getVerify_date());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getVerifyer() != null) {
						cell = row.createCell((short) 14);// 创建第i+1行第0列
						cell.setCellValue(vo.getVerifyer());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
					if (vo.getStatusText() != null) {
						cell = row.createCell((short) 15);// 创建第i+1行第0列
						cell.setCellValue(vo.getStatusText());// 设置第i+1行第0列的值
						cell.setCellStyle(style);// 设置风格
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return workbook;
	}
}
