package com.device.core.util;

/**
 * 
 * 字符串操作 
 * 
 * @author  geek
 * @version  [版本号, 2012-7-26]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public final class StringTools {
    private StringTools() {

    }

    /**
     * 删掉字符串首尾字符
     * 
     * @param string 目标字符串
     * @param ch 删除的字符
     * @return string 处理后的字符串
     */
    public static String trimChar(String string, char ch) {
        byte[] bytes = string.getBytes();

        int start = bytes.length;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] != ch) {
                start = i;
                break;
            }
        }

        int end = 0;
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[bytes.length - 1 - i] != ch) {
                end = bytes.length - i;
                break;
            }
        }

        if (end > start) {
            return string.substring(start, end);
        } else {
            return null;
        }
    }

    /**
     * 去掉正则表达式中的参数，如将“/person/{id:\d{3}}”转为“/person/\d{3}”
     * 
     * @param string 正则表达式中的参数
     * @return string 解析后的参数
     */
    public static String removeRegexParam(String string) {
        byte[] bytes = string.getBytes();
        for (int i = 0; i < bytes.length; i++) {
            if (bytes[i] == '{') {
                for (int j = i; j < bytes.length; j++) {
                    if (bytes[j] == ':') {
                        int leftCount = 1;
                        int rightCount = 0;
                        for (int k = j; k < bytes.length; k++) {
                            if (bytes[k] == '{') {
                                leftCount++;
                            }
                            if (bytes[k] == '}') {
                                rightCount++;
                            }

                            if (leftCount == rightCount) {
                                String prefix = string.substring(0, i);
                                String middle = string.substring(j + 1, k);
                                String suffix = string.substring(k + 1);

                                return removeRegexParam(prefix + middle + suffix);
                            }
                        }
                    }
                }
            }
        }

        return string;
    }

    /**
     * 将路径中的参数转换为正则表达式，如将/person/{username}/{password}/修改为"/person/.*"
     * 
     * @param string 待处理的请求路径
     * @return 解析后的参数
     */
    public static String removePathParam(String string) {
        int begin = string.indexOf('{');
        int end = string.indexOf('}');
        if (begin > -1 && end > begin) {
            String result = string.substring(0, begin) + ".*" + string.substring(end + 1);
            return removePathParam(result);
        } else {
            return string;
        }
    }

    /**
     * 判断字符串为空对象或空字符串
     * @param str  字符串
     * @return 字符串是否为空对象或空字符串
     */
    public static boolean isNullOrEmpty(String str) {

        boolean isNull = true;
        if (null == str || "".equals(str.trim())) {
            return isNull;
        }
        isNull = false;
        return isNull;
    }
}
