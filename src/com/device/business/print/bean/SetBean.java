package com.device.business.print.bean;

/**
 * 
 * <fusionCharts中代表一条数据>
 * <功能详细描述>
 * 
 * @author  geek
 * @version  [版本号, 2013-4-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class SetBean {

    private String name;

    private String value;

    private String color;

    /**
     * <默认构造函数>
     * @param value 值
     * 
     */
    public SetBean(String value) {
        this.value = value;
    }

    /**
     * <默认构造函数>
     * @param color 颜色
     * @param name 名称
     * @param value 值
     */
    public SetBean(String color, String name, String value) {
        super();
        this.color = color;
        this.name = name;
        this.value = value;
    }

    /**
     * <默认构造函数>
     * @param name 名称
     * @param value 值
     */
    public SetBean(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    /**
     * <默认构造函数>
     */
    public SetBean() {
        super();
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
