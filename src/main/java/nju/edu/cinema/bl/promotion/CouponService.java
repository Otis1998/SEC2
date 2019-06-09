package nju.edu.cinema.bl.promotion;

import java.util.List;

import nju.edu.cinema.vo.CouponForm;
import nju.edu.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/17.
 */
public interface CouponService {

    ResponseVO getCouponsByUser(int userId);

    ResponseVO addCoupon(CouponForm couponForm);

    ResponseVO issueCoupon(int couponId,List<Integer> userIdList);

	ResponseVO getAllCoupons();

}
