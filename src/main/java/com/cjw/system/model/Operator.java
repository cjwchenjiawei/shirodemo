package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

@Data
@TableName(value = "`operator`")
public class Operator implements Serializable {
    /**
     * 菜单 ID
     */
    @TableId(value = "operator_id", type = IdType.AUTO)
    private Integer operatorId;

    /**
     * 所属菜单 ID
     */
    @TableField(value = "menu_id")
    private Integer menuId;

    /**
     * 资源名称
     */
    @TableField(value = "operator_name")
    private String operatorName;

    /**
     * 资源 URL
     */
    @TableField(value = "url")
    private String url;

    /**
     * 权限标识符
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 资源需要的 HTTP 请求方法
     */
    @TableField(value = "http_method")
    private String httpMethod;

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

    private static final long serialVersionUID = 1L;

    public static final String COL_OPERATOR_ID = "operator_id";

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_OPERATOR_NAME = "operator_name";

    public static final String COL_URL = "url";

    public static final String COL_PERMS = "perms";

    public static final String COL_HTTP_METHOD = "http_method";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";
}