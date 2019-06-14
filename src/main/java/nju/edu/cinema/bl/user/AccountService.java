package nju.edu.cinema.bl.user;

import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserVO;

/**
 * @author huwen
 * @date 2019/3/23
 */
public interface AccountService {

    /**
     * 注册账号
     * @return
     */
    public ResponseVO registerAccount(UserForm userForm);

    /**
     * 用户登录，登录成功会将用户信息保存在session中
     * @return
     */
    public UserVO login(UserForm userForm);

}
