package nju.edu.cinema.bl.promotion;

import nju.edu.cinema.vo.ActivityForm;
import nju.edu.cinema.vo.ResponseVO;

/**
 * Created by liying on 2019/4/20.
 */
public interface ActivityService {
    
    ResponseVO publishActivity(ActivityForm activityForm);

    ResponseVO getActivities();




}
