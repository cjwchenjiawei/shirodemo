package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;

import com.cjw.common.groups.Create;
import com.cjw.common.groups.Update;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
    * 用户表
    */
@Data
@TableName(value = "`user`")
public class User implements Serializable {
    @TableId(value = "user_id", type = IdType.AUTO)
    @NotBlank(message = "id不能为空" , groups = {Update.class})
    private Integer userId;

    /**
     * 用户名
     */
    @TableField(value = "username")
    @NotBlank(message = "用户名不能为空" , groups = {Create.class,Update.class})
    private String username;

    /**
     * 密码
     */
    @TableField(value = "`password`")
    @NotBlank(message = "密码不能为空", groups = {Create.class})
    private String password;

    /**
     * 盐
     */
    @TableField(value = "salt")
    private String salt;

    /**
     * 邮箱
     */
    @TableField(value = "email")
    private String email;

    /**
     * 账号状态: 0: 未激活, 1: 已激活. 
     */
    @TableField(value = "`status`")
    private Integer status;

    /**
     * 最后登录时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "modify_time")
    private Date modifyTime;

    /**
     * 注册激活码
     */
    @TableField(value = "active_code")
    private String activeCode;

    /**
     * 部门ID
     */
    @TableField(value = "dept_id")
    private Integer deptId;

    @TableField(exist = false)
    private String deptName;

    @TableField(value = "deleted")
    private Integer deleted;

    private static final long serialVersionUID = 1L;

    public static final String COL_USER_ID = "user_id";

    public static final String COL_USERNAME = "username";

    public static final String COL_PASSWORD = "password";

    public static final String COL_SALT = "salt";

    public static final String COL_EMAIL = "email";

    public static final String COL_STATUS = "status";

    public static final String COL_LAST_LOGIN_TIME = "last_login_time";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";

    public static final String COL_ACTIVE_CODE = "active_code";

    public static final String COL_DEPT_ID = "dept_id";

    public static final String COL_DELETED = "deleted";
}