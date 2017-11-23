package com.device.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.Region;

public class HSSFWorkbookUtil {

    //	private FileOutputStream mTargetExcelFile;

    private HSSFWorkbook mTemplateExcelWorkbook;

    private HSSFCellStyle mCellStyle;

    //    private int mDefaultFontSize = 12;

    //    private HashMap mStyleMap = new HashMap();

    private static final int BORDER_NO_BORDER = 0;

    private static final int BORDER_TOP = 1;

    private static final int BORDER_RIGHT = 2;

    private static final int BORDER_BOTTOM = 4;

    private static final int BORDER_LEFT = 8;

    private static final int TABLE_TOP = 1;

    private static final int TABLE_BOTTOM = 2;

    private static final int TABLE_LEFT = 4;

    private static final int TABLE_RIGHT = 8;

    private static final String DELIMITER = "|";

    public HSSFWorkbookUtil(String targetFile) throws FileNotFoundException, IOException {
        mTemplateExcelWorkbook = new HSSFWorkbook();
        //		mCellStyle = getDefaultStyle();
    }

    //    public void setDefaultFontSize(int defaultFontSize) {
    //        mDefaultFontSize = defaultFontSize;
    //    }

    public HSSFWorkbook getWorkbook() {
        return mTemplateExcelWorkbook;
    }

    public void setStyle(int sheetNo, int rowNo, short cellNo, HSSFCellStyle cellStyle) {
        HSSFCell cell = getCell(sheetNo, rowNo, cellNo);

        if (cellStyle == null) {
            cell.setCellStyle(mCellStyle);
        } else {
            cell.setCellStyle(cellStyle);
        }
    }

    public void setStyle(int sheetNo, int rowNo, int cellNo, HSSFCellStyle cellStyle) {
        setStyle(sheetNo, rowNo, (short) cellNo, cellStyle);
    }

