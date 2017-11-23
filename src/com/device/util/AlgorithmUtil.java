package com.device.util;

import java.math.BigInteger;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 算法�? * 
 * @author  geek 
 * * @version  [1.0, 2013-3-25]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public class AlgorithmUtil {

    /**
     * 合并byte数组
     * 
     * @param a
     * @param b
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static byte[] arraycopy(byte[] a, byte[] b) {
        byte[] c = new byte[a.length + b.length];
        System.arraycopy(a, 0, c, 0, a.length);
        System.arraycopy(b, 0, c, a.length, b.length);
        return c;
    }

    /**
     * int类型转换成byte[]
     * 
     * @param num
     *            int�?     * @return byte[]
     */
    public static byte[] intToBytes(int n) {
        byte[] b = new byte[4];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        return b;
    }

    /**
     * 整型转二进制
     * 
     * @param i
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getIntToBinaryString(int i) {
        String binaryString = "";
        for (int j = 12; j >= 0; --j)
            if (((1 << j) & i) == 0)
                binaryString += "0";
            else
                binaryString += "1";
        return binaryString;
    }

    /**
     * long类型转换成byte[]
     * 
     * @param n
     * @return
     */
    public static byte[] longToBytes(long n) {
        byte[] b = new byte[8];
        b[0] = (byte) (n & 0xff);
        b[1] = (byte) (n >> 8 & 0xff);
        b[2] = (byte) (n >> 16 & 0xff);
        b[3] = (byte) (n >> 24 & 0xff);
        b[4] = (byte) (n >> 32 & 0xff);
        b[5] = (byte) (n >> 40 & 0xff);
        b[6] = (byte) (n >> 48 & 0xff);
        b[7] = (byte) (n >> 56 & 0xff);
        return b;
    }

    /**
     * 求两个数组的交集
     * 
     * @param arr1
     * @param arr2
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String[] intersect(String[] arr1, String[] arr2) {
        Map<String, Boolean> map = new HashMap<String, Boolean>();
        LinkedList<String> list = new LinkedList<String>();
        for (String str : arr1) {
            if (!map.containsKey(str)) {
                map.put(str, Boolean.FALSE);
            }
        }
        for (String str : arr2) {
            if (map.containsKey(str)) {
                map.put(str, Boolean.TRUE);
            }
        }

        for (Entry<String, Boolean> e : map.entrySet()) {
            if (e.getValue().equals(Boolean.TRUE)) {
                list.add(e.getKey());
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 求两个数字的差集
     * 
     * @param arr1
     * @param arr2
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String[] minus(String[] arr1, String[] arr2) {
        LinkedList<String> list = new LinkedList<String>();
        LinkedList<String> history = new LinkedList<String>();
        String[] longerArr = arr1;
        String[] shorterArr = arr2;
        //找出较长的数组来减较短的数组   
        if (arr1.length > arr2.length) {
            longerArr = arr2;
            shorterArr = arr1;
        }
        for (String str : longerArr) {
            if (!list.contains(str)) {
                list.add(str);
            }
        }
        for (String str : shorterArr) {
            if (list.contains(str)) {
                history.add(str);
                list.remove(str);
            } else {
                if (!history.contains(str)) {
                    list.add(str);
                }
            }
        }

        String[] result = {};
        return list.toArray(result);
    }

    /**
     * 二进制交�?     * 
     * @param a
     * @param c
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getIntersectInteger(String a, Integer c) {
        BigInteger b = new BigInteger(a, 2);
        Integer d = Integer.valueOf(b.toString());
        d = d | c;
        return getIntToBinaryString(d);
    }

    /**
     * 二进制差�?     * 
     * @param a
     * @param c
     * @return
     * @see [类�?�?方法、类#成员]
     */
    public static String getMinusInteger(String a, Integer c) {
        BigInteger b = new BigInteger(a, 2);
        Integer d = Integer.valueOf(b.toString());
        d = d & ~c;
        return getIntToBinaryString(d);
    }

    public static boolean checkIntegerAuth(String a, Integer b) {
        BigInteger c = new BigInteger(a, 2);
        Integer d = Integer.valueOf(c.toString());
        return (d & b) == b;
    }

    /**
     * 天数增加算法
     * 
     * @param d
     * @param day
     * @return
     * @throws Exception
     */
    public static Date addDate(Date d, Integer day) throws Exception {
        long time = d.getTime();
        day = day * 24 * 60 * 60 * 1000;
        time += day;
        return new Date(time);
    }

    public static String getRecordAuth(String code) {
        String a = code.substring(0, 1);
        String b = code.substring(4, code.length());
        return a + "000" + b;
    }

    public static String getRecordAuthBack(String code) {
        String a = code.substring(0, 9);
        String b = code.substring(12, code.length());
        return a + "000" + b;
    }

    /**
     * 重装文档名称，增�?fd字段
     * 
     * @param docName
     * @return
     */
    public static String doDocName(String docName) {
        int len = docName.lastIndexOf(".");
        if (len == -1) {
            docName = docName + "-fd";
        } else {
            String a = docName.substring(0, len);
            String b = docName.substring(len, docName.length());
            docName = a + "-fd" + b;
        }
        return docName;
    }

}
