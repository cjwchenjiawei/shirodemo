package com.cjw.system.controller;


import com.cjw.common.annotation.RefreshFilterChain;
import com.cjw.common.util.ResultVO;
import com.cjw.system.model.Menu;
import com.cjw.system.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingside
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping("/index")
    public String index(){
        return "menu/menu-list";
    }

    /*
     * 根据父级id获取该菜单下的所有子集菜单
     */
    @GetMapping("/list")
    @ResponseBody
    public ResultVO getList(@RequestParam(required = false) Integer parentId) {
        List<Menu> menuList = menuService.selectByParentId(parentId);
        return ResultVO.success(menuList);
    }

    @GetMapping("/tree")
    @ResponseBody
    public ResultVO tree(){
        return ResultVO.success(menuService.selectAllTree());
    }

    /*
     * 获取菜单树+自定义的根节点->导航目录
     */
    @GetMapping("/tree/root")
    @ResponseBody
    public ResultVO treeAndRoot(){
        return ResultVO.success(menuService.getAllMenuTreeAndRoot());
    }


    /*
     * 获取菜单树+自定义的根节点->导航目录
     */
    @GetMapping("/tree/root/operator")
    @ResponseBody
    public ResultVO menuAndCountOperatorTreeAndRoot(){
        return ResultVO.success(menuService.getAllMenuAndCountOperatorTreeAndRoot());
    }

    @GetMapping()
    public String add(){
        return "menu/menu-add";
    }

    /*
     * 新增菜单节点，并且刷新过滤器链
     */
    @RefreshFilterChain
    @PostMapping
    @ResponseBody
    public ResultVO add(Menu menu) {
        return ResultVO.success(menuService.add(menu));
    }

    @GetMapping("/{menuId}")
    public String update(@PathVariable("menuId")Integer menuId, Model model){
        model.addAttribute("menu",menuService.getById(menuId));
        return "menu/menu-add";
    }

    /*
     * 更新菜单节点，并且刷新过滤器链
     */
    @RefreshFilterChain
    @PutMapping
    @ResponseBody
    public ResultVO update(Menu menu) {
        return ResultVO.success(menuService.updateMenu(menu));
    }

    /*
     * 删除菜单节点，并且刷新过滤器链
     */
    @RefreshFilterChain
    @DeleteMapping("/{menuId}")
    @ResponseBody
    public ResultVO delete(@PathVariable("menuId") Integer menuId) {
        return ResultVO.success(menuService.deleteMenu(menuId));
    }

    @PostMapping("/swap")
    @ResponseBody
    public ResultVO swap(Integer currentId, Integer swapId) {
        return ResultVO.success(menuService.swap(currentId,swapId));
    }
}
