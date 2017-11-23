package com.device.core.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.CycleDetectionStrategy;
import net.sf.json.util.PropertyFilter;

/**
 * 
 * JSON格式化输出工具 
 * 
 * @author  geek
 * @version  [版本号, 2012-8-15]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class JsonUtils {

    /**
     * 排除不需要json格式化的属性
     * 
     * @param nodes 需要json的集合对象
     * @param excludes 不需要格式化的属性数组
     * @return String
     * @throws JSONException json异常
     * @see [类、类#方法、类#成员]
     */
    public static String outJSONString(List<?> nodes, String[] excludes) throws JSONException {

        JsonConfig config = new JsonConfig();
        config.setCycleDetectionStrategy(CycleDetectionStrategy.LENIENT);
        config.setExcludes(new String[] { "handler", "hibernateLazyInitializer" });
        final List ex = Arrays.asList(excludes);
        config.setJsonPropertyFilter(new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                // 配置你可能出现递归的属性
                if (ex.contains(name)) {
                    return true;
                } else {
                    return false;
                }
            }
        });

        return JSONArray.fromObject(nodes, config).toString();
    }

    /**
     * 排除不需要json格式化的属性
     * @param nodes 需要json的集合对象
     * @param exclude 不需要格式化的属性
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getJsonString(List<?> nodes, final String exclude) {
        JsonConfig jsonConfig = new JsonConfig();
        PropertyFilter filter = new PropertyFilter() {
            public boolean apply(Object source, String name, Object value) {
                if (exclude.equals(name)) {
                    return true;
                }
                return false;
            }
        };
        jsonConfig.setJsonPropertyFilter(filter);
        return JSONArray.fromObject(nodes, jsonConfig).toString();
    }

    /**
     *将List转成Json字符串
     * @param nodes 需要json的集合对象
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getJsonString(List<?> nodes) {
        return JSONArray.fromObject(nodes).toString();
    }

    /**
     *将Map转成Json字符串
     * @param nodes 需要json的集合对象
     * @return String
     * @see [类、类#方法、类#成员]
     */
    public static String getMapJsonString(Map<?, ?> nodes) {
        return JSONArray.fromObject(nodes).toString();
    }

    /**
     * 把json字符串转化为集合对象返回
     * 
     * @param jsonArrayString json字符串
     * @param clazz 对象字节码
     * @return collection 集合
     * @see [类、类#方法、类#成员]
     */
    @SuppressWarnings("unchecked")
    public static List jsonToCollection(String jsonArrayString, Class clazz) {

        JSONArray jsonArray = JSONArray.fromObject(jsonArrayString);
        List list = (List) JSONArray.toCollection(jsonArray, clazz);
        return list;
    }

    public static Object jsonToObj(String jsonArrayString, Class clazz) {

        JSONObject jsonObj = JSONObject.fromObject(jsonArrayString);
        return JSONObject.toBean(jsonObj, clazz);
    }
}
