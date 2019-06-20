package nju.edu.cinema.blImpl.sales;

import nju.edu.cinema.po.Activity;

import java.util.List;

public interface ActivityServiceForBl {
    /**
     * 根据电影Id查询限定电影的电影的优惠券
     * @param movieId
     * @return
     */
    List<Activity> selectActivitiesByMovie(int movieId);

    List<Activity> selectActivitiesWithoutMovie();
}
