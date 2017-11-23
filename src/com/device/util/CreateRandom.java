package com.device.util;

import java.util.Random;

/**
 * 
 * 获取随机�?
 * 
 * @author  庄海�?
 * @version  [版本�? 2011-12-14]
 * @see  [相关�?方法]
 * @since  [产品/模块版本]
 */
public final class CreateRandom {
    /**
     * 默认构�?函数
     */
    private CreateRandom() {

    }

    /**
     * 根据index种子获取随机�?
     * 
     * @return 随机�?
     * @see [类�?�?方法、类#成员]
     */
    public static int getRandomInt() {
        final Random random = new Random();
        return random.nextInt();
    }

}
