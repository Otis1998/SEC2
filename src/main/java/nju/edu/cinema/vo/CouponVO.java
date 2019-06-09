package nju.edu.cinema.vo;

import nju.edu.cinema.po.Coupon;

import java.sql.Timestamp;

public class CouponVO {
    /**
     * 优惠券id
     */
    private int id;
	/**
     * 优惠券名称
     */
    private String name;
    /**
     * 优惠券使用门槛
     */
    private double targetAmount;
    /**
     * 优惠券优惠金额
     */
    private double discountAmount;
    /**
     * 可用时间
     */
    private Timestamp startTime;
    /**
     * 失效时间
     */
    private Timestamp endTime;

    public CouponVO(Coupon coupon){
    	this.id=coupon.getId();
    	this.name=coupon.getName();
        this.discountAmount=coupon.getDiscountAmount();
        this.targetAmount=coupon.getTargetAmount();
        this.startTime=coupon.getStartTime();
        this.endTime=coupon.getEndTime();
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public double getTargetAmount() {
        return targetAmount;
    }

    public Timestamp getEndTime() {
        return endTime;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public void setTargetAmount(double targetAmount) {
        this.targetAmount = targetAmount;
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
}
