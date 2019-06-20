package nju.edu.cinema.bl.sales;

import nju.edu.cinema.po.Order;
import nju.edu.cinema.vo.ResponseVO;

public interface OrderService {
    /**
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

    /**
     * 出票（模拟打印订单）
     * @param orderId
     * @return
     */
    ResponseVO printOrder(int orderId);

	ResponseVO getTargetUserByCumulative(double cumulative);

}
