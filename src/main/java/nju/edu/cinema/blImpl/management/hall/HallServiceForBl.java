package nju.edu.cinema.blImpl.management.hall;

import nju.edu.cinema.vo.HallVO;

/**
 * @author fjj
 * @date 2019/4/28 12:27 AM
 */
public interface HallServiceForBl {
    /**
     * 搜索影厅
     * @param id
     * @return
     */
    HallVO getHallById(int id);
}
