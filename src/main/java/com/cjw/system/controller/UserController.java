package com.cjw.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cjw.common.groups.Create;
import com.cjw.common.util.ResultVO;
import com.cjw.system.model.User;
import com.cjw.system.service.RoleService;
import com.cjw.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author yingside
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/index")
    public String index(){
        System.out.println("=====UserController.index=====");
        return "user/user-list";
    }

    @GetMapping("/list")
    @ResponseBody
    public ResultVO getList(Integer page, Integer limit, User userQuery){
        System.out.println("====UserController.getList====");
        System.out.println("userQuery = " + userQuery);
        IPage<User> userPage = userService.selectAllWithDept(page, limit, userQuery);
        return ResultVO.success(userPage);
    }

    @GetMapping
    public String add(Model model){
        model.addAttribute("roles",roleService.list());
        return "user/user-add";
    }

    @PostMapping
    @ResponseBody
    public ResultVO add(@Validated(Create.class) User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
        System.out.println("========UserController.add-post========");
        return ResultVO.success(userService.add(user, roleIds));
    }

    @GetMapping("/{userId}")
    public String update(@PathVariable("userId") Integer userId, Model model){
        model.addAttribute("user",userService.getById(userId));
        model.addAttribute("roleIds",userService.selectRoleIdsByUserId(userId));
        model.addAttribute("roles",roleService.list());
        return "user/user-add";
    }

    /**
     * 执行修改操作
     */
    @PutMapping
    @ResponseBody
    public ResultVO update(@Valid User user, @RequestParam(value = "role[]", required = false) Integer[] roleIds) {
        System.out.println("========UserController.update========");
        System.out.println("user = " + user);
        userService.update(user, roleIds);
        return ResultVO.success();
    }

    @PostMapping("/{userId}/enable")
    @ResponseBody
    public ResultVO enable(@PathVariable("userId") Integer userId) {
        userService.enableById(userId);
        return ResultVO.success();
    }

    @PostMapping("/{userId}/disable")
    @ResponseBody
    public ResultVO disable(@PathVariable("userId") Integer userId) {
        userService.disableById(userId);
        return ResultVO.success();
    }


    @DeleteMapping("/{userId}")
    @ResponseBody
    public ResultVO delete(@PathVariable("userId") Integer userId) {
        userService.delete(userId);
        return ResultVO.success();
    }

}
