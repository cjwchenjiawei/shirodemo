package com.cjw.system.service;

import com.cjw.system.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface MenuService extends IService<Menu>{

    /**
     * 根据用户名获取该用户拥有的树形菜单
     * @return 有树状层级关系的Menu集合
     */
    List<Menu> selectCurrentUserMenuTree();

    /**
     * 查询所有的menu，并组装层级关系
     * @return 有树状层级关系的Menu集合
     */
    List<Menu> selectAllTree();

    /**
     * 根据用户名获取该用户拥有的树形菜单
     * @param username 用户名
     * @return         封装好的树状层级关系的Menu集合
     */
    List<Menu> selectMenuTreeByUsername(String username);

    /**
     * 将所有子集Menu循环出来,这个方法主要是对selectAllTree()方法的再封装
     * selectAllTree()方法是直接从数据库中读取的有父子层级关系的集合
     * 而getLeafNodeMenu()是为了将父子层级关系剥离出来，只取子集中的数据
     * 目的是为了更加有序
     * @return 子集menu的集合
     */
    List<Menu> getLeafNodeMenu();

    List<Menu> getAllMenuTreeAndRoot();

    List<Menu> selectByParentId(Integer parentId);

    Integer add(Menu menu);

    Integer updateMenu(Menu menu);

    Integer delete(Integer menuId);

    Integer swap(Integer currentId, Integer swapId);

    List<Menu> getAllMenuAndCountOperatorTreeAndRoot();

    Integer deleteMenu(Integer menuId);

}
