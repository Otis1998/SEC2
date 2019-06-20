package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.po.VIPCard;

public interface VIPServiceForBl {
    /**
     * 更新会员卡余额
     * @param userId
     * @param refundedMoney
     */
    void updateVIPCardBalance(int userId,double refundedMoney);

    /**
     *通过用户id获取其会员卡
     * @param userId
     * @return
     */
    VIPCard getCardByUserIdForBl(int userId);
}
