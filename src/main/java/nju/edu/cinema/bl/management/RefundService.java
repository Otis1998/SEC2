package nju.edu.cinema.bl.management;

import nju.edu.cinema.vo.RefundStrategyForm;
import nju.edu.cinema.vo.ResponseVO;

public interface RefundService {

    /**
     * 新增退票策略
     * @param strategy
     * @return
     */
    ResponseVO addStrategy(RefundStrategyForm strategy);

    /**
     * 查找所有退票策略
     * @return
     */
    ResponseVO searchAllStrategies();

    /**
     * 根据id查找退票策略
     * @param strategyId
     * @return
     */
    ResponseVO searchStrategyById(Integer strategyId);

    /**
     * 更新退票策略
     * @param strategy
     * @return
     */
    ResponseVO updateStrategy(RefundStrategyForm strategy);

    /**
     * 删除退票策略
     * @param strategyId
     * @return
     */
    ResponseVO deleteStrayegy(Integer strategyId);
}
