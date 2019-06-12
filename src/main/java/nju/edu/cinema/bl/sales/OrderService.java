package nju.edu.cinema.bl.sales;

import nju.edu.cinema.po.Order;
import nju.edu.cinema.vo.ResponseVO;

public interface OrderService {
    /**
     * TODO:完成退票（根据购票的订单号进行退票）
     * @author Wang Youxin
     * @Date 2019/06/06 23:00
     * @param orderId
     * @return
     */
    ResponseVO cancelOrder(int orderId);

    /**
     * 获得用户已支付订单
     * @param userId
     * @return
     */
    ResponseVO getOrderByUser(int userId);

    void addOrder(Order order);

	ResponseVO getTargetUserByCumulative(double cumulative);

}
