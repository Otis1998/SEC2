package nju.edu.cinema.blImpl.promotion;

import nju.edu.cinema.bl.promotion.CouponService;
import nju.edu.cinema.blImpl.sales.CouponServiceForBl;
import nju.edu.cinema.data.promotion.CouponMapper;
import nju.edu.cinema.po.Coupon;
import nju.edu.cinema.vo.CouponForm;
import nju.edu.cinema.vo.CouponVO;
import nju.edu.cinema.vo.ResponseVO;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by liying on 2019/4/17.
 */
@Service
public class CouponServiceImpl implements CouponService, CouponServiceForBl {

    @Autowired
    CouponMapper couponMapper;

    @Override
    public ResponseVO getCouponsByUser(int userId) {
        try {
            return ResponseVO.buildSuccess(couponMapper.selectCouponByUser(userId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO addCoupon(CouponForm couponForm) {
        try {
            Coupon coupon=new Coupon();
            coupon.setName(couponForm.getName());
            coupon.setDescription(couponForm.getDescription());
            coupon.setTargetAmount(couponForm.getTargetAmount());
            coupon.setDiscountAmount(couponForm.getDiscountAmount());
            coupon.setStartTime(couponForm.getStartTime());
            coupon.setEndTime(couponForm.getEndTime());
            couponMapper.insertCoupon(coupon);
            return ResponseVO.buildSuccess(coupon);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO issueCoupon(int couponId, int userId) {
        try {
        	couponMapper.insertCouponUser(couponId,userId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }

    }

	@Override
	public ResponseVO getAllCoupons() {
		try {
            return ResponseVO.buildSuccess(couponList2CouponVOList(couponMapper.selectAllCoupons()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
	}

	private Object couponList2CouponVOList(List<Coupon> couponList) {
		List<CouponVO> couponVOList = new ArrayList<>();
        for(Coupon coupon : couponList){
        	couponVOList.add(new CouponVO(coupon));
        }
        return couponVOList;
	}

	@Override
    public Coupon selectCouponById(int couponId){
        return couponMapper.selectById(couponId);
    }

    @Override
    public void deleteCoupon(int couponId, int userId){
        couponMapper.deleteCouponUser(couponId,userId);
    }

    @Override
    public void insertCoupon(int couponId,int userId){
        couponMapper.insertCouponUser(couponId,userId);
    }
}
