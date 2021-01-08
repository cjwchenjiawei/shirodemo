package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
    * 操作日志表
    */
@Data
@TableName(value = "sys_log")
public class SysLog implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    @TableField(value = "username")
    private String username;

    /**
     * 操作
     */
    @TableField(value = "`operation`")
    private String operation;

    /**
     * 响应时间/耗时
     */
    @TableField(value = "`time`")
    private Integer time;

    /**
     * 请求方法
     */
    @TableField(value = "`method`")
    private String method;

    /**
     * 请求参数
     */
    @TableField(value = "params")
    private String params;

    /**
     * IP
     */
    @TableField(value = "ip")
    private String ip;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    private static final long serialVersionUID = 1L;

    public static final String COL_ID = "id";

    public static final String COL_USERNAME = "username";

    public static final String COL_OPERATION = "operation";

    public static final String COL_TIME = "time";

    public static final String COL_METHOD = "method";

    public static final String COL_PARAMS = "params";

    public static final String COL_IP = "ip";

    public static final String COL_CREATE_TIME = "create_time";
}