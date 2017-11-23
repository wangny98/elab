package com.device.business.moniter.bean;

import java.math.BigDecimal;
import java.util.Date;

public class PileMoniterBean {
    private Long id;

    private String pileDriverNumber;

    private String pileNumber;

    private BigDecimal pileLength;

    private Date startTime;

    private Integer gunitingSecond;

    private BigDecimal totalLiquid;

    private BigDecimal totalCement;

    private BigDecimal density;

    private BigDecimal averageLiquid;

    private BigDecimal averageCement;

    private BigDecimal maxSpeed;

    private BigDecimal maxInsidePower;

    private BigDecimal maxOutsidePower;

    private BigDecimal maxLean;

    private BigDecimal deepth;

    private BigDecimal speed;

    private BigDecimal flow;

    private BigDecimal frontBackAngle;

    private BigDecimal leftRightAngle;

    private BigDecimal outsidePower;

    private BigDecimal insidePower;

    private BigDecimal lean;
    
    private Integer status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPileDriverNumber() {
        return pileDriverNumber;
    }

    public void setPileDriverNumber(String pileDriverNumber) {
        this.pileDriverNumber = pileDriverNumber == null ? null : pileDriverNumber.trim();
    }

    public String getPileNumber() {
        return pileNumber;
    }

    public void setPileNumber(String pileNumber) {
        this.pileNumber = pileNumber == null ? null : pileNumber.trim();
    }

    public BigDecimal getPileLength() {
        return pileLength;
    }

    public void setPileLength(BigDecimal pileLength) {
        this.pileLength = pileLength;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Integer getGunitingSecond() {
        return gunitingSecond;
    }

    public void setGunitingSecond(Integer gunitingSecond) {
        this.gunitingSecond = gunitingSecond;
    }

    public BigDecimal getTotalLiquid() {
        return totalLiquid;
    }

    public void setTotalLiquid(BigDecimal totalLiquid) {
        this.totalLiquid = totalLiquid;
    }

    public BigDecimal getTotalCement() {
        return totalCement;
    }

    public void setTotalCement(BigDecimal totalCement) {
        this.totalCement = totalCement;
    }

    public BigDecimal getDensity() {
        return density;
    }

    public void setDensity(BigDecimal density) {
        this.density = density;
    }

    public BigDecimal getAverageLiquid() {
        return averageLiquid;
    }

    public void setAverageLiquid(BigDecimal averageLiquid) {
        this.averageLiquid = averageLiquid;
    }

    public BigDecimal getAverageCement() {
        return averageCement;
    }

    public void setAverageCement(BigDecimal averageCement) {
        this.averageCement = averageCement;
    }

    public BigDecimal getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(BigDecimal maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public BigDecimal getMaxInsidePower() {
        return maxInsidePower;
    }

    public void setMaxInsidePower(BigDecimal maxInsidePower) {
        this.maxInsidePower = maxInsidePower;
    }

    public BigDecimal getMaxOutsidePower() {
        return maxOutsidePower;
    }

    public void setMaxOutsidePower(BigDecimal maxOutsidePower) {
        this.maxOutsidePower = maxOutsidePower;
    }

    public BigDecimal getMaxLean() {
        return maxLean;
    }

    public void setMaxLean(BigDecimal maxLean) {
        this.maxLean = maxLean;
    }

    public BigDecimal getDeepth() {
        return deepth;
    }

    public void setDeepth(BigDecimal deepth) {
        this.deepth = deepth;
    }

    public BigDecimal getSpeed() {
        return speed;
    }

    public void setSpeed(BigDecimal speed) {
        this.speed = speed;
    }

    public BigDecimal getFlow() {
        return flow;
    }

    public void setFlow(BigDecimal flow) {
        this.flow = flow;
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

    public BigDecimal getOutsidePower() {
        return outsidePower;
    }

    public void setOutsidePower(BigDecimal outsidePower) {
        this.outsidePower = outsidePower;
    }

    public BigDecimal getInsidePower() {
        return insidePower;
    }

    public void setInsidePower(BigDecimal insidePower) {
        this.insidePower = insidePower;
    }

    public BigDecimal getLean() {
        return lean;
    }

    public void setLean(BigDecimal lean) {
        this.lean = lean;
    }

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
    
    
}