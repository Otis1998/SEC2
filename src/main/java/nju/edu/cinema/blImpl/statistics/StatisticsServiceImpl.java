package nju.edu.cinema.blImpl.statistics;

import nju.edu.cinema.bl.statistics.StatisticsService;
import nju.edu.cinema.data.statistics.StatisticsMapper;
import nju.edu.cinema.po.*;
import nju.edu.cinema.vo.*;
import nju.edu.cinema.po.*;
import nju.edu.cinema.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/16 1:34 PM
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {
    @Autowired
    private StatisticsMapper statisticsMapper;
    @Override
    public ResponseVO getScheduleRateByDate(Date date) {
        try{
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieScheduleTimeList2MovieScheduleTimeVOList(statisticsMapper.selectMovieScheduleTimes(requireDate, nextDate)));

        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getTotalBoxOffice() {
        try {
            return ResponseVO.buildSuccess(movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(statisticsMapper.selectMovieTotalBoxOffice()));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getAudiencePriceSevenDays() {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date today = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            Date startDate = getNumDayAfterDate(today, -6);
            List<AudiencePriceVO> audiencePriceVOList = new ArrayList<>();
            for(int i = 0; i < 7; i++){
                AudiencePriceVO audiencePriceVO = new AudiencePriceVO();
                Date date = getNumDayAfterDate(startDate, i);
                audiencePriceVO.setDate(date);
                List<AudiencePrice> audiencePriceList = statisticsMapper.selectAudiencePrice(date, getNumDayAfterDate(date, 1));
                double totalPrice = audiencePriceList.stream().mapToDouble(item -> item.getTotalPrice()).sum();
                audiencePriceVO.setPrice(Double.parseDouble(String.format("%.2f", audiencePriceList.size() == 0 ? 0 : totalPrice / audiencePriceList.size())));
                audiencePriceVOList.add(audiencePriceVO);
            }
            return ResponseVO.buildSuccess(audiencePriceVOList);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getMoviePlacingRateByDate(Date date) {
        try {
            Date requireDate = date;
            if(requireDate == null){
                requireDate = new Date();
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            requireDate = simpleDateFormat.parse(simpleDateFormat.format(requireDate));

            Date nextDate = getNumDayAfterDate(requireDate, 1);
            return ResponseVO.buildSuccess(movieAudAndSeatNumList2MoviePlacingRateVOList
                    (statisticsMapper.selectAudienceNumber(requireDate, nextDate), statisticsMapper.selectSeatNumber(requireDate, nextDate)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public ResponseVO getPopularMovies(int days, int movieNum) {
        //要求见接口说明
    	try {
    		Date today=new Date();
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    		today = simpleDateFormat.parse(simpleDateFormat.format(today));
    		Date oldDate = getNumDayAfterDate(today, -days);
            return ResponseVO.buildSuccess(movieRecentFareList2MovieRecentFareVOList(statisticsMapper.selectMovieRecentFare(oldDate, movieNum)));
        }catch (Exception e){
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }


	/**
     * 获得num天后的日期
     * @param oldDate
     * @param num
     * @return
     */
    Date getNumDayAfterDate(Date oldDate, int num){
        Calendar calendarTime = Calendar.getInstance();
        calendarTime.setTime(oldDate);
        calendarTime.add(Calendar.DAY_OF_YEAR, num);
        return calendarTime.getTime();
    }
    private List<MovieRecentFareVO> movieRecentFareList2MovieRecentFareVOList(List<MovieRecentFare> movieRecentFareList) {
    	List<MovieRecentFareVO> movieRecentFareVOList=new ArrayList<>();
    	for(MovieRecentFare movieRecentFare : movieRecentFareList) {
    		movieRecentFareVOList.add(new MovieRecentFareVO(movieRecentFare));
    	}
		return movieRecentFareVOList;
	}

    private List<MovieScheduleTimeVO> movieScheduleTimeList2MovieScheduleTimeVOList(List<MovieScheduleTime> movieScheduleTimeList){
        List<MovieScheduleTimeVO> movieScheduleTimeVOList = new ArrayList<>();
        for(MovieScheduleTime movieScheduleTime : movieScheduleTimeList){
            movieScheduleTimeVOList.add(new MovieScheduleTimeVO(movieScheduleTime));
        }
        return movieScheduleTimeVOList;
    }


    private List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeList2MovieTotalBoxOfficeVOList(List<MovieTotalBoxOffice> movieTotalBoxOfficeList){
        List<MovieTotalBoxOfficeVO> movieTotalBoxOfficeVOList = new ArrayList<>();
        for(MovieTotalBoxOffice movieTotalBoxOffice : movieTotalBoxOfficeList){
            movieTotalBoxOfficeVOList.add(new MovieTotalBoxOfficeVO(movieTotalBoxOffice));
        }
        return movieTotalBoxOfficeVOList;
    }


    private List<MoviePlacingRateVO> movieAudAndSeatNumList2MoviePlacingRateVOList(List<MovieAudienceNumber> movieAudienceNumberList, List<MovieSeatNumber> movieSeatNumberList){
        List<MoviePlacingRateVO> moviePlacingRateVOList = new ArrayList<>();
        for (MovieAudienceNumber movieAudienceNumber : movieAudienceNumberList){
            for (MovieSeatNumber movieSeatNumber : movieSeatNumberList){
                if (movieAudienceNumber.getMovieId().equals(movieSeatNumber.getMovieId())){
                    moviePlacingRateVOList.add
                            (new MoviePlacingRateVO(
                                    movieAudienceNumber.getMovieId(),
                                    Double.parseDouble(String.format("%.2f", movieSeatNumber.getSeatNumber() == 0 ? 0 : new Double(movieAudienceNumber.getAudienceNumber()) / new Double(movieSeatNumber.getSeatNumber()) * 100)),
                                    movieAudienceNumber.getName()
                            ));
                }
            }
        }
        return moviePlacingRateVOList;
    }
}
