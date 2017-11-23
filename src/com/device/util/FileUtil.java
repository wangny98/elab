package com.device.util;

import java.io.File;

public class FileUtil {
    public static void main(String[] args) {

        try {
            File f = new File("D://elab//science");
            f.mkdir();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
