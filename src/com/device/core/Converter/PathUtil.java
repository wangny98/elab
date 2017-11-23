package com.device.core.Converter;

import java.io.File;
import java.net.URISyntaxException;

public class PathUtil {

    public static String getDx(String storeName) {
        String dx = new String();
        int startDx = storeName.lastIndexOf(".");
        if (startDx == -1) {
            return "";
        }

        dx = storeName.substring(startDx + 1);

        return dx;
    }

    public static boolean isOffice(String dx) {
        if (!dx.equalsIgnoreCase("doc") && !dx.equalsIgnoreCase("docx") && !dx.equalsIgnoreCase("ppt")
                && !dx.equalsIgnoreCase("pptx") && !dx.equalsIgnoreCase("xls") && !dx.equalsIgnoreCase("xlsx")
                && !dx.equalsIgnoreCase("txt")) {
            return false;
        } else {
            return true;
        }
    }

    public static String getNoDx(String storeName) {
        String name = new String();
        int startDx = storeName.lastIndexOf(".");
        if (startDx == -1) {
            return storeName;
        }
        name = storeName.substring(0, startDx);
        return name;
    }

    /**
     * @return 获取web-info目录
     */
    public static String getWEBINFDir() {
        String path = null;
        try {
            path = PathUtil.class.getResource("").toURI().getPath();
            path = path.substring(0, path.indexOf("classes"));
            return path;
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 
     * @return WebRoot目录的绝对路径
     */
    public static String getWebRootDir() {
        String path = null;
        String folderPath = PathUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
        if (folderPath.indexOf("WEB-INF") > 0) {
            path = folderPath.substring(0, folderPath.indexOf("WEB-INF/classes"));
        }
        path = path.replaceAll("%20", " ");
        return path;
    }

    /**
     * @param args
     */
    public static String getWebRootDirFilePath(String dir) {
        String path = getWebRootDir() + dir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * @param args
     */
    public static String getWebInfoDirFilePath(String dir) {
        String path = getWEBINFDir() + dir;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

}
