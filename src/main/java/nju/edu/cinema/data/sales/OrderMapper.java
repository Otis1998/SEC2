package nju.edu.cinema.data.sales;

import nju.edu.cinema.po.Cumulative;
import nju.edu.cinema.po.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderMapper {

    int insertOrder(Order order);

    void deleteOrderById(int orderId);

    Order selectByOrderId(int OrderId);

    List<Order> selectByUserId(int UserId);

    int selectMaxId();

	List<Cumulative> selectTargetUserByCumulative(double cumulative);
}
