package com.device.business.report.dao;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import com.device.business.report.dao.mapper.PropertyReport2Mapper;
import com.device.business.report.dao.mapper.PropertyReportMapper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import com.device.business.report.bean.CellBean;
import com.device.business.report.bean.ComponentJsonReaderBean;
import com.device.business.report.bean.ComponentJsonReaderMetaDataBean;
import com.device.util.HSSFWorkbookUtil;

public class PropertyReportDao {

	@Autowired
	PropertyReportMapper propertyReportMapper;
	
	@Autowired
	PropertyReport2Mapper propertyReportMapper2;
	
	public List<String> getKey(List<List<CellBean>> lists,String keyStr){

		List<String> keys = new ArrayList<String>();
		String[] temp = keyStr.split(",");
		for(int i=0;i<temp.length;i++){
			keys.add(temp[i]);
		}
		
		for(List<CellBean> list : lists){
			for(CellBean bean:list){
				String value = ","+bean.getRowName()+",";
				if(keyStr.indexOf(value)<0)
					keys.add(bean.getRowName());
			}
		}
		return keys;
	}
	
	public Map<String,Object> list2map(List<CellBean> list){
		Map<String,Object> map = new HashMap<String,Object>();
		for(CellBean bean:list){
			map.put(bean.getRowName(), String.valueOf(bean.getValue()));
		}
		return map;
	}
	
	
	
	//获取元数据
	public ComponentJsonReaderMetaDataBean getMetaData(List<String> fields){
		List<Map<String, String>> fieldsList = new ArrayList<Map<String, String>>();
		ComponentJsonReaderMetaDataBean MetaDataBean = new ComponentJsonReaderMetaDataBean();
		
		Map<String, String> groupMap = new HashMap<String, String>();
		groupMap.put("name","key");
		fieldsList.add(groupMap);
		
		for(String column:fields){
			Map<String, String> map = new HashMap<String, String>();
			map.put("name",column);
			fieldsList.add(map);
		}
		MetaDataBean.setFields(fieldsList.toArray());
		return MetaDataBean;
	}
	
	//固定资产基本信息报表
	public ComponentJsonReaderBean groupPropertyByclassification() throws ParseException{

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new  Date());
		int year = calendar.get(Calendar.YEAR);

	/*	SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startStr=year+"-00-00 00:00:00";
		String endStr=year+"-12-31 24:59:59";*/
		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String startStr=year+"-01-01";
		String endStr=year+1+"-01-01";
		
		Date startDate = sdf.parse(startStr);
		
		Date endDate = sdf.parse(endStr);
		List<String> fields = new ArrayList<String>();
		fields.add("start");
		fields.add("add");
		fields.add("del");
		fields.add("end");
		
		List<List<CellBean>> lists = new ArrayList<List<CellBean>>();
		
		//存在形式用途分类
		
		List<CellBean> startList = propertyReportMapper.groupPropertyByclassification(startDate);
		CellBean start1 = propertyReportMapper.allPropertyFirst(startDate);
		if(start1!=null){
			start1.setRowName("资产总计");
			startList.add(start1);
		}
		List<CellBean> endList = propertyReportMapper.groupPropertyByclassification(endDate);
		CellBean end1 = propertyReportMapper.allPropertyFirst(endDate);
		if(end1!=null){
			end1.setRowName("资产总计");
			endList.add(end1);
		}
		List<CellBean> addList = propertyReportMapper.groupPropertyByclassificationAdd(startDate, endDate);
		List<CellBean> delList = propertyReportMapper.groupPropertyByclassificationDel(startDate, endDate);
		lists.add(startList);
		lists.add(addList);
		lists.add(delList);
		lists.add(endList);
		
