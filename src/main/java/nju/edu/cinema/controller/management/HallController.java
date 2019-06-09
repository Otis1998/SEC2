package nju.edu.cinema.controller.management;

import nju.edu.cinema.bl.management.HallService;
import nju.edu.cinema.vo.HallForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**影厅管理
 * @author fjj
 * @date 2019/4/12 1:59 PM
 */
@RestController
public class HallController {
    @Autowired
    private HallService hallService;

    @RequestMapping(value = "hall/all", method = RequestMethod.GET)
    public ResponseVO searchAllHall(){
        return hallService.searchAllHall();
    }

    @RequestMapping(value = "/hall/add", method = RequestMethod.POST)
    public ResponseVO addHall(@RequestBody HallForm hallForm){
        return hallService.addHall(hallForm);
    }
}
