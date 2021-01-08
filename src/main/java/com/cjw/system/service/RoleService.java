package com.cjw.system.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.system.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
public interface RoleService extends IService<Role>{

    /**
     * 分页获取所有角色列表
     * @param page 当前页码
     * @param limit 每页显示数量
     * @param roleQuery 查询条件
     * @return 分页的角色集合
     */
    Page<Role> selectAllByQuery(Integer page, Integer limit, Role roleQuery);


    /**
     * 根据角色id查询该角色拥有的所有menu id
     * @param roleId 角色id
     * @return 该角色拥有的所有menu id 的数组
     */
    Integer[] getMenusByRoleId(Integer roleId);


    void grantMenu(Integer roleId, Integer[] menuIds);

    Integer[] getOperatorsByRoleId(Integer roleId);

    void grantOperator(Integer roleId, Integer[] operatorIds);

    Integer add(Role role);

    Integer update(Role role);

    Integer delete(Integer roleId);

}
