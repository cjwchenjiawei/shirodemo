package com.cjw.system.controller;

import com.cjw.system.model.Menu;
import com.cjw.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private MenuService menuService;

    @GetMapping(value = {"/","/index","/main"})
    public String index(Model model){
        System.out.println("======IndexController.index======");
        List<Menu> menus = menuService.selectCurrentUserMenuTree();
        System.out.println("menus = " + menus);
        model.addAttribute("menus",menus);
        return "index";
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "welcome";
    }
}
