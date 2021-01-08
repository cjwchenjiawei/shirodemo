package com.cjw.system.service.impl;

import com.cjw.common.util.TreeUtil;
import com.cjw.system.mapper.MenuMapper;
import com.cjw.system.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.mapper.OperatorMapper;
import com.cjw.system.model.Operator;
import com.cjw.system.service.OperatorService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class OperatorServiceImpl extends ServiceImpl<OperatorMapper, Operator> implements OperatorService{

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private OperatorMapper operatorMapper;

    @Override
    public List<Menu> getAllMenuAndOperatorTree() {
        List<Operator> operators = this.list();
        List<Menu> menus = menuMapper.selectList(null);

        // 获取功能权限树时, 菜单应该没有复选框
        for (Menu menu: menus) {
            menu.setCheckArr(null);
        }

        List<Menu> menuTree = TreeUtil.toTree(menus, "menuId", "parentId", "children", Menu.class);
        List<Menu> allLeafNode = TreeUtil.getAllLeafNode(menuTree);

        for (Menu menu:allLeafNode) {
            List<Menu> children = menu.getChildren();
            if (children == null) {
                children = new ArrayList<>();
            }
            for (Operator operator:operators) {
                if (menu.getMenuId().equals(operator.getMenuId())){
                    Menu temp = new Menu();
                    temp.setMenuId(operator.getOperatorId()+10000);
                    temp.setParentId(operator.getMenuId());
                    temp.setMenuName(operator.getOperatorName());
                    children.add(temp);
                }
            }
            menu.setChildren(children);
        }
        return menuTree;
    }

    @Override
    @Transactional
    public Integer add(Operator operator) {

        return operatorMapper.insert(operator);
    }

    @Override
    @Transactional
    public Integer updateOperator(Operator operator) {
        return operatorMapper.updateById(operator);
    }

    @Override
    @Transactional
    public Integer delete(Integer operatorId) {
        return operatorMapper.deleteById(operatorId);
    }
}
