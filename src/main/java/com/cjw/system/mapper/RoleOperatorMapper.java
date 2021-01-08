package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.system.model.RoleOperator;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface RoleOperatorMapper extends BaseMapper<RoleOperator> {

    @Select("select operator_id from role_operator where role_id = #{roleId}")
    Integer[] getOperatorsByRoleId(@Param("roleId") Integer roleId);

    int insertRoleOperators(@Param("roleId") Integer roleId, @Param("operatorIds") Integer[] operatorIds);
}