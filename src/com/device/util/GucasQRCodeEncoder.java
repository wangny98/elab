package com.device.util;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;

import com.swetake.util.Qrcode;
  
public class GucasQRCodeEncoder {  
      
    public static int max_data_size_small = 84;  
    public static int max_data_size_large = 500;  
      
    /** 
     *  
     * @param srcValue 
     * @param qrcodePicfilePath 
     * @return 
     */  
    public static boolean encode(String srcValue, String qrcodePicfilePath, OutputStream out) {  
        //return  encode_500(srcValue, qrcodePicfilePath, out);  
        return  encode_84(srcValue, qrcodePicfilePath, out);  
    }  
      
    /** 
     * Encoding the information to a QRCode, size of the information must be less than 84 byte. 
     * @param srcValue 
     * @param qrcodePicfilePath 
     * @return 
     */  
    public static boolean encode_84(String srcValue, String qrcodePicfilePath, OutputStream out) {  
        int MAX_DATA_LENGTH = max_data_size_small; 
        byte[] d = srcValue.getBytes();  
        int dataLength = d.length;  
        int imageWidth = 113; 
        int imageHeight = imageWidth;  
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = bi.createGraphics();  
        g.setBackground(Color.WHITE);  
        g.clearRect(0, 0, imageWidth, imageHeight);  
        g.setColor(Color.BLACK);  
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {  
            Qrcode qrcode = new Qrcode();  
            qrcode.setQrcodeErrorCorrect('M'); 
            qrcode.setQrcodeEncodeMode('B'); 
            qrcode.setQrcodeVersion(5);  
            boolean[][] b = qrcode.calQrcode(d);  
            int qrcodeDataLen = b.length;  
            for (int i = 0; i < qrcodeDataLen; i++) {  
                for (int j = 0; j < qrcodeDataLen; j++) {  
                    if (b[j][i]) {  
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3); 
                    }  
                }  
            }  
        } else {  
            System.out.println("Generate QRCode image error! Data size is " + dataLength +", it is lager than 84 bytes.");  
            return false;  
        }  
        g.dispose();  
        bi.flush();  
        /* generate image */  
        File f = new File(qrcodePicfilePath);  
        String suffix = f.getName().substring(f.getName().indexOf(".")+1, f.getName().length());  
        try {  
            ImageIO.write(bi, suffix, out); //"png"  
        } catch (IOException ioe) {  
            System.out.println("Generate QRCode image error!" + ioe.getMessage());  
            return false;  
        }  
  
        return true;  
    }  
      
    /** 
     * Encoding the information to a QRCode, size of the information must be less tah 500 byte. 
     * @param srcValue 
     * @param qrcodePicfilePath 
     * @return 
     */  
    public static boolean encode_500(String srcValue, String qrcodePicfilePath, OutputStream out) {  
        int MAX_DATA_LENGTH = max_data_size_large; 
        byte[] d = srcValue.getBytes();  
        int dataLength = d.length;  
        int imageWidth = 269; 
        int imageHeight = imageWidth;  
        BufferedImage bi = new BufferedImage(imageWidth, imageHeight,  
                BufferedImage.TYPE_INT_RGB);  
        Graphics2D g = bi.createGraphics();  
        g.setBackground(Color.WHITE);  
        g.clearRect(0, 0, imageWidth, imageHeight);  
        g.setColor(Color.BLACK);  
        if (dataLength > 0 && dataLength <= MAX_DATA_LENGTH) {  
             
            Qrcode qrcode = new Qrcode();  
            qrcode.setQrcodeErrorCorrect('M'); 
            qrcode.setQrcodeEncodeMode('B'); 
            qrcode.setQrcodeVersion(18);                                            
            
            boolean[][] b = qrcode.calQrcode(d);  
            int qrcodeDataLen = b.length;  
            for (int i = 0; i < qrcodeDataLen; i++) {  
                for (int j = 0; j < qrcodeDataLen; j++) {  
                    if (b[j][i]) {  
                        g.fillRect(j * 3 + 2, i * 3 + 2, 3, 3); 
                    }  
                }  
            }  
        } else {  
            return false;  
        }  
        g.dispose();  
        bi.flush();  
        /* generate image */  
        File f = new File(qrcodePicfilePath);  
        String suffix = f.getName().substring(f.getName().indexOf(".")+1, f.getName().length());  
        System.out.println(suffix);  
        try {  
        	ImageIO.write(bi, suffix, out); //"png"  
        } catch (IOException ioe) {  
            System.out.println("Generate QRCode image error!" + ioe.getMessage());  
            return false;  
        }  
  
        return true;  
    }  
      
    public static void main(String[] args) throws Exception {  
        System.out.println("-----------------A------------------");
        String data = "www.baidu.com.测试";  
        System.out.println("编码数据长度: " + data.getBytes().length);  
        //GucasQRCodeEncoder.encode(data, "/Users/mac/Desktop/a.jpg");  
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GucasQRCodeEncoder.encode("123", "a.jpg", out);
        System.out.println(GucasQRCodeDecoder.decode("/Users/mac/Desktop/a.jpg"));  
        System.out.println("-----------------B------------------");
    }  
}  