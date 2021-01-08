package com.cjw.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cjw.system.model.User;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface UserService extends IService<User>{

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return         用户对象
     */
    User selectUserByUsername(String username);

    /**
     *根据页面查询条件 分页显示用户信息 查询条件为空则查询所有
     * @param page        当前页码
     * @param limit       每页显示数量
     * @param userQuery   传入的查询条件
     * @return            分页后的用户信息
     */
    IPage<User> selectAllWithDept(Integer page,Integer limit,User userQuery);


    /**
     * 根据用户姓名查询用户所拥有的角色名
     * @param username
     * @return
     */
    Set<String> selectRoleNameByUserName(@Param("username") String username);

    /**
     * 获取用户拥有的所有菜单权限和操作权限.
     * @param username      用户名
     * @return              权限标识符号列表
     */
    Set<String> selectPermsByUserName(String username);

    /**
     * 获取用户所拥有的操作权限
     * @param username 登录用户名
     * @return         该用户拥有操作权限的Set集合
     */
    Set<String> selectOperatorPermsByUserName(String username);

    /**
     * 新增用户
     * @param user 用户对象
     * @param roleIds 用户对应的角色id
     * @return 添加成功返回新增用户id
     */
    Integer add(User user, Integer [] roleIds);

    /**
     * 查询此用户拥有的所有角色的 ID
     * @param userId 用户 ID
     * @return 拥有的角色 ID 数组
     */
    Integer[] selectRoleIdsByUserId(Integer userId);

    /**
     * 更新用户
     * @param user 用户对象
     * @param roleIds 用户对象对应的角色
     * @return
     */
    boolean update(User user, Integer[] roleIds);

    boolean enableById(Integer userId);

    boolean disableById(Integer userId);

    boolean delete(Integer userId);
}
