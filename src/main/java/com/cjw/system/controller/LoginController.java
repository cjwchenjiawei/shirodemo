package com.cjw.system.controller;

import com.cjw.common.util.ResultVO;
import com.cjw.system.model.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(Model model) {
        return "login";
    }

    //登录
    @PostMapping("/login")
    @ResponseBody
    public ResultVO login(User user) {
        System.out.println("user ==== " + user);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(), user.getPassword());
        subject.login(token);
        ResultVO success = ResultVO.success();
        System.out.println("success = " + success);
        return success;
    }

    //注销
    @GetMapping("logout")
    public String logout(){
        SecurityUtils.getSubject().logout();
        return "redirect:login";
    }
}
