package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 登录日志表
    */
@Data
@TableName(value = "login_log")
public class LoginLog implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 登录时间
     */
    @TableField(value = "login_time")
    private Date loginTime;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 登录状态, 0 表示登录失败, 1 表示登录成功.
     */
    @TableField(value = "login_status")
    private String loginStatus;

    /**
     * IP
     */
    @TableField(value = "ip")
    private String ip;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_LOGIN_TIME = "login_time";

    public static final String COL_USERNAME = "username";

    public static final String COL_LOGIN_STATUS = "login_status";

    public static final String COL_IP = "ip";
}