package nju.edu.cinema.data.management;

import nju.edu.cinema.po.Hall;
import nju.edu.cinema.po.Seat;
import nju.edu.cinema.vo.HallForm;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author fjj
 * @date 2019/4/11 3:46 PM
 */
@Mapper
public interface HallMapper {
    /**
     * 插入一条影厅信息
     * @param addHallForm
     * @return
     */
    int insertOneHall(HallForm addHallForm);

    /**
     * 查询所有影厅信息
     * @return
     */
    List<Hall> selectAllHall();

    /**
     * 根据id查询影厅
     * @return
     */
    Hall selectHallById(@Param("hallId") int hallId);

    /**
     * 根据影厅id查询影厅的座位
     * @param hallId
     * @return
     */
    List<Seat> selectSeatsByHallId(@Param("hallId") int hallId);

    /**
     * 根据影厅id插入座位记录
     * @param seat
     * @return
     */
    int insertSeat(Seat seat);
}
