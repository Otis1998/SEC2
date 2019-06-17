package nju.edu.cinema.bl.user;

import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserInfoForm;
import nju.edu.cinema.vo.UserVO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

    /**
     * 修改密码
     */
    public ResponseVO changePassword(UserForm userForm);

    /**
     * 根据用户id获取用户个人信息
     */
    public ResponseVO getUserInfo(int userId);

    /**
     * 修改用户个人信息
     */
    public ResponseVO changeUserInfo(UserInfoForm userInfoForm);

    /**
     * 用户输入验证码的验证
     * @param session
     * @param verifyCode
     * @return
     */
    public ResponseVO check(HttpSession session, String verifyCode);
}
