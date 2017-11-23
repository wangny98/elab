package com.device.business.print.bean;

import java.util.List;

/**
 * 
 * <fusionCharts中代表数据的集合>
 * <功能详细描述>
 * 
 * @author  geek
 * @version  [版本号, 2013-4-24]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class DatasetBean {

    private String seriesName;

    private String color;

    private String showValue;

    private String lineThickness;

    private String yaxismaxvalue;

    private String anchorSides;

    private String anchorRadius;

    private String anchorAlpha;

    private String parentYAxis;

    private String fontSize;

    private String font;

    private List<SetBean> set;

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

    public String getFont() {
        return font;
    }

    public void setFont(String font) {
        this.font = font;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getShowValue() {
        return showValue;
    }

    public void setShowValue(String showValue) {
        this.showValue = showValue;
    }

    public String getLineThickness() {
        return lineThickness;
    }

    public void setLineThickness(String lineThickness) {
        this.lineThickness = lineThickness;
    }

    public String getYaxismaxvalue() {
        return yaxismaxvalue;
    }

    public void setYaxismaxvalue(String yaxismaxvalue) {
        this.yaxismaxvalue = yaxismaxvalue;
    }

    public String getAnchorSides() {
        return anchorSides;
    }

    public void setAnchorSides(String anchorSides) {
        this.anchorSides = anchorSides;
    }

    public String getAnchorRadius() {
        return anchorRadius;
    }

    public void setAnchorRadius(String anchorRadius) {
        this.anchorRadius = anchorRadius;
    }

    public String getAnchorAlpha() {
        return anchorAlpha;
    }

    public void setAnchorAlpha(String anchorAlpha) {
        this.anchorAlpha = anchorAlpha;
    }

    public String getParentYAxis() {
        return parentYAxis;
    }

    public void setParentYAxis(String parentYAxis) {
        this.parentYAxis = parentYAxis;
    }

    public List<SetBean> getSet() {
        return set;
    }

    public void setSet(List<SetBean> set) {
        this.set = set;
    }
}
