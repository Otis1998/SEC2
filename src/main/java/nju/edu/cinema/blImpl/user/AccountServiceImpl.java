package nju.edu.cinema.blImpl.user;

import nju.edu.cinema.bl.user.AccountService;
import nju.edu.cinema.data.user.AccountMapper;
import nju.edu.cinema.po.User;
import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService, AccountServiceForBl {
    private final static String ACCOUNT_EXIST = "账号已存在";
    @Autowired
    private AccountMapper accountMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            User user = new User();
            user.setIdentity(0);
            user.setUsername(userForm.getUsername());
            user.setPassword(getMD5(userForm.getPassword()));
            accountMapper.createNewAccount(user);
            user.setPassword("");
            return ResponseVO.buildSuccess(user);
        } catch (Exception e) {
            return ResponseVO.buildFailure(ACCOUNT_EXIST);
        }
    }

    @Override
    public UserVO login(UserForm userForm) {
        User user = accountMapper.getAccountByName(userForm.getUsername());
        if (null == user || !getMD5(userForm.getPassword()).equals(user.getPassword())) {
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

    /**
     * 采用MD5对用户存入数据库中的密码进行加密
     * @param password
     * @return
     */

    private String getMD5(String password){
        //盐，用于混交md5
        String slat = "&%5123***&&%%$$#@";
        String result = "";
        try {
            String dataStr = password + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte s[] = m.digest();
            for (int i = 0; i < s.length; i++) {
                result += Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("密码转换MD5失败");
        }
        return result;
    }

}
