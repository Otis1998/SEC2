package nju.edu.cinema.data.management;

import nju.edu.cinema.po.RefundStrategy;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RefundMapper {

    /**
     * 新增退票策略
     * @param refundStrategy
     * @return
     */
    int insertRefundStrategy(RefundStrategy refundStrategy);

    /**
     * 查询所有退票策略
     * @return
     */
    List<RefundStrategy> selectAllStrategies();

    /**
     * 通过id查询退票策略
     * @param id
     * @return
     */
    RefundStrategy selectStrategyById(int id);

    /**
     * 查询当前的退票策略
     * @return
     */
    RefundStrategy selectCurrentStrategy();

    /**
     * 设置当前退票策略
     */
    void updateCurrentStrategy(int id);

    /**
     * 更新退票策略
     * @param refundStrategy
     */
    void updateRefundStrategy(RefundStrategy refundStrategy);

    /**
     * 删除退票策略
     * @param id
     */
    void deleteStrategy(int id);
}
