package com.cjw.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cjw.common.exception.BizRuntimeException;
import com.cjw.common.util.ResultCode;
import com.cjw.common.util.ShiroPasswordSaltUtil;
import com.cjw.common.util.TreeUtil;
import com.cjw.system.mapper.RoleMapper;
import com.cjw.system.mapper.UserRoleMapper;
import com.cjw.system.model.Menu;
import com.cjw.system.model.UserRole;
import com.cjw.system.service.MenuService;
import com.cjw.system.service.OperatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.mapper.UserMapper;
import com.cjw.system.model.User;
import com.cjw.system.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuService menuService;

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectOne(new QueryWrapper<User>().eq("username",username));
    }

    @Override
    public IPage<User> selectAllWithDept(Integer page, Integer limit, User userQuery) {
        Page<User> pages = new Page<>((page - 1),limit);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if(userQuery != null){
            queryWrapper.eq(userQuery.getUsername() != null && !userQuery.getUsername().equals(""),"username",userQuery.getUsername());
            queryWrapper.eq(userQuery.getEmail() != null && !userQuery.getEmail().equals(""),"email",userQuery.getEmail());
            queryWrapper.eq(userQuery.getStatus() != null,"status",userQuery.getStatus());
            queryWrapper.eq(userQuery.getDeptId() != null,"u.dept_id",userQuery.getDeptId());
        }
        IPage<User> userPage = userMapper.selectAllWithDept(pages, queryWrapper);
        return userPage;
    }

    @Override
    public Set<String> selectRoleNameByUserName(String username) {
        return userMapper.selectRoleNameByUserName(username);
    }

    @Override
    public Set<String> selectPermsByUserName(String username) {
        Set<String> perms = new HashSet<>();
        List<Menu> menuTree = menuService.selectMenuTreeByUsername(username);
        List<Menu> leafNodeMenuList = TreeUtil.getAllLeafNode(menuTree);
        // 循环添加所有菜单权限
        for (Menu menu : leafNodeMenuList) {
            perms.add(menu.getPerms());
        }
        // 添加该用户所有操作权限
        perms.addAll(selectOperatorPermsByUserName(username));
        return perms;
    }

    @Override
    public Set<String> selectOperatorPermsByUserName(String username) {
        return userMapper.selectOperatorPermsByUserName(username);
    }

    /**
     * 创建用户的时候检查用户名是否存在
     * @param username 新增用户的用户名
     */
    private void checkUserNameExistOnCreate(String username) {
        Integer count = userMapper.selectCount(new QueryWrapper<User>().eq("username", username));
        if(count > 0) {
            throw new BizRuntimeException(ResultCode.USER_NAME_DUPLICATED);
        }
    }

    /**
     * 给用户赋予角色，如果用户id与角色id<=0抛出异常
     * @param userId 用户id
     * @param roleIds 用户对应的角色
     */
    private void grantRole(Integer userId, Integer[] roleIds) {
        if(userId == null || userId <= 0){
            throw new BizRuntimeException(ResultCode.INTERNAL_SERVER_ERROR);
        }
        if (roleIds == null || roleIds.length == 0) {
            throw new BizRuntimeException(ResultCode.PARAM_NOT_COMPLETE);
        }
        // 清空原有的角色, 赋予新角色.
        userRoleMapper.deleteUserRoleByUserId(userId);
        userRoleMapper.insertUserRoles(userId, roleIds);
    }

    /**
     * 新增用户
     * 分为三步：
     * 1、检查用户名是否存在，如果存在直接抛出BizRuntimeException异常
     * 2、用户信息加入到用户表
     * 3、给用户赋予角色
     * @param user 用户对象
     * @param roleIds 用户对应的角色id
     * @return 新增用户id
     */
    @Override
    @Transactional
    public Integer add(User user, Integer[] roleIds) {
        // 创建用户的时候检查用户名是否已经存在
        checkUserNameExistOnCreate(user.getUsername());

        String salt = ShiroPasswordSaltUtil.createSalt();
        user.setSalt(salt);
        //用户密码加密加盐
        user.setPassword(ShiroPasswordSaltUtil.md5Salt(user.getPassword(),salt));
        // 保存用户
        this.save(user);

        // 给用户赋角色
        grantRole(user.getUserId(), roleIds);

        return user.getUserId();
    }

    @Override
    public Integer[] selectRoleIdsByUserId(Integer userId) {
        return userMapper.selectRoleIdsByUserId(userId);
    }


    @Override
    @Transactional
    public boolean update(User user, Integer[] roleIds) {
        //检查出当前用户外，是否存在同名情况
        checkUserNameExistOnUpdate(user);

        //用户授权
        grantRole(user.getUserId(), roleIds);

//        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
//        updateWrapper.eq(user.getUsername() != null,"username",user.getUsername());
//        updateWrapper.eq(user.getPassword() != null,"password",user.getPassword());
//        updateWrapper.eq(user.getSalt() != null,"salt",user.getSalt());
//        updateWrapper.eq(user.getEmail() != null,"email",user.getEmail());
//        updateWrapper.eq(user.getLastLoginTime() != null,"lastLoginTime",user.getLastLoginTime());
//        updateWrapper.eq(user.getDeptId() != null,"dept_id",user.getDeptId());


        int update = userMapper.updateById(user);
        return update == 1;
    }

    @Override
    @Transactional
    public boolean enableById(Integer userId) {
        return userMapper.enableById(userId);
    }

    @Override
    @Transactional
    public boolean disableById(Integer userId) {
        return userMapper.disableById(userId);
    }

    @Override
    @Transactional
    public boolean delete(Integer userId) {
        userMapper.deleteById(userId);
        //清空该用户的角色
        userRoleMapper.deleteUserRoleByUserId(userId);
        return false;
    }

    /**
     * 修改用户的时候检查除当前正在修改的用户，用户名是否存在
     * @param user 用户对象
     */
    private void checkUserNameExistOnUpdate(User user) {
        Integer count = userMapper.countByUserNameNotCurrentUserId(user.getUsername(), user.getUserId());
        if(count > 0) {
            throw new BizRuntimeException(ResultCode.USER_NAME_DUPLICATED);
        }
    }
}
