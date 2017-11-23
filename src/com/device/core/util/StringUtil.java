package com.device.core.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

/**
 * 字符串工具类
 * @author fuwenbin
 *
 */
public class StringUtil {

    private static String defaultEncoding = "UTF-8";

    public static String getEncodedString(String s, String encoding) throws java.io.UnsupportedEncodingException {
        if (encoding == null || "".equals(encoding.trim())) {
            encoding = defaultEncoding;
        }
        return new String(s.getBytes("ISO8859_1"), encoding);
    }

    public static String getPrefixString(String source, int num) {
        if (num <= 0) {
            return null;
        }

        if (num > source.length()) {
            return source;
        }
        return source.substring(0, num);
    }

    public static String getExceptionStack(Throwable t) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw, true);
        t.printStackTrace(pw);
        return sw.getBuffer().toString();
    }

    public static String getString(Object s, String def) {
        if (s == null)
            return def;
        return s.toString();
    }

    public static String[] getArray(String orig, String regex) {
        if (orig == null || "".equals(orig.trim())) {
            return new String[0];
        }

        return orig.split(regex);
    }

    public static List getList(String orig, String regex) {
        List result = new ArrayList();

        if (orig == null || "".equals(orig.trim())) {
            return result;
        }

        String[] values = orig.split(regex);

        for (int i = 0; i < values.length; i++) {
            result.add(values[i]);
        }

        return result;
    }

    public static String replaceAll(String src, String regex, String replacement) {
        String result = src;

        String tempResult = src;
        while (true) {
            tempResult = replaceFirst(tempResult, regex, replacement);
            if (tempResult.equals(result)) {
                break;
            }
            result = tempResult;
        }
        return result;
    }

    public static String replaceFirst(String src, String regex, String replacement) {
        int firstStart = src.indexOf(regex);
        int firstEnd = firstStart + regex.length();

        if (firstStart >= 0) {
            StringBuffer result = new StringBuffer();
            result.append(src.subSequence(0, firstStart));
            result.append(replacement);
            result.append(src.substring(firstEnd));
            return result.toString();
        } else {
            return src;
        }
    }

    public static String replaceAll(String orig, Map replacementMap) {
        String temp = orig;

        for (Iterator it = replacementMap.keySet().iterator(); it.hasNext();) {
            String key = it.next().toString();
            temp = temp.replaceAll(key, replacementMap.get(key).toString());
        }

        return temp;
    }

    public static boolean hasLength(Object str) {
        if (str == null) {
            return false;
        }

        String newstr = str instanceof String ? (String) str : str.toString();

        return newstr.length() > 0;
    }

    public static boolean hasText(Object str) {
        int strLen;
        if (str == null) {
            return false;
        }

        String newstr = str instanceof String ? (String) str : str.toString();

        if ((strLen = newstr.length()) == 0) {
            return false;
        }

        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(newstr.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    public static String element2string(Object vo) {
        StringBuffer returnValue = new StringBuffer("");
        Method[] ms = vo.getClass().getDeclaredMethods();
        for (int i = 0; i < ms.length; i++) {
            Method m = ms[i];
            String mname = m.getName();
            if (mname.startsWith("get") && !m.getReturnType().isInstance(vo)) {
                returnValue.append(mname.substring(3, 4).toLowerCase());
                returnValue.append(mname.substring(4));
                try {
                    returnValue.append("=\"" + m.invoke(vo, new Object[] {}) + "\" ");
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        return returnValue.toString();
    }

    public static String vo2json(Object vo) {
        StringBuffer returnValue = new StringBuffer("{");
        Method[] ms = vo.getClass().getDeclaredMethods();
        for (int i = 0; i < ms.length; i++) {
            Method m = ms[i];
            String mname = m.getName();
            if (mname.startsWith("get") && !m.getReturnType().isInstance(vo)) {
                returnValue.append(mname.substring(3, 4).toLowerCase());
                returnValue.append(mname.substring(4));
                try {
                    Object v = m.invoke(vo, new Object[] {});
                    if (v == null)
                        v = "";
                    v = v.toString();
                    v = ((String) v).replace("\n", "");
                    v = ((String) v).replace("\r", "");
                    returnValue.append(":\"" + v + "\"");
                    if (i < ms.length - 2) {
                        returnValue.append(",");
                    }
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
        returnValue.append("}");
        return returnValue.toString();
    }

    public static String[] split(String origin, String sign) {
        if (null == origin) {
            return null;
        }
        if (org.apache.commons.lang.StringUtils.isEmpty(sign) || sign.toCharArray().length != 1) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(origin, sign);
            String[] dest = new String[st.countTokens()];
            for (int i = 0; i < dest.length; i++) {
                dest[i] = st.nextToken();
            }
            return dest;
        }
    }

    public static String subString(String s, int length) {
        if (s == null || s.length() <= length) {
            return s;
        } else {
            return s.substring(0, length);
        }

    }

    public static String trim(String s) {
        if (s == null)
            return null;
        return s.trim();
    }

    public static String integerShow(Integer origin) {
        if (origin == null) {
            return "0";
        }
        String ori = origin.toString();
        StringBuffer temp = new StringBuffer();
        StringBuffer dest = new StringBuffer();
        if (ori.length() <= 3) {
            return ori;
        } else {
            // 1 652 356
            int prex = ori.length() % 3;
            if (prex != 0) {
                for (int i = prex; i < 3; i++) {
                    temp.append("0");
                }
            }
            temp.append(ori);// 001 652 356
            for (int i = 0; i < temp.length() / 3; i++) {
                dest.append(temp.substring(i * 3, (i + 1) * 3)).append(",");
            }// 001,652,356,
            String deststr = dest.toString();
            if (deststr.endsWith(",")) {
                deststr = deststr.substring(0, deststr.length() - 1);
            }// 001,652,356
            for (int i = 0; i < 3; i++) {
                if (deststr.charAt(0) == '0') {
                    deststr = deststr.substring(1, deststr.length());
                } else {
                    break;
                }
            }// 1,652,356
            return deststr;
        }
    }

    public static String encodebase64String(String str) {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encodeBuffer(str.getBytes()).trim();
    }
}
