package com.cjw.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.common.annotation.RefreshFilterChain;
import com.cjw.common.util.ResultVO;
import com.cjw.system.model.Menu;
import com.cjw.system.model.Operator;
import com.cjw.system.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;


@Controller
@RequestMapping("/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;


    /**
     * 跳转 功能权限 列表页
     */
    @GetMapping("/index")
    public String index() {
        return "operator/operator-list";
    }


    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(required = false) Integer menuId) {
        return ResultVO.success(operatorService.list(new QueryWrapper<Operator>().eq("menu_id",menuId)));
    }


    @GetMapping("/tree")
    @ResponseBody
    public ResultVO tree(){
        ResultVO success = ResultVO.success(operatorService.getAllMenuAndOperatorTree());
        System.out.println("success = " + success);
        return success;
    }

    /**
     * 跳转 添加功能
     */
    @GetMapping()
    public String add() {
        return "operator/operator-add";
    }

    @PostMapping()
    @ResponseBody
    @RefreshFilterChain
    public ResultVO add(Operator operator) {
        return ResultVO.success(operatorService.add(operator));
    }

    @GetMapping("/{operatorId}")
    public String update(@PathVariable("operatorId")Integer operatorId, Model model){
        model.addAttribute("operator",operatorService.getById(operatorId));
        return "operator/operator-add";
    }

    /*
     * 更新菜单节点，并且刷新过滤器链
     */
    @RefreshFilterChain
    @PutMapping
    @ResponseBody
    public ResultVO update(Operator operator) {
        return ResultVO.success(operatorService.updateOperator(operator));
    }

    /*
     * 删除菜单节点，并且刷新过滤器链
     */
    @RefreshFilterChain
    @DeleteMapping("/{operatorId}")
    @ResponseBody
    public ResultVO delete(@PathVariable("operatorId") Integer operatorId) {
        return ResultVO.success(operatorService.delete(operatorId));
    }
}
