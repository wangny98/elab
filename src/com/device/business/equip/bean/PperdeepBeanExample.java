package com.device.business.equip.bean;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PperdeepBeanExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public PperdeepBeanExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(String value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(String value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(String value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(String value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(String value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(String value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLike(String value) {
            addCriterion("id like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotLike(String value) {
            addCriterion("id not like", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<String> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<String> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(String value1, String value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(String value1, String value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andZidIsNull() {
            addCriterion("zid is null");
            return (Criteria) this;
        }

        public Criteria andZidIsNotNull() {
            addCriterion("zid is not null");
            return (Criteria) this;
        }

        public Criteria andZidEqualTo(Integer value) {
            addCriterion("zid =", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidNotEqualTo(Integer value) {
            addCriterion("zid <>", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidGreaterThan(Integer value) {
            addCriterion("zid >", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidGreaterThanOrEqualTo(Integer value) {
            addCriterion("zid >=", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidLessThan(Integer value) {
            addCriterion("zid <", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidLessThanOrEqualTo(Integer value) {
            addCriterion("zid <=", value, "zid");
            return (Criteria) this;
        }

        public Criteria andZidIn(List<Integer> values) {
            addCriterion("zid in", values, "zid");
            return (Criteria) this;
        }

        public Criteria andZidNotIn(List<Integer> values) {
            addCriterion("zid not in", values, "zid");
            return (Criteria) this;
        }

        public Criteria andZidBetween(Integer value1, Integer value2) {
            addCriterion("zid between", value1, value2, "zid");
            return (Criteria) this;
        }

        public Criteria andZidNotBetween(Integer value1, Integer value2) {
            addCriterion("zid not between", value1, value2, "zid");
            return (Criteria) this;
        }

        public Criteria andPileNumberIsNull() {
            addCriterion("Pile_Number is null");
            return (Criteria) this;
        }

        public Criteria andPileNumberIsNotNull() {
            addCriterion("Pile_Number is not null");
            return (Criteria) this;
        }

        public Criteria andPileNumberEqualTo(String value) {
            addCriterion("Pile_Number =", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberNotEqualTo(String value) {
            addCriterion("Pile_Number <>", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberGreaterThan(String value) {
            addCriterion("Pile_Number >", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberGreaterThanOrEqualTo(String value) {
            addCriterion("Pile_Number >=", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberLessThan(String value) {
            addCriterion("Pile_Number <", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberLessThanOrEqualTo(String value) {
            addCriterion("Pile_Number <=", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberLike(String value) {
            addCriterion("Pile_Number like", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberNotLike(String value) {
            addCriterion("Pile_Number not like", value, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberIn(List<String> values) {
            addCriterion("Pile_Number in", values, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberNotIn(List<String> values) {
            addCriterion("Pile_Number not in", values, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberBetween(String value1, String value2) {
            addCriterion("Pile_Number between", value1, value2, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileNumberNotBetween(String value1, String value2) {
            addCriterion("Pile_Number not between", value1, value2, "pileNumber");
            return (Criteria) this;
        }

        public Criteria andPileDeepIsNull() {
            addCriterion("Pile_Deep is null");
            return (Criteria) this;
        }

        public Criteria andPileDeepIsNotNull() {
            addCriterion("Pile_Deep is not null");
            return (Criteria) this;
        }

        public Criteria andPileDeepEqualTo(BigDecimal value) {
            addCriterion("Pile_Deep =", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepNotEqualTo(BigDecimal value) {
            addCriterion("Pile_Deep <>", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepGreaterThan(BigDecimal value) {
            addCriterion("Pile_Deep >", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Pile_Deep >=", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepLessThan(BigDecimal value) {
            addCriterion("Pile_Deep <", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Pile_Deep <=", value, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepIn(List<BigDecimal> values) {
            addCriterion("Pile_Deep in", values, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepNotIn(List<BigDecimal> values) {
            addCriterion("Pile_Deep not in", values, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Pile_Deep between", value1, value2, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andPileDeepNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Pile_Deep not between", value1, value2, "pileDeep");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightIsNull() {
            addCriterion("Liquid_Cement_Weight is null");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightIsNotNull() {
            addCriterion("Liquid_Cement_Weight is not null");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightEqualTo(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight =", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightNotEqualTo(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight <>", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightGreaterThan(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight >", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight >=", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightLessThan(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight <", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Liquid_Cement_Weight <=", value, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightIn(List<BigDecimal> values) {
            addCriterion("Liquid_Cement_Weight in", values, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightNotIn(List<BigDecimal> values) {
            addCriterion("Liquid_Cement_Weight not in", values, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Liquid_Cement_Weight between", value1, value2, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andLiquidCementWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Liquid_Cement_Weight not between", value1, value2, "liquidCementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightIsNull() {
            addCriterion("Cement_Weight is null");
            return (Criteria) this;
        }

        public Criteria andCementWeightIsNotNull() {
            addCriterion("Cement_Weight is not null");
            return (Criteria) this;
        }

        public Criteria andCementWeightEqualTo(BigDecimal value) {
            addCriterion("Cement_Weight =", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightNotEqualTo(BigDecimal value) {
            addCriterion("Cement_Weight <>", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightGreaterThan(BigDecimal value) {
            addCriterion("Cement_Weight >", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Cement_Weight >=", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightLessThan(BigDecimal value) {
            addCriterion("Cement_Weight <", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Cement_Weight <=", value, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightIn(List<BigDecimal> values) {
            addCriterion("Cement_Weight in", values, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightNotIn(List<BigDecimal> values) {
            addCriterion("Cement_Weight not in", values, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cement_Weight between", value1, value2, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andCementWeightNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Cement_Weight not between", value1, value2, "cementWeight");
            return (Criteria) this;
        }

        public Criteria andPowerInsideIsNull() {
            addCriterion("Power_Inside is null");
            return (Criteria) this;
        }

        public Criteria andPowerInsideIsNotNull() {
            addCriterion("Power_Inside is not null");
            return (Criteria) this;
        }

        public Criteria andPowerInsideEqualTo(BigDecimal value) {
            addCriterion("Power_Inside =", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideNotEqualTo(BigDecimal value) {
            addCriterion("Power_Inside <>", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideGreaterThan(BigDecimal value) {
            addCriterion("Power_Inside >", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Power_Inside >=", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideLessThan(BigDecimal value) {
            addCriterion("Power_Inside <", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Power_Inside <=", value, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideIn(List<BigDecimal> values) {
            addCriterion("Power_Inside in", values, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideNotIn(List<BigDecimal> values) {
            addCriterion("Power_Inside not in", values, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Power_Inside between", value1, value2, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerInsideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Power_Inside not between", value1, value2, "powerInside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideIsNull() {
            addCriterion("Power_Outside is null");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideIsNotNull() {
            addCriterion("Power_Outside is not null");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideEqualTo(BigDecimal value) {
            addCriterion("Power_Outside =", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideNotEqualTo(BigDecimal value) {
            addCriterion("Power_Outside <>", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideGreaterThan(BigDecimal value) {
            addCriterion("Power_Outside >", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Power_Outside >=", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideLessThan(BigDecimal value) {
            addCriterion("Power_Outside <", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Power_Outside <=", value, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideIn(List<BigDecimal> values) {
            addCriterion("Power_Outside in", values, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideNotIn(List<BigDecimal> values) {
            addCriterion("Power_Outside not in", values, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Power_Outside between", value1, value2, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andPowerOutsideNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Power_Outside not between", value1, value2, "powerOutside");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleIsNull() {
            addCriterion("Front_Back_Angle is null");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleIsNotNull() {
            addCriterion("Front_Back_Angle is not null");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleEqualTo(BigDecimal value) {
            addCriterion("Front_Back_Angle =", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleNotEqualTo(BigDecimal value) {
            addCriterion("Front_Back_Angle <>", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleGreaterThan(BigDecimal value) {
            addCriterion("Front_Back_Angle >", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Front_Back_Angle >=", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleLessThan(BigDecimal value) {
            addCriterion("Front_Back_Angle <", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Front_Back_Angle <=", value, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleIn(List<BigDecimal> values) {
            addCriterion("Front_Back_Angle in", values, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleNotIn(List<BigDecimal> values) {
            addCriterion("Front_Back_Angle not in", values, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Front_Back_Angle between", value1, value2, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andFrontBackAngleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Front_Back_Angle not between", value1, value2, "frontBackAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleIsNull() {
            addCriterion("Left_Right_Angle is null");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleIsNotNull() {
            addCriterion("Left_Right_Angle is not null");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleEqualTo(BigDecimal value) {
            addCriterion("Left_Right_Angle =", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleNotEqualTo(BigDecimal value) {
            addCriterion("Left_Right_Angle <>", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleGreaterThan(BigDecimal value) {
            addCriterion("Left_Right_Angle >", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Left_Right_Angle >=", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleLessThan(BigDecimal value) {
            addCriterion("Left_Right_Angle <", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Left_Right_Angle <=", value, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleIn(List<BigDecimal> values) {
            addCriterion("Left_Right_Angle in", values, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleNotIn(List<BigDecimal> values) {
            addCriterion("Left_Right_Angle not in", values, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Left_Right_Angle between", value1, value2, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andLeftRightAngleNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Left_Right_Angle not between", value1, value2, "leftRightAngle");
            return (Criteria) this;
        }

        public Criteria andSquarenessIsNull() {
            addCriterion("Squareness is null");
            return (Criteria) this;
        }

        public Criteria andSquarenessIsNotNull() {
            addCriterion("Squareness is not null");
            return (Criteria) this;
        }

        public Criteria andSquarenessEqualTo(BigDecimal value) {
            addCriterion("Squareness =", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessNotEqualTo(BigDecimal value) {
            addCriterion("Squareness <>", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessGreaterThan(BigDecimal value) {
            addCriterion("Squareness >", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("Squareness >=", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessLessThan(BigDecimal value) {
            addCriterion("Squareness <", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessLessThanOrEqualTo(BigDecimal value) {
            addCriterion("Squareness <=", value, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessIn(List<BigDecimal> values) {
            addCriterion("Squareness in", values, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessNotIn(List<BigDecimal> values) {
            addCriterion("Squareness not in", values, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Squareness between", value1, value2, "squareness");
            return (Criteria) this;
        }

        public Criteria andSquarenessNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("Squareness not between", value1, value2, "squareness");
            return (Criteria) this;
        }

        public Criteria andSpeedIsNull() {
            addCriterion("Speed is null");
            return (Criteria) this;
        }

        public Criteria andSpeedIsNotNull() {
            addCriterion("Speed is not null");
            return (Criteria) this;
        }

        public Criteria andSpeedEqualTo(Short value) {
            addCriterion("Speed =", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotEqualTo(Short value) {
            addCriterion("Speed <>", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedGreaterThan(Short value) {
            addCriterion("Speed >", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedGreaterThanOrEqualTo(Short value) {
            addCriterion("Speed >=", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedLessThan(Short value) {
            addCriterion("Speed <", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedLessThanOrEqualTo(Short value) {
            addCriterion("Speed <=", value, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedIn(List<Short> values) {
            addCriterion("Speed in", values, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotIn(List<Short> values) {
            addCriterion("Speed not in", values, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedBetween(Short value1, Short value2) {
            addCriterion("Speed between", value1, value2, "speed");
            return (Criteria) this;
        }

        public Criteria andSpeedNotBetween(Short value1, Short value2) {
            addCriterion("Speed not between", value1, value2, "speed");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberIsNull() {
            addCriterion("Pile_Machine_Number is null");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberIsNotNull() {
            addCriterion("Pile_Machine_Number is not null");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberEqualTo(String value) {
            addCriterion("Pile_Machine_Number =", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberNotEqualTo(String value) {
            addCriterion("Pile_Machine_Number <>", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberGreaterThan(String value) {
            addCriterion("Pile_Machine_Number >", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberGreaterThanOrEqualTo(String value) {
            addCriterion("Pile_Machine_Number >=", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberLessThan(String value) {
            addCriterion("Pile_Machine_Number <", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberLessThanOrEqualTo(String value) {
            addCriterion("Pile_Machine_Number <=", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberLike(String value) {
            addCriterion("Pile_Machine_Number like", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberNotLike(String value) {
            addCriterion("Pile_Machine_Number not like", value, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberIn(List<String> values) {
            addCriterion("Pile_Machine_Number in", values, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberNotIn(List<String> values) {
            addCriterion("Pile_Machine_Number not in", values, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberBetween(String value1, String value2) {
            addCriterion("Pile_Machine_Number between", value1, value2, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andPileMachineNumberNotBetween(String value1, String value2) {
            addCriterion("Pile_Machine_Number not between", value1, value2, "pileMachineNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberIsNull() {
            addCriterion("Mix_Station_Number is null");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberIsNotNull() {
            addCriterion("Mix_Station_Number is not null");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberEqualTo(String value) {
            addCriterion("Mix_Station_Number =", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberNotEqualTo(String value) {
            addCriterion("Mix_Station_Number <>", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberGreaterThan(String value) {
            addCriterion("Mix_Station_Number >", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberGreaterThanOrEqualTo(String value) {
            addCriterion("Mix_Station_Number >=", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberLessThan(String value) {
            addCriterion("Mix_Station_Number <", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberLessThanOrEqualTo(String value) {
            addCriterion("Mix_Station_Number <=", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberLike(String value) {
            addCriterion("Mix_Station_Number like", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberNotLike(String value) {
            addCriterion("Mix_Station_Number not like", value, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberIn(List<String> values) {
            addCriterion("Mix_Station_Number in", values, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberNotIn(List<String> values) {
            addCriterion("Mix_Station_Number not in", values, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberBetween(String value1, String value2) {
            addCriterion("Mix_Station_Number between", value1, value2, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andMixStationNumberNotBetween(String value1, String value2) {
            addCriterion("Mix_Station_Number not between", value1, value2, "mixStationNumber");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("Create_Time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("Create_Time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("Create_Time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("Create_Time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("Create_Time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Create_Time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("Create_Time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("Create_Time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("Create_Time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("Create_Time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("Create_Time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("Create_Time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIsNull() {
            addCriterion("Change_Time is null");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIsNotNull() {
            addCriterion("Change_Time is not null");
            return (Criteria) this;
        }

        public Criteria andChangeTimeEqualTo(Date value) {
            addCriterion("Change_Time =", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotEqualTo(Date value) {
            addCriterion("Change_Time <>", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeGreaterThan(Date value) {
            addCriterion("Change_Time >", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("Change_Time >=", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeLessThan(Date value) {
            addCriterion("Change_Time <", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeLessThanOrEqualTo(Date value) {
            addCriterion("Change_Time <=", value, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeIn(List<Date> values) {
            addCriterion("Change_Time in", values, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotIn(List<Date> values) {
            addCriterion("Change_Time not in", values, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeBetween(Date value1, Date value2) {
            addCriterion("Change_Time between", value1, value2, "changeTime");
            return (Criteria) this;
        }

        public Criteria andChangeTimeNotBetween(Date value1, Date value2) {
            addCriterion("Change_Time not between", value1, value2, "changeTime");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}