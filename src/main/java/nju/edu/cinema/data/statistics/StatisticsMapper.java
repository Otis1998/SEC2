package nju.edu.cinema.data.statistics;

import nju.edu.cinema.po.*;
import nju.edu.cinema.po.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:43 PM
 */
@Mapper
public interface StatisticsMapper {
	/**
	 * 查询oldDate到今天前几部电影的票房（降序排列）
	 * @return
	 */
    List<MovieRecentFare> selectMovieRecentFare(@Param("oldDate") Date olDate, @Param("movieNum")int movieNum);

	/**
     * 查询date日期每部电影的排片次数
     * @param date
     * @return
     */
    List<MovieScheduleTime> selectMovieScheduleTimes(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询所有电影的总票房（包括已经下架的，降序排列）
     * @return
     */
    List<MovieTotalBoxOffice> selectMovieTotalBoxOffice();

    /**
     * 查询某天每个客户的购票金额
     * @param date
     * @param nextDate
     * @return
     */
    List<AudiencePrice> selectAudiencePrice(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询date日期每部电影的观众人数
     * @param date
     * @param nextDate
     * @return
     */
    List<MovieAudienceNumber> selectAudienceNumber(@Param("date") Date date, @Param("nextDate") Date nextDate);

    /**
     * 查询date日期每部电影的座位数
     * @param date
     * @param nextDate
     * @return
     */
    List<MovieSeatNumber> selectSeatNumber(@Param("date") Date date, @Param("nextDate") Date nextDate);

}
