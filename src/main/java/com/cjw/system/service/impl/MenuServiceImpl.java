package com.cjw.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.common.util.ShiroUtil;
import com.cjw.common.util.TreeUtil;
import com.cjw.system.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.mapper.MenuMapper;
import com.cjw.system.model.Menu;
import com.cjw.system.service.MenuService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService{

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取当前登陆用户拥有的树形菜单 (admin 账户拥有所有权限.)
     */
    @Override
    public List<Menu> selectCurrentUserMenuTree() {
        // 从shiro中取出当前登录用户
        User currentUser = ShiroUtil.getCurrentUser();

        return selectMenuTreeByUsername(currentUser.getUsername());
    }

    /**
     * 根据用户名获取该用户拥有的树形菜单
     * @param username 用户名
     * @return         封装好的树状层级关系的Menu集合
     */
    @Override
    public List<Menu> selectMenuTreeByUsername(String username){
        List<Menu> menus = null;

        if("admin".equals(username)){
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.orderByAsc("order_num");
            menus = menuMapper.selectList(queryWrapper);
        }
        else{
            menus = menuMapper.selectMenuByUserName(username);
        }

        return toTree(menus);
    }

    /**
     * 将普通的Menu集合转换为树形结构
     */
    private List<Menu> toTree(List<Menu> menuList) {
        return TreeUtil.toTree(menuList, "menuId", "parentId", "children", Menu.class);
    }

    /**
     * 查询所有的menu，并组装层级关系(数据库直接子查询获取)
     * @return 有树状层级关系的Menu集合
     */
    @Override
    public List<Menu> selectAllTree() {
        return menuMapper.selectAllTree();
    }

    @Override
    public List<Menu> getLeafNodeMenu() {
        List<Menu> menus = selectAllTree();
        return TreeUtil.getAllLeafNode(menus);
    }

    /**
     * 将树形结构添加到一个根节点下.
     */
    private List<Menu> addRootNode(String rootName, Integer rootId, List<Menu> children) {
        Menu root = new Menu();
        root.setMenuId(rootId);
        root.setMenuName(rootName);
        root.setChildren(children);
        List<Menu> rootList = new ArrayList<>();
        rootList.add(root);
        return rootList;
    }

    @Override
    public List<Menu> getAllMenuTreeAndRoot() {
        List<Menu> menus = this.selectAllTree();
        return addRootNode("导航目录",0,menus);
    }

    @Override
    public List<Menu> selectByParentId(Integer parentId) {
        return menuMapper.selectByParentId(parentId);
    }

    @Override
    @Transactional
    public Integer add(Menu menu) {
        Integer maxOrderNum = menuMapper.selectMaxOrderNum();
        menu.setOrderNum(maxOrderNum+1);
        return menuMapper.insert(menu);
    }

    @Override
    @Transactional
    public Integer updateMenu(Menu menu) {
        return menuMapper.updateById(menu);
    }

    @Override
    @Transactional
    public Integer delete(Integer menuId) {
        return menuMapper.deleteById(menuId);
    }

    @Override
    @Transactional
    public Integer swap(Integer currentId, Integer swapId) {

        return menuMapper.swap(currentId,swapId);
    }

    @Override
    public List<Menu> getAllMenuAndCountOperatorTreeAndRoot() {
        List<Menu>menus = menuMapper.selectAllMenuAndCountOperator();
        return addRootNode("导航目录",0,menus);
    }

    @Override
    @Transactional
    public Integer deleteMenu(Integer menuId) {
        return menuMapper.deleteMenu(menuId);
    }
}
