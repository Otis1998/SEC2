package nju.edu.cinema.blImpl.management.refundStrategy;

import nju.edu.cinema.bl.management.RefundService;

import nju.edu.cinema.blImpl.sales.RefundServiceForBl;
import nju.edu.cinema.data.management.RefundMapper;
import nju.edu.cinema.po.RefundStrategy;
import nju.edu.cinema.vo.RefundStrategyForm;
import nju.edu.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundServiceImpl implements RefundService, RefundServiceForBl {

    private static final String WRONG_CHARGE_NUMBER_ERROR_MESSAGE = "手续费必须为正数";
    private static final String WRONG_TIME_ERROR_MESSAGE = "时间不能为负数";

    @Autowired
    private RefundMapper refundMapper;

    @Override
    public ResponseVO addStrategy(RefundStrategyForm strategy) {
        try {
            ResponseVO responseVO = preCheck(strategy);
            if (!responseVO.getSuccess()) {
                return responseVO;
            }
            refundMapper.insertRefundStrategy(refundStrategyForm2RefundStrategy(strategy));
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchAllStrategies() {
        try {
            return ResponseVO.buildSuccess(refundMapper.selectAllStrategies());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO searchStrategyById(Integer strategyId) {
        try {
            return ResponseVO.buildSuccess(refundMapper.selectStrategyById(strategyId));

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getCurrentStrategy() {
        try {
            RefundStrategy strategy = refundMapper.selectCurrentStrategy();
            if (strategy != null){
                return ResponseVO.buildSuccess(strategy);
            }
            return ResponseVO.buildFailure("无退票策略");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * @author Wang Youxin
     * @Date 2019/06/12 14:00
     * @return
     */
    @Override
    public RefundStrategy getCurrentStrategyForBl(){
        return refundMapper.selectCurrentStrategy();
    }

    @Override
    public ResponseVO setCurrentStrategy(Integer strategyId) {
        try {
            refundMapper.updateCurrentStrategy(strategyId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO updateStrategy(RefundStrategyForm strategy) {
        try {
            ResponseVO responseVO = preCheck(strategy);
            if (!responseVO.getSuccess()) {
                return responseVO;
            }
            refundMapper.updateRefundStrategy(refundStrategyForm2RefundStrategy(strategy));
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO deleteStrategy(Integer strategyId) {
        try {
            refundMapper.deleteStrategy(strategyId);
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    /**
     * 前置检查，手续费和时间必须为正数
     * @param strategy
     * @return
     */
    private ResponseVO preCheck(RefundStrategyForm strategy){
        if (strategy.getCharge() <= 0) {
            return ResponseVO.buildFailure(WRONG_CHARGE_NUMBER_ERROR_MESSAGE);
        }

        /*else if (strategy.getAvailableHour().getTime() < new Time(0).getTime()) {
            return ResponseVO.buildFailure(WRONG_TIME_ERROR_MESSAGE);
        }*/

        else {
            return ResponseVO.buildSuccess();
        }
    }

    private RefundStrategy refundStrategyForm2RefundStrategy(RefundStrategyForm strategy) {
        RefundStrategy strategy1 = new RefundStrategy();
        strategy1.setId(strategy.getId());
        strategy1.setAvailableHour(strategy.getAvailableHour());
        strategy1.setCharge(strategy.getCharge());
        strategy1.setName(strategy.getName());
        strategy1.setRefundable(strategy.getRefundable());
        strategy1.setState(strategy.getState());
        return strategy1;
    }

    private List<RefundStrategyForm> refundStrategyList2RefundStrategyFormList(List<RefundStrategy> strategyList) {
        List<RefundStrategyForm> strategyList1 = new ArrayList<>();
        for (RefundStrategy strategy : strategyList){
            strategyList1.add(new RefundStrategyForm(strategy.getId(), strategy.getName(),
                    strategy.getRefundable(), strategy.getAvailableHour(), strategy.getCharge(), strategy.getState()));
        }
        return strategyList1;
    }
}
