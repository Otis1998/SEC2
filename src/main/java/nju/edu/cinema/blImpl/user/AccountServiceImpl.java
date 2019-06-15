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

import java.awt.image.BufferedImage;
import java.security.MessageDigest;
import java.awt.*;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.servlet.*;
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

    public void generateVerificationCode(HttpSession session, HttpServletResponse response){
        // 验证码图片的宽度。
        int width = 150;

        // 验证码图片的高度。
        int height = 50;

        // 验证码字符个数
        int codeCount = 4;

        int x = 0;

        // 字体高度
        int fontHeight;

        int codeY;

        char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
                'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
                'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
        x = width / (codeCount + 1);
        fontHeight = height - 2;
        codeY = height - 4;
        BufferedImage buffImg = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffImg.createGraphics();

        // 创建一个随机数生成器类
        Random random = new Random();

        // 将图像填充为白色
        g.setColor(new Color(248,248,248));
        g.fillRect(0, 0, width, height);

        // 创建字体，字体的大小应该根据图片的高度来定。
        Font font = new Font("Fixedsys", Font.PLAIN, fontHeight);
        // 设置字体。
        g.setFont(font);
        g.setColor(Color.BLACK);
        // 随机产生160条干扰线，使图象中的认证码不易被其它程序探测到。
        for (int i = 0; i < 1; i++) {
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x2, y2, x + xl, y2 + yl);
        }


        // randomCode用于保存随机产生的验证码，以便用户登录后进行验证。
        StringBuffer randomCode = new StringBuffer();

        int red = 0, green = 0, blue = 0;

        // 随机产生codeCount数字的验证码。
        for (int i = 0; i < codeCount; i++) {
            // 得到随机产生的验证码数字。
            String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同。
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);

            // 用随机产生的颜色将验证码绘制到图像中。
            g.setColor(new Color(red, green, blue));
            g.drawString(strRand, (i + 1) * x, codeY);

            // 将产生的四个随机数组合在一起。
            randomCode.append(strRand);
            if(session.getAttribute("validateCode")!=null){
                session.removeAttribute("validateCode");
                session.setAttribute("validateCode", randomCode.toString());
            }else{
                session.setAttribute("validateCode", "请输入验证码");
            }
        }
        // 将四位数字的验证码保存到Session中。
        session.removeAttribute("validateCode");
        session.setAttribute("validateCode", randomCode.toString());
        g.dispose();
        ServletOutputStream sos;
        try {
            //将内存的图片以流的形式传给前端
            sos = response.getOutputStream();
            ImageIO.write(buffImg, "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("向前端传送图片流失败");
        }
    }

}
