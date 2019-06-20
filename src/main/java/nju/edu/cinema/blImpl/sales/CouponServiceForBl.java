package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.po.Coupon;

/**
 * Created By Wang Youxin on 2019/06/20
 */

public interface CouponServiceForBl {
    /**
     * 根据Coupon的Id查找Coupon并返回
     * @param couponId
     * @return
     */
    Coupon selectCouponById(int couponId);

    /**
     * 根据couponId和userId删除优惠券
     * @param couponId
     * @param userId
     */
    void deleteCoupon(int couponId, int userId);

    /**
     * 根据couponId和userId插入优惠券
     * @param couponId
     * @param userId
     */
    void insertCoupon(int couponId, int userId);
}
