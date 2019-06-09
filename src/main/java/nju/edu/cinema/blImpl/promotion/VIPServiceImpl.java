package nju.edu.cinema.blImpl.promotion;

import nju.edu.cinema.bl.promotion.VIPService;
import nju.edu.cinema.data.promotion.VIPCardMapper;
import nju.edu.cinema.vo.VIPCardForm;
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
        vipInfoVO.setDescription(card.getDescription());
        vipInfoVO.setPrice(card.getPrice());
        return ResponseVO.buildSuccess(vipInfoVO);
    }

    @Override
    public ResponseVO charge(VIPCardForm vipCardForm) {

        VIPCard vipCard = vipCardMapper.selectCardById(vipCardForm.getVipId());
        if (vipCard == null) {
            return ResponseVO.buildFailure("会员卡不存在");
        }
        double balance = vipCard.calculate(vipCardForm.getAmount());
        vipCard.setBalance(vipCard.getBalance() + balance);
        try {
            vipCardMapper.updateCardBalance(vipCardForm.getVipId(), vipCard.getBalance());
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
        //TODO
        return null;
    }
    
    @Override
    public ResponseVO addCardInfo(VIPInfoVO vipInfoVO) {
    	VIPCard vipCardInfo = new VIPCard();
    	vipCardInfo.setDescriptionId(vipInfoVO.getDescriptionId());
    	vipCardInfo.setDescription(vipInfoVO.getDescription());
    	vipCardInfo.setFull(vipInfoVO.getFull());
    	vipCardInfo.setPresent(vipInfoVO.getPresent());
    	try {
    		int id = vipCardMapper.insertOneDescription(vipCardInfo);
			return ResponseVO.buildSuccess(vipCardMapper.selectDescriptionById(id));
		} catch (Exception e) {
			e.printStackTrace();
            return ResponseVO.buildFailure("失败");
		}
    }
    @Override
    public ResponseVO changeCardInfo(VIPInfoVO vipInfoVO) {
    	VIPCard vipCardInfo = vipCardMapper.selectDescriptionById(vipInfoVO.getDescriptionId());
		try {
			vipCardMapper.updateCardInfo(vipCardInfo.getPrice(), vipCardInfo.getFull(), vipCardInfo.getPresent(), vipCardInfo.getDescription());
			return ResponseVO.buildSuccess(vipCardInfo);
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseVO.buildFailure("失败");
		}
	}

}
