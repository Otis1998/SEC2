package nju.edu.cinema.vo;

import nju.edu.cinema.po.Present;

public class PresentVO {
	/**
	 * 充值优惠id
	 */
	private Integer id;
	/**
	 * 目标金额
	 */
	private double targetAmount;
	/**
	 * 赠送金额
	 */
	private double presentAmount;
	
	public PresentVO(Present present) {
		this.id=present.getId();
		this.targetAmount=present.getTargetAmount();
		this.presentAmount=present.getPresentAmount();
	}
	public double getTargetAmount() {
		return targetAmount;
	}
	public void setTargetAmount(double targetAmount) {
		this.targetAmount = targetAmount;
	}
	public double getPresentAmount() {
		return presentAmount;
	}
	public void setPresentAmount(double presentAmount) {
		this.presentAmount = presentAmount;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
