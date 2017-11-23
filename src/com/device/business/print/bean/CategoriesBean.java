package com.device.business.print.bean;

import java.util.List;

/**
 * 
 * <fusionCharts中代表柱子的集合>
 * <功能详细描述>
 * 
 * @author  geek
 * @version  [版本号, 2013-4-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class CategoriesBean {

    private String font;

    private String fontSize;

    private String fontColor;

    private List<CategoryBean> category;

    public List<CategoryBean> getCategory() {
        return category;
    }

    public void setCategory(List<CategoryBean> category) {
        this.category = category;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFontColor() {
        return fontColor;
    }

    public void setFontColor(String fontColor) {
        this.fontColor = fontColor;
    }

}
