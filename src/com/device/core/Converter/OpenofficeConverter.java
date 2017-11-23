package com.device.core.Converter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;
import java.util.Date;

import org.apache.log4j.Logger;
import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.device.util.PathHelper;
import com.device.util.ReadServerParam;

public class OpenofficeConverter implements FileConverter {
    /**
     * openoffice的安装目录
     */
    public static final String OFFICE_HOME;
    private OfficeManager officeManager;
    private int port[] = { 8100 };
    private static OpenofficeConverter openofficeConverter = null;
    DocumentConverter converter;
    private DefaultOfficeManagerConfiguration configuration;
    static {
        OFFICE_HOME = ReadServerParam.getMessage("convert.office.path").trim();
    }

    public static synchronized OpenofficeConverter getInstance() {
        if (openofficeConverter == null) {
            openofficeConverter = new OpenofficeConverter();
        }
        return openofficeConverter;
    }

    private OpenofficeConverter() {
        System.out.println("[openoffice]:初始化配置");
        setup();
    }

    private void setup() {
        configuration = new DefaultOfficeManagerConfiguration();
        configuration.setOfficeHome(OFFICE_HOME);// 设置OpenOffice.org安装目录
        configuration.setPortNumbers(port); // 设置转换端口，默认为8100
        configuration.setMaxTasksPerProcess(3);
        configuration.setTaskExecutionTimeout(1000 * 60 * 3L);
        configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);
    }

    public boolean convert(File src, File dest) {
        //return office2PDF(src, dest);
        return convert2PDF(src, dest);
    }

    public boolean convert2PDF(File src, File dest) {
        Date start = new Date();
        try {
            System.out.println("src:" + (src == null) + ",dest:" + (dest == null));
            startService();
            Logger.getLogger(OpenofficeConverter.class).info("进行文档转换:" + src.getName());
            if (converter == null) {
                OfficeDocumentConverter converter_1 = new OfficeDocumentConverter(officeManager);
                synchronized (src) {
                    converter_1.convert(src, dest);
                }
            } else {
                synchronized (src) {
                    converter.convert(src, dest);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("对不起，还未安装openoffice:" + e.getMessage());
            return false;
        } finally {
            //stopService();
        }
        long l = (start.getTime() - new Date().getTime());
        long day = l / (24 * 60 * 60 * 1000);
        long hour = (l / (60 * 60 * 1000) - day * 24);
        long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
        long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
        Logger.getLogger(OpenofficeConverter.class).info("生成" + dest.getName() + "耗费：" + min + "分" + s + "秒");
        if (dest.exists()) {
            return true;
        } else {
            System.out.println("office文件内容可能已经损坏，无法完成转换");
            return false;
        }
    }

    private boolean isStarted() throws IOException, InterruptedException {
        String command;
        if (!new PathHelper().isLinux()) {
            command = "cmd.exe /c  tasklist";
        } else {
            command = "ps aux|grep  java|grep -v grep|wc -l";
        }
        Process ps = Runtime.getRuntime().exec(command);
        int ptr = 0;
        BufferedInputStream br = new BufferedInputStream(ps.getInputStream());
        BufferedInputStream errbr = new BufferedInputStream(ps.getInputStream());
        StringBuffer buffer = new StringBuffer();
        synchronized (br) {
            while ((ptr = br.read()) != -1) {
                buffer.append((char) ptr);
            }
            synchronized (errbr) {
                while ((ptr = errbr.read()) != -1) {
                    System.out.println("err:" + ptr);
                }
            }
        }
        ps.waitFor();
        System.out.println("buffer.toString():" + buffer.toString());
        if (!new PathHelper().isLinux()) {
            if (buffer.toString().indexOf("office") >= 0) {
                return true;
            }
            return false;
        }
        if (!buffer.toString().equals("0")) {
            return true;
        }
        return false;
    }

    public void startService() {
        try {
            System.out.println("[openoffice]:启动服务");
            synchronized (officeManager) {
                if (null == converter) {
                    OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1", 8100);
                    connection.connect();
                    converter = new OpenOfficeDocumentConverter(connection);
                }
            }
            System.out.println("office转换服务启动成功!");
        } catch (Exception e) {
            System.out.println("office转换服务启动失败!详细信息:" + e);
            officeManager = configuration.buildOfficeManager();
            officeManager.start();
        }

    }

    public boolean office2PDF(File inputFile, File outputFile) {
        try {
            // 如果目标路径不存在, 则新建该路径  
            if (!outputFile.getParentFile().exists()) {
                outputFile.getParentFile().mkdirs();
            }
            //安装openoffice的主机
            String host = ReadServerParam.getMessage("convert.office.hostIp");
            System.out.println("主机：" + host);
            String command;
            // 启动OpenOffice的服务  
            if (!new PathHelper().isLinux()) {
                command = OFFICE_HOME + File.separator + "program" + File.separator
                        + "soffice.exe -headless -accept=\"socket,host=" + host + ",port=8100;urp;\"";
            } else {
                command = OFFICE_HOME + File.separator + "program" + File.separator
                        + "soffice -headless -accept=\"socket,host=" + host + ",port=8100;urp;\"";
            }
            Process pro = Runtime.getRuntime().exec(command);
            // connect to an OpenOffice.org instance running on port 8100  convert.office.hostIp
            OpenOfficeConnection connection = new SocketOpenOfficeConnection(host, 8100);
            connection.connect();
            // convert  
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);

            // close the connection  
            connection.disconnect();
            // 关闭OpenOffice服务的进程  
            pro.destroy();

            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (ConnectException e) {
            System.out.println("对不起，还未安装openoffice:" + e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("office文件内容可能已经损坏，无法完成转换");
            e.printStackTrace();
        }

        return false;
    }

    public void stopService() {
        System.out.println("关闭office转换服务....");
        if (officeManager != null) {
            officeManager.stop();
        }
        System.out.println("关闭office转换成功!");
    }

    public static void main(String[] args) {
        OpenofficeConverter
                .getInstance()
                .convert2PDF(
                        new File(
                                "D:\\elab\\science\\07bb0cbf10c6499a91c7c6b8cf1b60aa\\8db27e427e04461eaebc6b9e296136a1.xlsx"),
                        new File(
                                "D:\\elab\\science\\07bb0cbf10c6499a91c7c6b8cf1b60aa\\pdf\\8db27e427e04461eaebc6b9e296136a1.pdf"));
    }
}
