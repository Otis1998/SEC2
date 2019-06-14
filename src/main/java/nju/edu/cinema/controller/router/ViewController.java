package nju.edu.cinema.controller.router;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author deng
 * @date 2019/03/11
 */
@Controller
public class ViewController {
    @RequestMapping(value = "/index")
    public String getIndex() {
        return "index";
    }

    @RequestMapping(value = "/signUp")
    public String getSignUp() {
        return "signUp";
    }

    @RequestMapping(value = "/admin/movie/manage")
    public String getAdminMovieManage() {
        return "adminMovieManage";
    }
    @RequestMapping(value="/superAdmin/people/manage")
    public String getSuperAdminPeopleManage() {
    	return "superAdminPeopleManage";
    }
    @RequestMapping(value="/superAdmin/coupon/manage")
    public String getSuperAdminCouponManage() {
    	return "superAdminCouponManage";
    }
    @RequestMapping(value="/superAdmin/VIPCard/manage")
    public String getSuperAdminVIPCardManage() {
    	return "superAdminVIPCardManage";
    }
    @RequestMapping(value = "/superAdmin/refund/manage")
    public String getSuperAdminRefundStrategyManage() {
        return "superAdminRefundStrategyManage";
    }
    @RequestMapping(value = "/admin/session/manage")
    public String getAdminSessionManage() {
        return "adminScheduleManage";
    }

    @RequestMapping(value = "/admin/cinema/manage")
    public String getAdminCinemaManage() {
        return "adminCinemaManage";
    }

    @RequestMapping(value = "/admin/promotion/manage")
    public String getAdminPromotionManage() {
        return "adminPromotionManage";
    }

    @RequestMapping(value = {"/superAdmin/cinema/statistic", "/admin/cinema/statistic"})
    public String getAdminCinemaStatistic() {
        return "adminCinemaStatistic";
    }

    @RequestMapping(value = "/admin/movieDetail")
    public String getAdminMovieDetail(@RequestParam int id) { return "adminMovieDetail"; }

    @RequestMapping(value = "/user/home")
    public String getUserHome() {
        return "userHome";
    }

    @RequestMapping(value = "/user/selfPage")
    public String getUserBuy() {
        return "userSelfPage";
    }

    @RequestMapping(value = "/user/movieDetail")
    public String getUserMovieDetail(@RequestParam int id) {
        return "userMovieDetail";
    }

    @RequestMapping(value = "/user/movieDetail/buy")
    public String getUserMovieBuy(@RequestParam int id) {
        return "userMovieBuy";
    }

    @RequestMapping(value = "/user/movie")
    public String getUserMovie() {
        return "userMovie";
    }

    @RequestMapping(value = "/user/member")
    public String getUserMember() {
        return "userMember";
    }

    @RequestMapping(value = "/user/chargeRecord")
    public String getUserChargeRecord() {
        return "userChargeRecord";
    }
}
