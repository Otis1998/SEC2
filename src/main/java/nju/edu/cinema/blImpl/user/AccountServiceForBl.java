package nju.edu.cinema.blImpl.user;

import nju.edu.cinema.vo.UserVO;

/**
 * @author 王煜霄
 * @date 2019/5/9
 */

public interface AccountServiceForBl {
    /**
     * 根据id查找用户
     * @param userId
     * @return
     */
    UserVO getAccountById(int userId);
}
