package nju.edu.cinema.data.promotion;


import nju.edu.cinema.po.VIPCard;


import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Created by liying on 2019/4/14.
 */
@Mapper
public interface VIPCardMapper {

    int insertOneDescription(VIPCard vipCardInfo);

	void updateCardInfo(@Param("price") double price,@Param("full") int full,@Param("present") int present,@Param("description") String description);

	int insertOneCard(VIPCard vipCard);

    VIPCard selectCardById(int id);

    void updateCardBalance(@Param("id") int id,@Param("balance") double balance);

	VIPCard selectCardByUserId(@Param("userId") int userId);

}
