package nju.edu.cinema.controller.sales;

import nju.edu.cinema.bl.sales.OrderService;
import nju.edu.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    @GetMapping("/get/{userId}")
    public ResponseVO getOrderByUserId(@PathVariable int userId){
        return orderService.getOrderByUser(userId);
    }
}
