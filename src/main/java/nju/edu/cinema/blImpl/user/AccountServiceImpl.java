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
import org.springframework.web.bind.annotation.RequestBody;

import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author huwen
 * @date 2019/3/23
 */
@Service
public class AccountServiceImpl implements AccountService, AccountServiceForBl {
    private final static String ACCOUNT_EXIST = "账号已存在";
    private String verificationCode = null;
    @Autowired
    private AccountMapper accountMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    @Override
    public ResponseVO registerAccount(UserForm userForm) {
        try {
            User user = new User();
            user.setIdentity(0);
            user.setUsername(userForm.getUsername());
            user.setPassword(getMD5(userForm.getPassword()));
            accountMapper.createNewAccount(user);
            userInfoMapper.insertUserInfo(user.getId());
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

    @Override
    public ResponseVO changePassword(UserForm userForm){
        try{
            //密码加密 TODO
            User user = new User();
            user.setId(userForm.getId());
            user.setPassword(getMD5(userForm.getPassword()));
            accountMapper.updatePassword(user.getId(),user.getPassword());
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

    public ResponseVO check(HttpSession session, String verifyCode) {
        String verifySession = (String) session.getAttribute("validateCode");
        //验证的时候不区分大小写
        if (verifyCode.equalsIgnoreCase(verifySession)) {
            return ResponseVO.buildSuccess();
        }else{
            return ResponseVO.buildFailure("验证码输入错误");
        }
    }
}
