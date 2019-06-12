package nju.edu.cinema.controller.management;

import nju.edu.cinema.bl.management.RefundService;
import nju.edu.cinema.vo.RefundStrategyForm;
import nju.edu.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class RefundController {
    @Autowired
    private RefundService refundService;

    @RequestMapping(value = "/refund-strategy/add", method = RequestMethod.POST)
    public ResponseVO addStrategy(@RequestBody RefundStrategyForm strategy){
        return refundService.addStrategy(strategy);
    }

    @RequestMapping(value = "/refund-strategy/all", method = RequestMethod.GET)
    public ResponseVO searchAllStrategy(){
        return refundService.searchAllStrategies();
    }

    @RequestMapping(value = "/refund-strategy/current", method = RequestMethod.GET)
    public ResponseVO getCurrentStrategy(){
        return refundService.getCurrentStrategy();
    }

    @RequestMapping(value = "/refund-strategy/get", method = RequestMethod.GET)
    public ResponseVO getStrategyById(@RequestParam int strategyId){
        return refundService.searchStrategyById(strategyId);
    }

    @RequestMapping(value = "/refund-strategy/enable/{id}", method = RequestMethod.GET)
    public ResponseVO setCurrentStrategy(@PathVariable("id") Integer strategyId){
        return refundService.setCurrentStrategy(strategyId);
    }

    @RequestMapping(value = "/refund-strategy/update", method = RequestMethod.POST)
    public ResponseVO updateStrategy(@RequestBody RefundStrategyForm strategy){
        return refundService.updateStrategy(strategy);
    }

    @RequestMapping(value = "/refund-strategy/delete/{id}", method = RequestMethod.DELETE)
    public ResponseVO deleteStrategy(@PathVariable("id") Integer strategyId){
        return refundService.deleteStrategy(strategyId);
    }
}
