package nju.edu.cinema.blImpl.management.rechargePresent;


public interface RechargePresentServiceForBl {
	/**
	 * 获得优惠策略描述
	 * @return
	 */
	String getPresentDescription();
	/**
	 * 计算赠送金额
	 * @return
	 */
	double calculate(int amount);
}
