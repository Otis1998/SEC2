package nju.edu.cinema.po;

public class Present {
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