		ComponentJsonReaderBean JRB = new ComponentJsonReaderBean();
		String allKey=",资产总计,按存在形式分类,流动资产,固定资产,土地,房屋,车辆,通用办公设备,办公家具,其他固定资产,对外投资,无形资产,其他资产,按单位性质分类,行政单位,事业单位,按用途分类,自用,出租出借,其他,";
		List<Map<String, Object>> dataList = this.getDate(lists,fields,allKey);
		ComponentJsonReaderMetaDataBean metaDate = this.getMetaData(fields);
		JRB.setData(dataList);
		JRB.setMetaData(metaDate);
		return JRB;
	}
	
	public List<Map<String, Object>> getDate(List<List<CellBean>> lists,List<String> fields,String allKey){
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		List<String> keys = this.getKey(lists,allKey);//获取所有的key
		for(String key:keys ){
			Map<String, Object> row = new HashMap<String, Object>();
			row.put("key", key);
			/*for(int i=0;i<fields.size();i++){
				if(lists.)
			}*/
			for(int i=0;i<lists.size();i++){
				List<CellBean> l = lists.get(i);
				Map<String,Object> map = this.list2map(l);
				if(map.containsKey(key)){
					row.put(fields.get(i), map.get(key));
				}else{
					row.put(fields.get(i), "-");
				}
			}
			dataList.add(row);
		}
		return dataList;
	}
	
	//固定资产变更信息报表
	public ComponentJsonReaderBean propertyChange() throws ParseException{
		ComponentJsonReaderBean JRB = new ComponentJsonReaderBean();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new  Date());
		int year = calendar.get(Calendar.YEAR);

		
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd");
		String startStr=year+"-01-01";
		String endStr=year+1+"-01-01";
		
		Date startDate = sdf.parse(startStr);
		Date endDate = sdf.parse(endStr);
		List<String> fields = new ArrayList<String>();
		
		//合计
		fields.add("c1");
		//土地面积
		fields.add("c2");
		//土地原值
		fields.add("c3");
		//房屋面积
		fields.add("c4");
		//房屋原值
		fields.add("c5");
		//交通数量
		fields.add("c6");
		//交通原值
		fields.add("c7");
		//通用办公设备数量
		fields.add("c8");
		//通用办公设备原值
		fields.add("c9");
		//办公家具数量
		fields.add("c10");
		//办公家具原值
		fields.add("c11");
		//其他 数量
		fields.add("c12");
		//其他 原值
		fields.add("c13");
		
		List<List<CellBean>> lists = new ArrayList<List<CellBean>>();
		lists = this.listPropertyChange(startDate, endDate);
		
		
		String allKey=",年初固定资产,本年度增加固定资产,购置,部门内部调剂,接收捐赠,置换,部门外调入,其他,本年度减少固定资产,报废,年末固定资产,";
		List<Map<String, Object>> dataList = this.getDate(lists,fields,allKey);
		ComponentJsonReaderMetaDataBean metaDate = this.getMetaData(fields);
		JRB.setData(dataList);
		JRB.setMetaData(metaDate);
		return JRB;
	}
	
	public List<List<CellBean>> listPropertyChange(Date startDate,Date endDate){
		List<List<CellBean>> list = new ArrayList<List<CellBean>>();
		
		//列1 合计
		List<CellBean> c1 = new ArrayList<CellBean>();
		CellBean c1r1 = propertyReportMapper2.propertyChange1(startDate, null);
		c1 .add(c1r1);
		
		List<CellBean> c1r2 = propertyReportMapper2.propertyChange5(startDate,endDate, null);
		c1 .addAll(c1r2);
		
		List<CellBean> c1r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,null);
		c1 .addAll(c1r3to7);
		
		
		CellBean c1r11 = propertyReportMapper2.propertyChange18(startDate, endDate);
		//9
		CellBean c1r9 = new CellBean();
		c1r9.setRowName("本年度减少固定资产");
		c1r9.setValue(c1r11.getValue());
		c1 .add(c1r9);
		c1 .add(c1r11);
		
		CellBean c1r17 = propertyReportMapper2.propertyChange1(endDate, null);
		c1r17.setRowName("年末固定资产");
		c1 .add(c1r17);
		list.add(c1);
		
		//列2 土地面积
		List<CellBean> c2 = new ArrayList<CellBean>();
		CellBean c2r1 = propertyReportMapper2.propertyChange12(startDate);
		c2 .add(c2r1);
		
		CellBean c2r2 = propertyReportMapper2.propertyChange13(startDate,endDate);
		c2 .add(c2r2);
		
		List<CellBean> c2r3to7 = propertyReportMapper2.propertyChange14(startDate, endDate);
		c2 .addAll(c2r3to7);
		
		CellBean c2r11 = propertyReportMapper2.propertyChange19(startDate, endDate);
		//9
		CellBean c2r9 = new CellBean();
		c2r9.setRowName("本年度减少固定资产");
		c2r9.setValue(c2r9.getValue());
		c2 .add(c2r9);
		c2 .add(c2r11);
		
		CellBean c2r17 = propertyReportMapper2.propertyChange12(endDate);
		c2r17.setRowName("年末固定资产");
		c2 .add(c2r17);
		list.add(c2);
		//列3 土地原值
		List<CellBean> c3 = new ArrayList<CellBean>();
		CellBean c3r1 = propertyReportMapper2.propertyChange1(startDate, "土地");
		c3 .add(c3r1);
		
		List<CellBean> c3r2 = propertyReportMapper2.propertyChange5(startDate,endDate, "土地");
		if(c3r2!=null)
			c3 .addAll(c3r2);
		
		List<CellBean> c3r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,"土地");
		c3 .addAll(c3r3to7);
		
		CellBean c3r11 = propertyReportMapper2.propertyChange22(startDate, endDate, "土地");
		//9
		CellBean c3r9 = new CellBean();
		c3r9.setRowName("本年度减少固定资产");
		c3r9.setValue(c3r9.getValue());
		c3 .add(c3r9);
		c3 .add(c3r11);
		
		CellBean c3r17 = propertyReportMapper2.propertyChange1(endDate, "土地");
		c3r17.setRowName("年末固定资产");
		c3 .add(c3r17);
		list.add(c3);
		
		//列4 房屋面积
		List<CellBean> c4 = new ArrayList<CellBean>();
		CellBean c4r1 = propertyReportMapper2.propertyChange15(startDate);
		c4 .add(c4r1);
		
		CellBean c4r2 = propertyReportMapper2.propertyChange16(startDate,endDate);
		c4 .add(c4r2);
		
		List<CellBean> c4r3to7 = propertyReportMapper2.propertyChange17(startDate,endDate);
		c4 .addAll(c4r3to7);
		
		CellBean c4r11 = propertyReportMapper2.propertyChange20(startDate, endDate);
		//9
		CellBean c4r9 = new CellBean();
		c4r9.setRowName("本年度减少固定资产");
		c4r9.setValue(c4r9.getValue());
		c4 .add(c4r9);
		c4 .add(c4r11);
		
		CellBean c4r17 = propertyReportMapper2.propertyChange15(endDate);
		c4r17.setRowName("年末固定资产");
		c4 .add(c3r17);
		list.add(c4);
		//列5 房屋原值
		List<CellBean> c5 = new ArrayList<CellBean>();
		CellBean c5r1 = propertyReportMapper2.propertyChange1(startDate, "房屋");
		c5 .add(c5r1);
		
		List<CellBean> c5r2 = propertyReportMapper2.propertyChange5(startDate,endDate, "房屋");
		c5 .addAll(c5r2);
		
		List<CellBean> c5r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,"房屋");
		c5 .addAll(c5r3to7);
		
		CellBean c5r11 = propertyReportMapper2.propertyChange22(startDate, endDate,"房屋");
		//9
		CellBean c5r9 = new CellBean();
		c5r9.setRowName("本年度减少固定资产");
		c5r9.setValue(c5r9.getValue());
		c5 .add(c5r9);
		c5 .add(c5r11);
		
		CellBean c5r17 = propertyReportMapper2.propertyChange1(endDate, "房屋");
		c5r17.setRowName("年末固定资产");
		c5 .add(c5r17);
		list.add(c5);
		
		//列6 交通数量
		List<CellBean> c6 = new ArrayList<CellBean>();
		CellBean c6r1 = propertyReportMapper2.propertyChange2(startDate, "交通");
		c6 .add(c6r1);
		
		CellBean c6r2 = propertyReportMapper2.propertyChange7(startDate,endDate, "交通");
		c6 .add(c6r2);
		
		List<CellBean> c6r3to7 = propertyReportMapper2.propertyChange11(startDate,endDate,"交通");
		c6 .addAll(c6r3to7);
		
		CellBean c6r11 = propertyReportMapper2.propertyChange21(startDate, endDate,"交通");
		//9
		CellBean c6r9 = new CellBean();
		c6r9.setRowName("本年度减少固定资产");
		c6r9.setValue(c6r9.getValue());
		c6 .add(c6r9);
		c6 .add(c6r11);
		
		CellBean c6r17 = propertyReportMapper2.propertyChange2(endDate, "交通");
		c6r17.setRowName("年末固定资产");
		c6 .add(c6r17);
		list.add(c6);
		
		//列7 交通原值
		List<CellBean> c7 = new ArrayList<CellBean>();
		CellBean c7r1 = propertyReportMapper2.propertyChange1(startDate, "交通");
		c7 .add(c7r1);
		
		List<CellBean> c7r2 = propertyReportMapper2.propertyChange5(startDate,endDate, "交通");
		c7 .addAll(c7r2);
		
		List<CellBean> c7r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,"交通");
		c7 .addAll(c7r3to7);
		
		CellBean c7r11 = propertyReportMapper2.propertyChange22(startDate, endDate,"交通");
		//9
		CellBean c7r9 = new CellBean();
		c7r9.setRowName("本年度减少固定资产");
		c7r9.setValue(c7r9.getValue());
		c7 .add(c7r9);
		c7 .add(c7r11);
		
		CellBean c7r17 = propertyReportMapper2.propertyChange1(endDate, "交通");
		c7r17.setRowName("年末固定资产");
		c7 .add(c7r17);
		list.add(c7);
		
		//列8 通用办公设备数量
		List<CellBean> c8 = new ArrayList<CellBean>();
		CellBean c8r1 = propertyReportMapper2.propertyChange2(startDate, "通用办公设备");
		c8 .add(c8r1);
		
		CellBean c8r2 = propertyReportMapper2.propertyChange7(startDate,endDate, "通用办公设备");
		c8 .add(c8r2);
		
		List<CellBean> c8r3to7 = propertyReportMapper2.propertyChange11(startDate,endDate,"通用办公设备");
		c8 .addAll(c8r3to7);
		
		CellBean c8r11 = propertyReportMapper2.propertyChange21(startDate, endDate,"通用办公设备");
		//9
		CellBean c8r9 = new CellBean();
		c8r9.setRowName("本年度减少固定资产");
		c8r9.setValue(c8r9.getValue());
		c8 .add(c8r9);
		c8 .add(c8r11);
		
		CellBean c8r17 = propertyReportMapper2.propertyChange2(endDate, "通用办公设备");
		c8r17.setRowName("年末固定资产");
		c8 .add(c8r17);
		list.add(c8);
		
		//列7 通用办公设备原值
		List<CellBean> c9 = new ArrayList<CellBean>();
		CellBean c9r1 = propertyReportMapper2.propertyChange1(startDate, "通用办公设备");
		c9 .add(c9r1);
		
		List<CellBean> c9r2 = propertyReportMapper2.propertyChange5(startDate,endDate, "通用办公设备");
		c9 .addAll(c9r2);
		
		List<CellBean> c9r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,"通用办公设备");
		c9 .addAll(c9r3to7);
		
		CellBean c9r11 = propertyReportMapper2.propertyChange22(startDate, endDate,"通用办公设备");
		//9
		CellBean c9r9 = new CellBean();
		c9r9.setRowName("本年度减少固定资产");
		c9r9.setValue(c9r9.getValue());
		c9 .add(c9r9);
		c9 .add(c9r11);
		
		CellBean c9r17 = propertyReportMapper2.propertyChange1(endDate, "通用办公设备");
		c9r17.setRowName("年末固定资产");
		c9 .add(c9r17);
		list.add(c9);
		
		//列10 办公家具数量
		List<CellBean> c10 = new ArrayList<CellBean>();
		CellBean c10r1 = propertyReportMapper2.propertyChange2(startDate, "办公家具");
		c10 .add(c10r1);
		
		CellBean c10r2 = propertyReportMapper2.propertyChange7(startDate,endDate, "办公家具");
		c10 .add(c10r2);
		
		List<CellBean> c10r3to7 = propertyReportMapper2.propertyChange11(startDate,endDate,"办公家具");
		c10 .addAll(c10r3to7);
		
		CellBean c10r11 = propertyReportMapper2.propertyChange21(startDate, endDate,"办公家具");
		//9
		CellBean c10r9 = new CellBean();
		c10r9.setRowName("本年度减少固定资产");
		c10r9.setValue(c10r9.getValue());
		c10 .add(c10r9);
		c10 .add(c10r11);
		
		CellBean c10r17 = propertyReportMapper2.propertyChange2(endDate, "办公家具");
		c10r17.setRowName("年末固定资产");
		c10 .add(c10r17);
		list.add(c10);
		
		//列11  办公家具原值
		List<CellBean> c11 = new ArrayList<CellBean>();
		CellBean c11r1 = propertyReportMapper2.propertyChange1(startDate, "办公家具");
		c11 .add(c11r1);
		
		List<CellBean> c11r2 = propertyReportMapper2.propertyChange5(startDate,endDate, "办公家具");
		c11 .addAll(c11r2);
		
		List<CellBean> c11r3to7 = propertyReportMapper2.propertyChange9(startDate,endDate,"办公家具");
		c11 .addAll(c11r3to7);
		
		CellBean c11r11 = propertyReportMapper2.propertyChange22(startDate, endDate,"办公家具");
		//9
		CellBean c11r9 = new CellBean();
		c11r9.setRowName("本年度减少固定资产");
		c11r9.setValue(c11r9.getValue());
		c11 .add(c11r9);
		c11 .add(c11r11);
		
		CellBean c11r17 = propertyReportMapper2.propertyChange1(endDate, "办公家具");
		c11r17.setRowName("年末固定资产");
		c11 .add(c11r17);
		list.add(c11);

		//列12 其他数量
		List<CellBean> c12 = new ArrayList<CellBean>();
		CellBean c12r1 = propertyReportMapper2.propertyChange4(startDate, "('土地','房屋','通用办公设备','办公家具')");
		c12 .add(c12r1);
		
		CellBean c12r2 = propertyReportMapper2.propertyChange8(startDate,endDate, "('土地','房屋','通用办公设备','办公家具')");
		c12 .add(c12r2);
		
		List<CellBean> c12r3to7 = propertyReportMapper2.propertyChange111(startDate,endDate,"('土地','房屋','通用办公设备','办公家具')");
		c12 .addAll(c12r3to7);
		
		CellBean c12r11 = propertyReportMapper2.propertyChange23(startDate, endDate,"('土地','房屋','通用办公设备','办公家具')");
		//9
		CellBean c12r9 = new CellBean();
		c12r9.setRowName("本年度减少固定资产");
		c12r9.setValue(c12r11.getValue());
		c12 .add(c12r9);
		c12 .add(c12r11);
		
		CellBean c12r17 = propertyReportMapper2.propertyChange4(endDate, "('土地','房屋','通用办公设备','办公家具')");
		c12r17.setRowName("年末固定资产");
		c12 .add(c12r17);
		list.add(c12);
		
		//列13 其他原值
		List<CellBean> c13 = new ArrayList<CellBean>();
		CellBean c13r1 = propertyReportMapper2.propertyChange3(startDate, "('土地','房屋','通用办公设备','办公家具')");
		c13 .add(c13r1);
		
		CellBean c13r2 = propertyReportMapper2.propertyChange6(startDate,endDate, "('土地','房屋','通用办公设备','办公家具')");
		c13 .add(c13r2);
		
		List<CellBean> c13r3to7 = propertyReportMapper2.propertyChange10(startDate,endDate,"('土地','房屋','通用办公设备','办公家具')");
		c13 .addAll(c13r3to7);
		
		CellBean c13r11 = propertyReportMapper2.propertyChange24(startDate, endDate,"('土地','房屋','通用办公设备','办公家具')");
		//9
		CellBean c13r9 = new CellBean();
		c13r9.setRowName("本年度减少固定资产");
		c13r9.setValue(c13r9.getValue());
		c13 .add(c13r9);
		c13 .add(c13r11);
		
		CellBean c13r17 = propertyReportMapper2.propertyChange3(endDate, "('土地','房屋','通用办公设备','办公家具')");
		c13r17.setRowName("年末固定资产");
		c13.add(c13r17);
		list.add(c13);
		
		
		return list;
	}
	
	public HSSFWorkbook exportEXL1() throws FileNotFoundException, IOException, ParseException{
		int sheetNo = 0;
		int cellOffset = 0;
		int rowNo = 1;
		HSSFWorkbookUtil hSSFWorkbookUtil = new HSSFWorkbookUtil("");
		HSSFSheet sheet = hSSFWorkbookUtil.createSheet();
		HSSFWorkbook wb = hSSFWorkbookUtil.getWorkbook();
		
		HSSFCellStyle cs1 = wb.createCellStyle();
		HSSFFont fCol1 = wb.createFont(); // 字体对象，表头
		fCol1.setFontHeightInPoints((short) 29);
		fCol1.setBoldweight(fCol1.BOLDWEIGHT_BOLD);
		cs1.setFont(fCol1);
		cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs1.setFillBackgroundColor((short) 16);
		
		HSSFCellStyle cs2 = wb.createCellStyle();
		HSSFFont fCol2 = wb.createFont(); // 字体对象，表头
		fCol2.setFontHeightInPoints((short) 19);
		fCol2.setBoldweight(fCol2.BOLDWEIGHT_BOLD);
		cs2.setFont(fCol2);
		cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs2.setFillBackgroundColor((short) 16);
		
		HSSFCellStyle cs3 = wb.createCellStyle();
		HSSFFont fCol3 = wb.createFont(); // 字体对象，表头
		fCol3.setFontHeightInPoints((short) 13);
		//fCol.setBoldweight(fCol.BOLDWEIGHT_BOLD);
		cs3.setFont(fCol3);
		cs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs3.setFillBackgroundColor((short) 16);
		
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 0, 10000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 1, 3000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 2, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 3, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 4, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 5, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 6, 7000);
		
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "固定资产基本情况表", cs1);
		rowNo=rowNo+2;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "金额单位：元", cs3);
		rowNo++;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, "项目", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 1, "行次", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, "年初数", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "本年增加数", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, "本年减少数", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, "年末数", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "备    注", cs2);
		rowNo++;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, "栏次", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, "1", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "2", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, "3", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, "4", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "5", cs3);
		
		ComponentJsonReaderBean bean = this.propertyChange();
		List<Map<String, Object>> list = bean.getData();
		for(int i=0;i<list.size();i++){
			rowNo++;
			String key = String.valueOf(list.get(i).get("key"));
			String start = String.valueOf(list.get(i).get("start"));
			String add = String.valueOf(list.get(i).get("add"));
			String del = String.valueOf(list.get(i).get("del"));
			String end = String.valueOf(list.get(i).get("end"));
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, key, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 1, String.valueOf(i+1), cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, start, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, add, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, del, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, end, cs3);
		}
		return hSSFWorkbookUtil.getWorkbook();
	}
	
	public HSSFWorkbook exportEXL2() throws FileNotFoundException, IOException, ParseException{
		int sheetNo = 0;
		int cellOffset = 0;
		int rowNo = 1;
		HSSFWorkbookUtil hSSFWorkbookUtil = new HSSFWorkbookUtil("");
		HSSFSheet sheet = hSSFWorkbookUtil.createSheet();
		HSSFWorkbook wb = hSSFWorkbookUtil.getWorkbook();
		
		HSSFCellStyle cs1 = wb.createCellStyle();
		HSSFFont fCol1 = wb.createFont(); // 字体对象，表头
		fCol1.setFontHeightInPoints((short) 29);
		fCol1.setBoldweight(fCol1.BOLDWEIGHT_BOLD);
		cs1.setFont(fCol1);
		cs1.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs1.setFillBackgroundColor((short) 16);
		
		HSSFCellStyle cs2 = wb.createCellStyle();
		HSSFFont fCol2 = wb.createFont(); // 字体对象，表头
		fCol2.setFontHeightInPoints((short) 19);
		fCol2.setBoldweight(fCol2.BOLDWEIGHT_BOLD);
		cs2.setFont(fCol2);
		cs2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs2.setFillBackgroundColor((short) 16);
		
		HSSFCellStyle cs3 = wb.createCellStyle();
		HSSFFont fCol3 = wb.createFont(); // 字体对象，表头
		fCol3.setFontHeightInPoints((short) 13);
		//fCol.setBoldweight(fCol.BOLDWEIGHT_BOLD);
		cs3.setFont(fCol3);
		cs3.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cs3.setFillBackgroundColor((short) 16);
		
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 0, 10000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 1, 3000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 2, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 3, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 4, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 5, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 6, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 7, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 8, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 9, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 10, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 11, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 12, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 13, 7000);
		hSSFWorkbookUtil.setColumnWidth(sheetNo, 14, 7000);
		
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "固定资产变动情况表", cs1);
		rowNo=rowNo+2;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "金额单位：元", cs3);
		rowNo++;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, "项目", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 1, "行次", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, "合计", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "土地面积", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, "土地原值", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, "房屋面积", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "房屋原值", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 7, "交通数量", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 8, "交通原值", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 9, "通用办公设备数量", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 10, "通用办公设备原值", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 11, "办公家具数量", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 12, "办公家具原值", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 13, "其他数量", cs2);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 14, "其他原值", cs2);
		rowNo++;
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, "栏次", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, "1", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, "2", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, "3", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, "4", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, "5", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 7, "6", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 8, "7", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 9, "8", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 10, "9", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 11, "10", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 12, "11", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 13, "12", cs3);
		hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 14, "13", cs3);
		
		ComponentJsonReaderBean bean = this.propertyChange();
		List<Map<String, Object>> list = bean.getData();
		for(int i=0;i<list.size();i++){
			rowNo++;
			String key = String.valueOf(list.get(i).get("key"));
			key = key.equals("null")?"-":key;
			String c1 = String.valueOf(list.get(i).get("c1"));
			c1 = c1.equals("null")?"-":c1;
			String c2 = String.valueOf(list.get(i).get("c2"));
			c2 = c2.equals("null")?"-":c2;
			String c3 = String.valueOf(list.get(i).get("c3"));
			c3 = c3.equals("null")?"-":c3;
			String c4 = String.valueOf(list.get(i).get("c4"));
			c4 = c4.equals("null")?"-":c4;
			String c5 = String.valueOf(list.get(i).get("c5"));
			c5 = c5.equals("null")?"-":c5;
			String c6 = String.valueOf(list.get(i).get("c6"));
			c6 = c6.equals("null")?"-":c6;
			String c7 = String.valueOf(list.get(i).get("c7"));
			c7 = c7.equals("null")?"-":c7;
			String c8 = String.valueOf(list.get(i).get("c8"));
			c8 = c8.equals("null")?"-":c8;
			String c9 = String.valueOf(list.get(i).get("c9"));
			c9 = c9.equals("null")?"-":c9;
			String c10 = String.valueOf(list.get(i).get("c10"));
			c10 = c10.equals("null")?"-":c10;
			String c11 = String.valueOf(list.get(i).get("c11"));
			c11 = c11.equals("null")?"-":c11;
			String c12 = String.valueOf(list.get(i).get("c12"));
			c12 = c12.equals("null")?"-":c12;
			String c13 = String.valueOf(list.get(i).get("c13"));
			c13 = c13.equals("null")?"-":c13;
			
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 0, key, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 1, String.valueOf(i+1), cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 2, c1, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 3, c2, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 4, c3, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 5, c4, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 6, c5, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 7, c6, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 8, c7, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 9, c8, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 10, c9, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 11, c10, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 12, c11, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 13, c12, cs3);
			hSSFWorkbookUtil.setStringValue(sheetNo, rowNo, 14, c13, cs3);
			
		}
		return hSSFWorkbookUtil.getWorkbook();
	}
}
