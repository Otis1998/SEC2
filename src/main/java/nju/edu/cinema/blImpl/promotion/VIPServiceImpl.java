package nju.edu.cinema.blImpl.promotion;

import nju.edu.cinema.bl.promotion.VIPService;
import nju.edu.cinema.blImpl.management.rechargePresent.RechargePresentServiceForBl;
import nju.edu.cinema.data.promotion.VIPCardMapper;
import nju.edu.cinema.data.promotion.VIPChargeMapper;
import nju.edu.cinema.vo.VIPCardForm;
import nju.edu.cinema.po.ChargeRecord;
import nju.edu.cinema.po.VIPCard;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.VIPInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by liying on 2019/4/14.
 */
@Service
public class VIPServiceImpl implements VIPService {
    @Autowired
    VIPCardMapper vipCardMapper;
    @Autowired
    VIPChargeMapper vipChargeMapper;
    @Autowired
    private RechargePresentServiceForBl rechargePresentServiceForBl;
    

    @Override
    public ResponseVO addVIPCard(int userId) {
        VIPCard vipCard = new VIPCard();
        vipCard.setUserId(userId);
        vipCard.setBalance(0);
        try {
            int id = vipCardMapper.insertOneCard(vipCard);
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardById(int id) {
        try {
            return ResponseVO.buildSuccess(vipCardMapper.selectCardById(id));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getVIPInfo() {
        VIPInfoVO vipInfoVO = new VIPInfoVO();
        VIPCard card=new VIPCard();
        vipInfoVO.setDescription(rechargePresentServiceForBl.getPresentDescription());
        vipInfoVO.setPrice(card.getPrice());
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {
        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = rechargePresentServiceForBl.calculate(vipCardForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            //TODO
            //获取当前优惠政策
            //判断当前充值金额是否满足某一优惠政策
            //根据优惠政策判断赠送金额
            //根据充值金额和赠送金额修改余额
            //插入一条新的充值记录
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
            ChargeRecord chargeRecord=new ChargeRecord();
            chargeRecord.setUserId(vipCard.getUserId());
            chargeRecord.setVipId(vipCardForm.getVipId());
            chargeRecord.setChargeSum(vipCardForm.getAmount());
            chargeRecord.setBonusSum(balance-vipCardForm.getAmount());
            chargeRecord.setBalance(vipCard.getBalance());
            vipChargeMapper.insertChargeRecord(chargeRecord);
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCardByUserId(int userId) {
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipCard);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getChargeRecord(int userId){
        try {
            VIPCard vipCard = vipCardMapper.selectCardByUserId(userId);
            if(vipCard==null){
                return ResponseVO.buildFailure("用户卡不存在");
            }
            return ResponseVO.buildSuccess(vipChargeMapper.selectChargeRecordByUser(userId));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

}
