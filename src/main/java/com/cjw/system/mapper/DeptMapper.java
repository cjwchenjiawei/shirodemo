package com.cjw.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cjw.system.model.Dept;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeptMapper extends BaseMapper<Dept> {
    List<Dept> selectAllDeptTree();

    @Select("select ifnull(max(order_num),0) from dept")
    Integer selectMaxOrderNum();

    Integer swap(Integer currentId, Integer swapId);

    @Select("select dept_id, dept_name, parent_id, order_num, create_time, modify_time from dept where parent_id = #{parentId} order by order_num")
    List<Dept> selectByParentId(Integer parentId);

    @Delete("delete from dept where dept_id = #{deptId} or parent_id = #{deptId}")
    Integer deleteDept(@Param("deptId") Integer deptId);

}