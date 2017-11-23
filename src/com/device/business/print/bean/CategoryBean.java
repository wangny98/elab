package com.device.business.print.bean;

/**
 * 
 * <fusionCharts中代表每一列本身>
 * <功能详细描述>
 * 
 * @author  geek
 * @version  [版本号, 2013-4-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CategoryBean {
    private String name;

    public CategoryBean() {

    }

    /**
     * <默认构造函数>
     * @param name 名词
     */
    public CategoryBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
