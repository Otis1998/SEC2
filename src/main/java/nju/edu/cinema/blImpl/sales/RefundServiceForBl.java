package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.po.RefundStrategy;

public interface RefundServiceForBl {
    /**
     * 获取当前退票策略
     * @return
     */
    RefundStrategy getCurrentStrategyForBl();
}
