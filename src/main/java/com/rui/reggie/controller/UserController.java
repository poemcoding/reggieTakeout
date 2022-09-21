package com.rui.reggie.controller;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.rui.reggie.common.R;
import com.rui.reggie.entity.User;
import com.rui.reggie.service.UserService;
import com.rui.reggie.utils.SMSUtils;
import com.rui.reggie.utils.ValidateCodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 发送手机短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String> sendMsg(@RequestBody User user, HttpSession session){
        //获取手机号
        String phone = user.getPhone();
        //手机号不为空
        if (!StringUtils.isEmpty(phone)){
            //生成随机4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("code={}",code);
            //调用腾讯云的短信服务API发送短信(需要修改SMSUtils文件)
            SMSUtils.sendMessage("瑞吉外卖","",phone,code);
            //需要将生成的验证码保存到session中
            session.setAttribute(phone,code);

            return R.success("手机验证码发送成功");
        }

        return R.error("手机验证码发送失败");
    }

    /**
     * 移动端用户登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> Login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());

        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //从session中保存的验证码
        Object codeInSession = session.getAttribute(phone);
        //进行验证码比对 页面提交的 和 session中保存的
        if (codeInSession!=null && codeInSession.equals(code)){
            //如果能够比对成功 登陆成功
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);

            User user = userService.getOne(queryWrapper);
            if (user == null){
                //判断当前手机号对应的用户是否为新用户，如果是新用户 自动完成注册
                user = new User();
                user.setPhone(phone);
                user.setStatus(1);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            return R.success(user);
        }

        return R.error("登陆失败");
    }
}
