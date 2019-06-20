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
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import java.awt.image.BufferedImage;
import java.io.*;
import java.security.MessageDigest;
import java.awt.*;
import java.util.Map;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
        UserInfoForm userInfoForm=userInfoMapper.getUserInfo(userId);
        try{
            userInfoForm.setProfilePicture("data:image/jpeg;base64,"+imageToBase64(userInfoForm.getProfilePicture()));
        }catch (Exception e){
        }finally {
            return ResponseVO.buildSuccess(userInfoForm);
        }
    }

    public static String imageToBase64(String path) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(path);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data);// 返回Base64编码过的字节数组字符串
    }

    @Override
    public ResponseVO changeUserInfo(UserInfoForm userInfoForm){
        if(userInfoForm.getProfilePicture()==null||userInfoForm.getProfilePicture().equals("")){
            return ResponseVO.buildSuccess(userInfoMapper.updateUserInfo(userInfoForm));
        }else {
            String path = "src/main/resources/static/images/userPic/" + userInfoForm.getId() + ".jpg";
            int comma = userInfoForm.getProfilePicture().indexOf(",");
            String base64Str = userInfoForm.getProfilePicture().substring(comma + 1);
            if (base64ToImage(base64Str, path)) {
                userInfoForm.setProfilePicture(path);
                return ResponseVO.buildSuccess(userInfoMapper.updateUserInfo(userInfoForm));
            } else {
                return ResponseVO.buildFailure("保存失败");
            }
        }
    }

    public static boolean base64ToImage(String base64, String path) {// 对字节数组字符串进行Base64解码并生成图片
        if (base64 == null){ // 图像数据为空
            return false;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            // Base64解码
            byte[] bytes = decoder.decodeBuffer(base64);
            for (int i = 0; i < bytes.length; ++i) {
                if (bytes[i] < 0) {// 调整异常数据
                    bytes[i] += 256;
                }
            }
            // 生成jpeg图片
            OutputStream out = new FileOutputStream(path);
            out.write(bytes);
            out.flush();
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
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