    public void setIntValue(int sheetNo, int rowNo, int cellNo, String cellValue, HSSFCellStyle cellStyle) {
        try {
            setIntValue(sheetNo, rowNo, (short) cellNo, Integer.parseInt(cellValue), cellStyle);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setIntValue(int sheetNo, int rowNo, short cellNo, int cellValue, HSSFCellStyle cellStyle) {
        try {
            HSSFCell cell = getCell(sheetNo, rowNo, cellNo);

            if (cellStyle == null) {
                cell.setCellStyle(mCellStyle);
            } else {
                cell.setCellStyle(cellStyle);
            }
            cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
            cell.setCellValue(cellValue);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    //	public void setDoubleValue(int sheetNo, int rowNo, int cellNo,
    //			String cellValue, HSSFCellStyle cellStyle) {
    //		try {
    //			setDoubleValue(sheetNo, rowNo, (short) cellNo, Double
    //					.parseDouble(cellValue), cellStyle);
    //		} catch (Exception e) {
    //			// setStringValue(sheetNo, rowNo, (short) cellNo,
    //			// BatchProperties.getPropertyAsString("renal.report.common.error",
    //			// "Error"), cellStyle);
    //		}
    //	}
    //
    //	public void setDoubleValue(int sheetNo, int rowNo, short cellNo,
    //			double cellValue, HSSFCellStyle cellStyle) {
    //
    //		HSSFCell cell = getCell(sheetNo, rowNo, cellNo);
    //		if (cellStyle == null) {
    //			cell.setCellStyle(mCellStyle);
    //		} else {
    //			cell.setCellStyle(cellStyle);
    //		}
    //		cell.setCellType(HSSFCell.CELL_TYPE_NUMERIC);
    //		cell.setCellValue(cellValue);
    //	}

    public void setBooleanValue(int sheetNo, int rowNo, int cellNo, boolean cellValue) {
        setBooleanValue(sheetNo, rowNo, cellNo, cellValue, null);
    }

    public void setBooleanValue(int sheetNo, int rowNo, int cellNo, boolean cellValue, HSSFCellStyle cellStyle) {
        setBooleanValue(sheetNo, rowNo, (short) cellNo, cellValue, cellStyle);
    }

    public void setBooleanValue(int sheetNo, int rowNo, short cellNo, boolean cellValue, HSSFCellStyle cellStyle) {
        HSSFCell cell = getCell(sheetNo, rowNo, cellNo);

        if (cellStyle == null) {
            cell.setCellStyle(mCellStyle);
        } else {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellType(HSSFCell.CELL_TYPE_BOOLEAN);
        cell.setCellValue(cellValue);
    }

    public void setStringValue(int sheetNo, int rowNo, int cellNo, String cellValue) {
        setStringValue(sheetNo, rowNo, (short) cellNo, cellValue, null);
    }

    public void setStringValue(int sheetNo, int rowNo, int cellNo, String cellValue, HSSFCellStyle cellStyle) {
        setStringValue(sheetNo, rowNo, (short) cellNo, cellValue, cellStyle);
    }

    public void setStringValue(int sheetNo, int rowNo, short cellNo, String cellValue, HSSFCellStyle cellStyle) {
        HSSFCell cell = getCell(sheetNo, rowNo, cellNo);

        if (cellStyle == null) {
            cell.setCellStyle(mCellStyle);
        } else {
            cell.setCellStyle(cellStyle);
        }
        cell.setCellType(HSSFCell.CELL_TYPE_STRING);
        //		cell.setEncoding(HSSFCell.ENCODING_UTF_16);
        cell.setCellValue(cellValue);
    }

    public HSSFCell getCell(int sheetNo, int rowNo, int cellNo) {
        return getCell(sheetNo, rowNo, (short) cellNo);
    }

    public HSSFCell getCell(int sheetNo, int rowNo, short cellNo) {
        HSSFSheet sheet = getSheet(sheetNo);
        HSSFRow row = sheet.getRow(rowNo);
        if (row == null) {
            sheet.createRow(rowNo);
            row = sheet.getRow(rowNo);
        }
        return row.getCell(cellNo) == null ? row.createCell(cellNo) : row.getCell(cellNo);
    }

    /**
     * To obtain a worksheet if the specified index number exist. A new
     * worksheet is created if it does not exist.
     * 
     * @param strSheetName
     *            as an index for worksheet of workbook
     * @return existing or new worksheet
     */
    public HSSFSheet getSheet(String strSheetName) {
        return mTemplateExcelWorkbook.getSheet(strSheetName);
    }

    /**
     * To obtain a worksheet if the specified index number exist. A new
     * worksheet is created if it does not exist.
     * 
     * @param intSheet
     *            as an index for worksheet of workbook
     * @return existing or new worksheet
     */
    public HSSFSheet getSheet(int intSheet) {
        try {
            HSSFSheet hSSFSheet = mTemplateExcelWorkbook.getSheetAt(intSheet);
            if (null == hSSFSheet) {
                return mTemplateExcelWorkbook.createSheet();
            } else {
                return hSSFSheet;
            }
        } catch (IndexOutOfBoundsException e) {
            mTemplateExcelWorkbook.createSheet();
            return getSheet(intSheet);
        }
    }

    /**
     * To obtain a worksheet if the specified index number exist. A new
     * worksheet is created if it does not exist.
     * 
     * @param intSheet
     *            as an index for worksheet of workbook
     * @return existing or new worksheet
     */
    public String getSheetName(int intSheet) {
        try {
            return mTemplateExcelWorkbook.getSheetName(intSheet);
        } catch (IndexOutOfBoundsException e) {
            mTemplateExcelWorkbook.createSheet();
            return getSheetName(intSheet);
        }
    }

    public void setSheetName(int sheetNo, String sheetName) {
        mTemplateExcelWorkbook.setSheetName(sheetNo, sheetName);
    }

    public HSSFSheet createSheet() {
        return mTemplateExcelWorkbook.createSheet();
    }

    public HSSFSheet createSheet(String sheetName) {
        return mTemplateExcelWorkbook.createSheet(sheetName);
    }

    /**
     * To save and close the excel file.
     */
    //	public void save() throws IOException {
    //		mTemplateExcelWorkbook.write(mTargetExcelFile);
    //		mTargetExcelFile.close();
    //	}
    public HSSFCellStyle getCellStyle(int sheetNo, int rowNo, int cellNo) {
        return getCell(sheetNo, rowNo, cellNo).getCellStyle();
    }

    public String getStringValue(int sheet, int row, int col) {
        return getStringValue(sheet, row, (short) col);
    }

    private HSSFCell getCellForRead(int sheet, int row, short col) {
        HSSFSheet s = mTemplateExcelWorkbook.getSheetAt(sheet);
        HSSFRow r = s.getRow(row);
        if (r == null) {
            return null;
        }
        return r.getCell(col);
    }

    private String getStringValue(int sheet, int row, short col) {
        HSSFCell c = getCellForRead(sheet, row, col);
        if (c.getCellType() == HSSFCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(c.getNumericCellValue());
        } else if (c.getCellType() == HSSFCell.CELL_TYPE_STRING) {
            return c.getStringCellValue();
        } else if (c.getCellType() == HSSFCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(c.getBooleanCellValue());
        }
        return c.getStringCellValue();
    }

    public int getIntValue(int sheet, int row, int col) {
        return getIntValue(sheet, row, (short) col);
    }

    public int getIntValue(int sheet, int row, short col) {
        HSSFCell c = getCellForRead(sheet, row, col);
        return (int) c.getNumericCellValue();
    }

    public double getDoubleValue(int sheet, int row, int col) {
        return getDoubleValue(sheet, row, (short) col);
    }

    public double getDoubleValue(int sheet, int row, short col) {
        HSSFCell c = getCellForRead(sheet, row, col);
        return c.getNumericCellValue();
    }

    public Date getDateValue(int sheet, int row, int col) {
        return getDateValue(sheet, row, (short) col);
    }

    public Date getDateValue(int sheet, int row, short col) {
        HSSFCell c = getCellForRead(sheet, row, col);
        return c.getDateCellValue();
    }

    public int getNumberOfSheets() {
        return mTemplateExcelWorkbook.getNumberOfSheets();
    }

    public int getNumberOfRows(int sheetNo) {
        return mTemplateExcelWorkbook.getSheetAt(sheetNo).getPhysicalNumberOfRows();
    }

    public int getNumberOfCells(int sheetNo, int rowNo) {
        return mTemplateExcelWorkbook.getSheetAt(sheetNo).getRow(rowNo).getPhysicalNumberOfCells();
    }

    public int getCellType(int sheetNo, int rowNo, int cellNo) {
        return getCellType(sheetNo, rowNo, (short) cellNo);
    }

    public int getCellType(int sheetNo, int rowNo, short cellNo) {
        return mTemplateExcelWorkbook.getSheetAt(sheetNo).getRow(rowNo).getCell(cellNo).getCellType();
    }

    public boolean isNullCell(int sheetNo, int rowNo, int cellNo) {
        return isNullCell(sheetNo, rowNo, (short) cellNo);
    }

    public boolean isNullCell(int sheetNo, int rowNo, short cellNo) {
        HSSFCell c = mTemplateExcelWorkbook.getSheetAt(sheetNo).getRow(rowNo).getCell(cellNo);
        return c == null;
    }

    public boolean isNullRow(int sheetNo, int rowNo) {
        HSSFRow r = mTemplateExcelWorkbook.getSheetAt(sheetNo).getRow(rowNo);
        return r == null;
    }

    public void setColumnWidth(int sheet, int column, int width) {
        getSheet(sheet).setColumnWidth((short) column, (short) width);
    }

    public void setRowHeightInPoints(int sheet, int row, float height) {
        getSheet(sheet).getRow(row).setHeightInPoints(height);
    }

    public int getBorder(int horizontalPosition, int verticalPosition) {
        int border = BORDER_NO_BORDER;

        // Top, need draw top border
        if ((verticalPosition & TABLE_TOP) > 0) {
            border += BORDER_TOP;
        }
        // Bottom, need draw bottom border
        if ((verticalPosition & TABLE_BOTTOM) > 0) {
            border += BORDER_BOTTOM;
        }

        // First column, need draw left border
        if ((horizontalPosition & TABLE_LEFT) > 0) {
            border += BORDER_LEFT;
        }
        // Right column, need draw right border
        if ((horizontalPosition & TABLE_RIGHT) > 0) {
            border += BORDER_RIGHT;
        }

        return border;
    }

    public HSSFCellStyle createCellStyle() {
        return mTemplateExcelWorkbook.createCellStyle();
    }

    public HSSFFont createFont() {
        return mTemplateExcelWorkbook.createFont();
    }

    public HSSFCellStyle getStyle(int sheetNo, int rowNo, int cellNo) {
        return getCell(sheetNo, rowNo, cellNo).getCellStyle();
    }

    /**
     * Retrieve HSSFCellStyle from the style pool
     * 
     * @param sheetNo
     *            Sheet No
     * @param rowFrom
     *            The Row No merge from
     * @param cellFrom
     *            The Cell No merge from
     * @param rowTo
     *            The Row No merge to
     * @param cellTo
     *            The Cell No merge to
     * @see org.apache.poi.hssf.util.Region
     */
    public void mergeCell(int sheetNo, int rowFrom, int cellFrom, int rowTo, int cellTo) {
        HSSFSheet sheet = getSheet(sheetNo);
        sheet.addMergedRegion(new Region(rowFrom, (short) cellFrom, rowTo, (short) cellTo));
    }

}
