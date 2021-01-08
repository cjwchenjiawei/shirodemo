package com.cjw.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.system.mapper.RoleMenuMapper;
import com.cjw.system.mapper.RoleOperatorMapper;
import com.cjw.system.model.RoleMenu;
import com.cjw.system.model.RoleOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.mapper.RoleMapper;
import com.cjw.system.model.Role;
import com.cjw.system.service.RoleService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService{

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Autowired
    private RoleOperatorMapper roleOperatorMapper;

    @Override
    public Page<Role> selectAllByQuery(Integer page, Integer limit, Role roleQuery) {
        QueryWrapper<Role> queryWrapper = new QueryWrapper<>();
        if(roleQuery != null){
            queryWrapper.like(
                    roleQuery.getRoleName() != null && !roleQuery.equals(""),
                    "role_name",
                    roleQuery.getRoleName());
        }
        Page<Role> pages = new Page<>((page - 1),limit);
        Page<Role> rolePage = roleMapper.selectPage(pages,queryWrapper);
        return rolePage;
    }

    @Override
    public Integer[] getMenusByRoleId(Integer roleId) {
        return roleMenuMapper.getMenusByRoleId(roleId);
    }

    @Override
    @Transactional
    public void grantMenu(Integer roleId, Integer[] menuIds) {
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        if (menuIds!=null&&menuIds.length!=0){
            int i = roleMenuMapper.insertRoleMenu(roleId, menuIds);
        }
    }

    @Override
    public Integer[] getOperatorsByRoleId(Integer roleId) {

        return roleOperatorMapper.getOperatorsByRoleId(roleId);
    }

    @Override
    @Transactional
    public void grantOperator(Integer roleId, Integer[] operatorIds) {
        roleOperatorMapper.delete(new QueryWrapper<RoleOperator>().eq("role_id",roleId));
        if (operatorIds!=null&&operatorIds.length!=0){
            for (int i = 0; i < operatorIds.length; i++) {
                operatorIds[i]-=10000;
            }
            roleOperatorMapper.insertRoleOperators(roleId,operatorIds);
        }

    }

    @Override
    @Transactional
    public Integer add(Role role) {
        return roleMapper.insert(role);
    }

    @Override
    @Transactional
    public Integer update(Role role) {
        return roleMapper.updateById(role);
    }

    @Override
    @Transactional
    public Integer delete(Integer roleId) {
        int i = roleMapper.deleteById(roleId);
        roleMenuMapper.delete(new QueryWrapper<RoleMenu>().eq("role_id",roleId));
        roleOperatorMapper.delete(new QueryWrapper<RoleOperator>().eq("role_id", roleId));
        return i;
    }
}
