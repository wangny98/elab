package com.device.core.Converter;

import java.io.File;

import com.sun.star.rdf.RepositoryException;

public class FileConverTerThread implements Runnable {

    private String filePath;

    private String storeName;

    public FileConverTerThread(String filePath, String storeName) {
        this.filePath = filePath;
        this.storeName = storeName;
    }

    public void run() {
        // TODO Auto-generated method stub
        synchronized (this) {
            convert2(this.filePath, this.storeName);
        }
    }

    public void convert2(String filePath, String storeName) {
        String docType = PathUtil.getDx(storeName);
        String fileName = PathUtil.getNoDx(storeName);
        FileConverter converter = null;
        File tmpFile = null;
        if (docType.equalsIgnoreCase("doc") || docType.equalsIgnoreCase("docx") || docType.equalsIgnoreCase("ppt")
                || docType.equalsIgnoreCase("pptx") || docType.equalsIgnoreCase("xls")
                || docType.equalsIgnoreCase("xlsx") || docType.equalsIgnoreCase("txt")) {
            try {
                converter = FileConverterFactory.getOfficeConverter();
                File pdfRecord = new File(filePath, "pdf");

                File destFile = new File(pdfRecord.getPath(), fileName + ".pdf");
                File srcFile = new File(filePath, storeName);
                System.out.println("[flex 预览]:获取openoffice格式转换工具");
                //执行pdf转换
                if (converter.convert(srcFile, destFile)) {
                    tmpFile = destFile;
                    System.out.println("[flex 预览]:将office文件" + fileName + "转换为pdf...");
                    converter = FileConverterFactory.getSwfConverter(ConvertConstant.PDF2SWF);
                    File swfRecord = new File(filePath, "swf");
                    swfRecord.mkdir();
                    File swfFile = new File(swfRecord, fileName + ".swf");
                    boolean isSuccess = convert2Swf(tmpFile, swfFile, converter);
                    if (isSuccess) {
                        /*  if (tmpFile.exists()) {
                              tmpFile.delete();
                          }
                          if (swfFile.exists()) {
                              swfFile.delete();
                          }
                        */}
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("[flex 预览]:不是Office文档");
        }
    }

    private boolean convert2Swf(File tmpFile, File swfFile, FileConverter converter) throws RepositoryException {
        System.out.println("[flex 预览]:将文件转换为swf文件");
        if (swfFile.exists()) {
            swfFile.delete();
        }
        if (null == converter) {
            return false;
        }
        return converter.convert(tmpFile, swfFile);
    }
}
