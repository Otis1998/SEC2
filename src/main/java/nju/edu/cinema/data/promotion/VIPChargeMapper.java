package nju.edu.cinema.data.promotion;

import nju.edu.cinema.po.ChargeRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface VIPChargeMapper {
    int insertChargeRecord(ChargeRecord chargeRecord);

    List<ChargeRecord> selectChargeRecordByUser(@Param("user_id")int userId);
}
