package nju.edu.cinema.blImpl.user;

import nju.edu.cinema.bl.user.AccountService;
import nju.edu.cinema.data.user.AccountMapper;
import nju.edu.cinema.data.user.UserInfoMapper;
import nju.edu.cinema.po.User;
import nju.edu.cinema.vo.UserInfoForm;
import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService, AccountServiceForBl {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            accountMapper.createNewAccount(0,userForm.getUsername(), userForm.getPassword());
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
        return ResponseVO.buildSuccess();
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !user.getPassword().equals(userForm.getPassword())) {
            return null;
        }
        return new UserVO(user);
    }

    @Override
    public UserVO getAccountById(int userId) {

        User user = accountMapper.getAccountById(userId);
        if (null == user) {
            return null;
        }
        return new UserVO(user);

    }

    @Override
    public ResponseVO changePassword(UserForm userForm){
        try{
            //密码加密 TODO
            return ResponseVO.buildSuccess();
        }catch (Exception e){
            e.printStackTrace();
        }
        return ResponseVO.buildFailure("修改失败");
    }

    @Override
    public ResponseVO getUserInfo(int userId){
        return ResponseVO.buildSuccess(userInfoMapper.getUserInfo(userId));
    }

    @Override
    public ResponseVO changeUserInfo(UserInfoForm userInfoForm){
        return ResponseVO.buildSuccess(userInfoMapper.updateUserInfo(userInfoForm));
    }
}
