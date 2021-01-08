package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.system.model.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMapper extends BaseMapper<UserRole> {
    /**
     * 清空用户所拥有的所有角色
     */
    @Delete("delete from user_role where user_id = #{userId}")
    int deleteUserRoleByUserId(@Param("userId") Integer userId);

    /**
     * 插入多条 用户色-角色 关联关系
     */
    int insertUserRoles(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);
}