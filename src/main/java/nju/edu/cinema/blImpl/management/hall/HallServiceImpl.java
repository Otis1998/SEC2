package nju.edu.cinema.blImpl.management.hall;

import nju.edu.cinema.bl.management.HallService;
import nju.edu.cinema.data.management.HallMapper;
import nju.edu.cinema.po.Hall;
import nju.edu.cinema.po.Seat;
import nju.edu.cinema.vo.HallForm;
import nju.edu.cinema.vo.HallVO;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.SeatForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
@Service
public class HallServiceImpl implements HallService, HallServiceForBl {
    @Autowired
    private HallMapper hallMapper;

    @Override
    public ResponseVO searchAllHall() {
        try {
            return ResponseVO.buildSuccess(hallList2HallVOList(hallMapper.selectAllHall()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    @Override
    public HallVO getHallById(int id) {
        try {
            Hall hall = hallMapper.selectHallById(id);
            int[][] seats = getAvailableSeats(hall);
            return new HallVO(hall, seats);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public ResponseVO addHall(HallForm hallForm) {
        try {
            int hallId = hallMapper.insertOneHall(hallForm);
            List<SeatForm> seatForms = hallForm.getSeats();
            for (SeatForm seatForm : seatForms) {
                Seat seat = new Seat();
                seat.setColumn(seatForm.getColumnIndex());
                seat.setRow(seatForm.getRowIndex());
                seat.setHallId(hallId);
                hallMapper.insertSeat(seat);
            }
            return ResponseVO.buildSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseVO.buildFailure("失败");
        }
    }

    private List<HallVO> hallList2HallVOList(List<Hall> hallList){
        List<HallVO> hallVOList = new ArrayList<>();
        for(Hall hall : hallList){
            int[][] seats = getAvailableSeats(hall);
            hallVOList.add(new HallVO(hall, seats));
        }
        return hallVOList;
    }

    private int[][] getAvailableSeats(Hall hall){
        int[][] seats = new int[hall.getRow()][hall.getColumn()];
        for (int i=0;i<hall.getRow();i++){
            for (int j=0;j<hall.getColumn();j++) {
                seats[i][j] = -1;
            }
        }
        List<Seat> availableSeats = hallMapper.selectSeatsByHallId(hall.getId());
        availableSeats.forEach(seat -> seats[seat.getRow()-1][seat.getColumn()-1] = 0);
        return seats;
    }
}
