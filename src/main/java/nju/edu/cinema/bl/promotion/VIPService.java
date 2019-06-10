package nju.edu.cinema.bl.promotion;

import nju.edu.cinema.vo.VIPCardForm;
import nju.edu.cinema.vo.VIPInfoVO;
import nju.edu.cinema.vo.ResponseVO;



/**
 * Created by liying on 2019/4/14.
 */

public interface VIPService {

    ResponseVO addVIPCard(int userId);

    ResponseVO getCardById(int id);

    ResponseVO getVIPInfo();

    ResponseVO charge(VIPCardForm vipCardForm);

    ResponseVO getCardByUserId(int userId);

    ResponseVO getChargeRecord(int userId);

}
