package com.cjw.system.controller;


import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.common.util.ResultVO;
import com.cjw.system.model.Role;
import com.cjw.system.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingside
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("/index")
    public String index() {
        return "role/role-list";
    }

    /**
     * 分页显示所有角色
     * @param page 当前页码
     * @param limit 每页显示数量
     * @param queryRole 查询条件
     * @return
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultVO getList(@RequestParam(value = "page",defaultValue = "1") Integer page,
                            @RequestParam(value = "limit",defaultValue = "10")Integer limit,
                            Role queryRole){
        Page<Role> rolePage = roleService.selectAllByQuery(page, limit, queryRole);
        return ResultVO.success(rolePage);
    }

    @GetMapping("/{roleId}/own/menu")
    @ResponseBody
    public ResultVO getRoleOwnMenu(@PathVariable("roleId") Integer roleId){
        return ResultVO.success(roleService.getMenusByRoleId(roleId));
    }

    /**
     * 为角色授予菜单权限
     */
    @PostMapping("/{roleId}/grant/menu")
    @ResponseBody
    public ResultVO grantMenu(@PathVariable("roleId") Integer roleId, @RequestParam(value = "menuIds[]", required = false) Integer[] menuIds) {
        roleService.grantMenu(roleId, menuIds);
        return ResultVO.success();
    }

    @GetMapping("/{roleId}/own/operator")
    @ResponseBody
    public ResultVO getRoleOwnOperator(@PathVariable("roleId") Integer roleId){
        Integer[] operatorIds = roleService.getOperatorsByRoleId(roleId);
        for (int i = 0; i < operatorIds.length; i++) {
            operatorIds[i]+=10000;
        }
        System.out.println("operatorIds = " + operatorIds);
        return ResultVO.success(operatorIds);
    }

    /**
     * 为角色授予操作权限
     */
    @PostMapping("/{roleId}/grant/operator")
    @ResponseBody
    public ResultVO grantOperator(@PathVariable("roleId") Integer roleId, @RequestParam(value = "operatorIds[]", required = false) Integer[] operatorIds) {
        roleService.grantOperator(roleId, operatorIds);
        return ResultVO.success();
    }

    @GetMapping()
    public String add(){
        return "role/role-add";
    }

    @PostMapping()
    @ResponseBody
    public ResultVO add(Role role){
        role.setCreateTime(new Date());
        return ResultVO.success(roleService.add(role));
    }

    @GetMapping("/{roleId}")
    public String update(@PathVariable("roleId") Integer roleId, ModelMap modelMap){
        modelMap.addAttribute("role",roleService.getById(roleId));
        return "role/role-add";
    }

    @PutMapping()
    @ResponseBody
    public ResultVO update(Role role){
        role.setModifyTime(new Date());
        return ResultVO.success(roleService.update(role));
    }

    @DeleteMapping ("/{roleId}")
    @ResponseBody
    public ResultVO delete(@PathVariable("roleId") Integer roleId){
        return ResultVO.success(roleService.delete(roleId));
    }
}
