package nju.edu.cinema.bl.management;

import nju.edu.cinema.vo.HallForm;
import nju.edu.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();

    /**
     * 添加影厅
     * @param hallForm
     * @return
     */
    ResponseVO addHall(HallForm hallForm);

    /**
     * 更新影厅信息
     * @param hallForm
     * @return
     */
    ResponseVO modifyHall(HallForm hallForm);

    /**
     * 删除影厅
     * @param hallId
     * @return
     */
    ResponseVO deleteHall(Integer hallId);
}
