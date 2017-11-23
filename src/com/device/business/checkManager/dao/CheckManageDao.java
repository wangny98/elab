package com.device.business.checkManager.dao;

import java.util.ArrayList;
import java.util.Date;
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

import com.agile.erms.utils.CommUtils;
import com.device.business.assetManage.bean.AssetBean;
import com.device.business.checkManager.bean.CheckManageBean;
import com.device.business.checkManager.bean.SearchBean;
import com.device.business.checkManager.dao.mapper.CheckManageMapper;
import com.device.business.checkManager.element.CheckManageElement;
import com.device.business.login.element.LoginElement;
import com.device.filter.AuthorityContext;
import com.device.util.PrimaryKeyUtil;

public class CheckManageDao {
	@Autowired
	private CheckManageMapper checkManageMapper;

	public List<AssetBean> queryPd(CheckManageBean checkManageBean ,int start, int limit) {
		List<AssetBean> list = new ArrayList<AssetBean>();
		RowBounds rows = new RowBounds(start, limit);
		list = checkManageMapper.queryPd(checkManageBean, rows);
		return list;
	}
	
	public int countPd(CheckManageBean checkManageBean){
		return checkManageMapper.countPd(checkManageBean);
	}
	
	public List<AssetBean> queryPd(CheckManageBean checkManageBean) {
		List<AssetBean> list = new ArrayList<AssetBean>();
		list = checkManageMapper.queryPd(checkManageBean);
		return list;
	}

	public int countProperty(CheckManageElement element){
		return checkManageMapper.countProperty();
	}
	
	public int addPd(CheckManageElement checkManageElement ){
		return checkManageMapper.addPd(checkManageElement);
	}
	
	public int isPdExist(String fanumber){
		return checkManageMapper.isPdExist(fanumber);
	}
	
	public int updPd(CheckManageElement checkManageElement ){
		return checkManageMapper.updPd(checkManageElement);
	}
/*
	public int addPd(CheckManageBean bean) {

		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setIspd(2);
		if (CommUtils.isNullOrBlank(bean.getPdname())) {
			LoginElement login = AuthorityContext.getLoginElement();
			bean.setPdname(login.getUserFullName());
		}
		bean.setPddata(new Date());

		return checkManageMapper.addPd(bean.getId(), bean.getProperty_id(),
				bean.getIspd(), bean.getPdname(), bean.getPddata(), bean.getRemark());
	}
	
	public int addQc(CheckManageBean bean){
		bean.setId(PrimaryKeyUtil.getSeq());
		bean.setIsqc(2);
		if (CommUtils.isNullOrBlank(bean.getQcname())) {
			LoginElement login = AuthorityContext.getLoginElement();
			bean.setQcname(login.getUserFullName());
		}
		bean.setQcdata(new Date());
		return checkManageMapper.addQc(bean.getId(), bean.getProperty_id(),
				bean.getIsqc(), bean.getQcname(), bean.getQcdata(), bean.getRemark());
	}
*/	
	
	public HSSFWorkbook exportPd(String[] sheet, String[] title,
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
				cell.setCellValue("资产盘点详单");
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
					if (vo.getPdname() != null) {
						cell = row.createCell((short) 23);// ������i+1�е�0��
						cell.setCellValue(vo.getPdname());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getPddate() != null) {
						cell = row.createCell((short) 24);// ������i+1�е�0��
						cell.setCellValue(vo.getPddate());// ���õ�i+1�е�0�е�ֵ
						cell.setCellStyle(style);// ���÷��
					}
					if (vo.getPdresultText() != null) {
						cell = row.createCell((short) 25);// ������i+1�е�0��
						cell.setCellValue(vo.getPdresultText());// ���õ�i+1�е�0�е�ֵ
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
