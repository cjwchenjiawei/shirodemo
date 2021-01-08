package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.system.model.RoleMenu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {
    @Select("select menu_id from role_menu where role_id = #{roleId}")
    Integer[] getMenusByRoleId(@Param("roleId") Integer roleId);

    int insertRoleMenu(@Param("roleId") Integer roleId, @Param("menuIds") Integer[] menuIds);
}