package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@TableName(value = "menu")
public class Menu implements Serializable {
    /**
     * 菜单 ID
     */
    @TableId(value = "menu_id", type = IdType.AUTO)
    private Integer menuId;

    @TableField(value = "parent_id")
    private Integer parentId;

    /**
     * 菜单名称
     */
    @TableField(value = "menu_name")
    private String menuName;

    /**
     * 菜单 URL
     */
    @TableField(value = "url")
    private String url;

    /**
     * 权限标识符
     */
    @TableField(value = "perms")
    private String perms;

    /**
     * 排序
     */
    @TableField(value = "order_num")
    private Integer orderNum;

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
     * 图标
     */
    @TableField(value = "icon")
    private String icon;

    /**
     * 树状菜单复选框需要的属性
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @TableField(exist = false)
    private String checkArr = "0";

    @TableField(exist=false)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<Menu> children;

    private static final long serialVersionUID = 1L;

    public static final String COL_MENU_ID = "menu_id";

    public static final String COL_PARENT_ID = "parent_id";

    public static final String COL_MENU_NAME = "menu_name";

    public static final String COL_URL = "url";

    public static final String COL_PERMS = "perms";

    public static final String COL_ORDER_NUM = "order_num";

    public static final String COL_CREATE_TIME = "create_time";

    public static final String COL_MODIFY_TIME = "modify_time";

    public static final String COL_ICON = "icon";
}