package nju.edu.cinema.controller.user;

import nju.edu.cinema.blImpl.user.AccountServiceImpl;
import nju.edu.cinema.blImpl.user.RandomValidateCodeServiceImpl;
import nju.edu.cinema.config.InterceptorConfiguration;
import nju.edu.cinema.vo.UserForm;
import nju.edu.cinema.vo.ResponseVO;
import nju.edu.cinema.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author huwen
 * @date 2019/3/23
 */
@RestController()
public class AccountController {
    private final static String ACCOUNT_INFO_ERROR="用户名或密码错误";
    private final static String VERIFICATION_CODE_ERROR = "验证码错误";
    @Autowired
    private AccountServiceImpl accountService;
    @PostMapping("/login")
    public ResponseVO login(@RequestBody UserForm userForm, HttpSession session){
        //校验验证码
        ResponseVO responseVO = accountService.check(session,userForm.getVerifyCode());
        if(responseVO.getSuccess()){
            UserVO user = accountService.login(userForm);
            if(user==null){
                return ResponseVO.buildFailure(ACCOUNT_INFO_ERROR);
            }
            //注册session
            session.setAttribute(InterceptorConfiguration.SESSION_KEY,userForm);
            return ResponseVO.buildSuccess(user);
        }else{
            return ResponseVO.buildFailure(VERIFICATION_CODE_ERROR);
        }
    }
    /**
     * 生成验证码
     */
    @RequestMapping(value = "/getVerify")
    public void getVerify(HttpServletRequest request, HttpServletResponse response){
        response.setContentType("image/jpeg");//设置相应类型,告诉浏览器输出的内容为图片
        response.setHeader("Pragma", "No-cache");//设置响应头信息，告诉浏览器不要缓存此内容
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeServiceImpl randomValidateCode = new RandomValidateCodeServiceImpl();
        try {
            randomValidateCode.generateVerificationCode(request.getSession(), response);//输出验证码图片方法
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 校验验证码
     */
    @RequestMapping(value = "/login/checkVerify", method = RequestMethod.POST, headers = "Accept=application/json")
    public void checkVerify(@RequestBody HttpServletRequest request, String signcode) {
        HttpSession session=request.getSession();
        String usercode=request.getParameter("verification_code");  //获取用户输入的验证码
        String sessioncode=(String) session.getAttribute("validateCode");  //获取保存在session里面的验证码
        String result="";
        if( usercode != null && usercode.equals(sessioncode)){   //对比两个code是否正确
            result = "1";
        }else{
            result = "0";
        }
//        PrintWriter out = response.getWriter();
//        out.write(result.toString());   //将数据传到前台
    }
    @PostMapping("/register")
    public ResponseVO registerAccount(@RequestBody UserForm userForm){
        return accountService.registerAccount(userForm);
    }

    @PostMapping("/logout")
    public String logOut(HttpSession session){
        session.removeAttribute(InterceptorConfiguration.SESSION_KEY);
        return "index";
    }

    @PostMapping("/changePassword")
    public ResponseVO changePassword(@RequestBody UserForm userForm){
        return accountService.changePassword(userForm);
    }

    @GetMapping("/getUserInfo/{userId}")
    public ResponseVO getUserInfo(@PathVariable int userId){
        return accountService.getUserInfo(userId);
    }

}
