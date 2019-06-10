package nju.edu.cinema.blImpl.management.rechargePresent;

import nju.edu.cinema.vo.ResponseVO;

public interface RechargePresentServiceForBl {
	/**
	 * 根据充值优惠id查询策略
	 * @param id
	 * @return
	 */
	public ResponseVO searchPresentById(int id);
	
}
