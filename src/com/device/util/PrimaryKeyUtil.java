package com.device.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 生成主键方式
 * @author geek
 * @date 2012-08-10作成
 * @desc 配置数据库表主键
 * @version 1.0
 **/
public class PrimaryKeyUtil {
    /***************************************************************************
     * 初始化主键值
     **************************************************************************/

    // 配合组建主键，每次工程重启后初始化为0
    private static Long NO = 0L;

    private static Integer SECOND = 3000;

    private static Long MAX = 9999L;

    private static Long getNO() {
        if (NO == MAX) {
            NO = 0L;
        }
        return ++NO;
    }

    /**
     * 一般表主键,并发量大的数据表采用
     **/
    public static Long getTableID() {
        String dateTime = new SimpleDateFormat("yyMMddHHmmss").format(new Date())
                + ((10000L + getNO()) + "").substring(1);
        return Long.parseLong(dateTime);
    }

    /***************************************************************************
     * 内部方法
     **************************************************************************/
    private static long[] ls = new long[SECOND];

    private static int li = 0;

    /**
     * getPK,获得数据库使用的一个long型唯一主键 16位，同一微秒内3000个不会重复
     * @return long
     */
    public synchronized static long getPK() {
        long lo = getpk();
        for (int i = 0; i < SECOND; i++) {
            long lt = ls[i];
            if (lt == lo) {
                lo = getPK();
                break;
            }
        }
        ls[li] = lo;
        li++;
        if (li == SECOND) {
            li = 0;
        }
        return lo;
    }

    private static long getpk() {
        String a = (String.valueOf(System.currentTimeMillis())).substring(3, 13);
        String d = (String.valueOf(Math.random())).substring(2, 8);
        Integer dd = Integer.valueOf(d);
        if (dd % 2 != 0) {
            dd += 1;
        }
        return Long.parseLong(a + dd.toString());
    }

    /**
     * 获取32位唯一值的序列号
     * @return
     * @see [类、类#方法、类#成员]
     */
    public static String getSeq() {

        String uuid = UUID.randomUUID().toString();
        return uuid.toString().replace("-", "");
    }
}
