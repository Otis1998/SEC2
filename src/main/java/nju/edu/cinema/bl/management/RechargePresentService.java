package nju.edu.cinema.bl.management;

import nju.edu.cinema.vo.PresentForm;
import nju.edu.cinema.vo.ResponseVO;

public interface RechargePresentService {
	/**
	 * 发布充值优惠
	 * @param presentForm
	 * @return
	 */
	public ResponseVO publishPresent(PresentForm presentForm);
	/**
	 * 删除充值优惠
	 * @param id
	 * @return
	 */
	public ResponseVO deletePresent(int id);
	/**
	 * 修改充值优惠
	 * @param presentForm
	 * @return
	 */
	public ResponseVO updatePresent(PresentForm presentForm);
	/**
	 * 查询所有充值优惠
	 * @param presentForm
	 * @return
	 */
	public ResponseVO searchAllPresents();
}
