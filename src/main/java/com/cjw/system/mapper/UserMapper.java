package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.system.model.User;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author cjw
 */
@Repository

public interface UserMapper extends BaseMapper<User> {

    /**
     * 查询所有用户并分页
     * @param page mybatis-plus的page对象
     * @param wrapper mybatis-plus条件查询对象
     * @return 分页的page对象
     */
    IPage<User> selectAllWithDept(Page<User> page, @Param(Constants.WRAPPER) Wrapper<User> wrapper);

    /**
     * 根据用户名获取拥有的所有角色名
     * @param username 登录用户名
     * @return 该用户拥有的所有角色名
     */
    @Select("select role.role_name " +
            "   from    user," +
            "           role," +
            "           user_role" +
            "   where user.user_id = user_role.user_id " +
            "       and role.role_id = user_role.role_id " +
            "       and user.username = #{username}")
    @ResultMap("com.cjw.system.mapper.UserMapper.BaseResultMap")
    Set<String> selectRoleNameByUserName(@Param("username") String username);

    /**
     * 获取用户所拥有的操作权限
     * @param username 登录用户名
     * @return         该用户拥有操作权限的Set集合
     */
    @Select(" select operator.perms " +
            "   from    user, " +
            "           role, " +
            "           user_role, " +
            "           operator, " +
            "           role_operator " +
            "   where user.user_id = user_role.user_id " +
            "   and role.role_id = user_role.role_id " +
            "   and role.role_id = role_operator.role_id " +
            "   and operator.operator_id = role_operator.operator_id " +
            "   and user.username = #{username}")
    Set<String> selectOperatorPermsByUserName(@Param("username") String username);


    /**
     * 查询此用户拥有的所有角色的 ID
     * @param userId 用户 ID
     * @return 拥有的角色 ID 数组
     */
    @Select("select role_id from user_role where user_id = #{userId}")
    Integer[] selectRoleIdsByUserId(@Param("userId") Integer userId);

    /**
     * 除当前用户外，计数和传入的username同名的个数
     * 大于0表示有同名的情况
     * @param username 用户名
     * @param userId 当前用户id
     * @return
     */
    @Select("select count(*) from user where username = #{username} and user_id != #{userId}")
    Integer countByUserNameNotCurrentUserId(@Param("username") String username, @Param("userId") Integer userId);

    @Update("update user set status = 1 where user_id = #{userId}")
    boolean enableById(Integer userId);

    @Update("update user set status = 0 where user_id = #{userId}")
    boolean disableById(Integer userId);
}