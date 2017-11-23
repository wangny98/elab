package com.device.business.equip.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PperdeepBean {
    private String id;

    private Integer zid;

    //桩编号
    private String pileNumber;

    //桩深
    private BigDecimal pileDeep;

    //浆量
    private BigDecimal liquidCementWeight;

    //灰量
    private BigDecimal cementWeight;

    //内电流
    private BigDecimal powerInside;

    //外电流
    private BigDecimal powerOutside;

    //前后角
    private BigDecimal frontBackAngle;

    //左右角
    private BigDecimal leftRightAngle;

    //垂直度
    private BigDecimal squareness;

    //速度
    private Short speed;

    //打桩机编号
    private String pileMachineNumber;

    //搅拌站编号
    private String mixStationNumber;

    private Date createTime;

    private Date changeTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public Integer getZid() {
        return zid;
    }

    public void setZid(Integer zid) {
        this.zid = zid;
    }

    public String getPileNumber() {
        return pileNumber;
    }

    public void setPileNumber(String pileNumber) {
        this.pileNumber = pileNumber == null ? null : pileNumber.trim();
    }

    public BigDecimal getPileDeep() {
        return pileDeep;
    }

    public void setPileDeep(BigDecimal pileDeep) {
        this.pileDeep = pileDeep;
    }

    public BigDecimal getLiquidCementWeight() {
        return liquidCementWeight;
    }

    public void setLiquidCementWeight(BigDecimal liquidCementWeight) {
        this.liquidCementWeight = liquidCementWeight;
    }

    public BigDecimal getCementWeight() {
        return cementWeight;
    }

    public void setCementWeight(BigDecimal cementWeight) {
        this.cementWeight = cementWeight;
    }

    public BigDecimal getPowerInside() {
        return powerInside;
    }

    public void setPowerInside(BigDecimal powerInside) {
        this.powerInside = powerInside;
    }

    public BigDecimal getPowerOutside() {
        return powerOutside;
    }

    public void setPowerOutside(BigDecimal powerOutside) {
        this.powerOutside = powerOutside;
    }

    public BigDecimal getFrontBackAngle() {
        return frontBackAngle;
    }

    public void setFrontBackAngle(BigDecimal frontBackAngle) {
        this.frontBackAngle = frontBackAngle;
    }

    public BigDecimal getLeftRightAngle() {
        return leftRightAngle;
    }

    public void setLeftRightAngle(BigDecimal leftRightAngle) {
        this.leftRightAngle = leftRightAngle;
    }

    public BigDecimal getSquareness() {
        return squareness;
    }

    public void setSquareness(BigDecimal squareness) {
        this.squareness = squareness;
    }

    public Short getSpeed() {
        return speed;
    }

    public void setSpeed(Short speed) {
        this.speed = speed;
    }

    public String getPileMachineNumber() {
        return pileMachineNumber;
    }

    public void setPileMachineNumber(String pileMachineNumber) {
        this.pileMachineNumber = pileMachineNumber == null ? null : pileMachineNumber.trim();
    }

    public String getMixStationNumber() {
        return mixStationNumber;
    }

    public void setMixStationNumber(String mixStationNumber) {
        this.mixStationNumber = mixStationNumber == null ? null : mixStationNumber.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getChangeTime() {
        return changeTime;
    }

    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
}