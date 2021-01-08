package com.cjw.system.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
    * 角色-操作关系表
    */
@Data
@TableName(value = "role_operator")
public class RoleOperator implements Serializable {
    @TableField(value = "role_id")
    private Integer roleId;

    @TableField(value = "operator_id")
    private Integer operatorId;

    private static final long serialVersionUID = 1L;

    public static final String COL_ROLE_ID = "role_id";

    public static final String COL_OPERATOR_ID = "operator_id";
}