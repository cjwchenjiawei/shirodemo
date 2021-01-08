package com.cjw.system.service;

import com.cjw.system.model.Dept;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface DeptService extends IService<Dept>{
    List<Dept> selectAllDeptTree();

    List<Dept> getAllDeptTreeAndRoot();

    Integer add(Dept dept);

    Integer updateDept(Dept dept);

    Integer swap(Integer currentId, Integer swapId);

    List<Dept> selectByParentId(Integer parentId);

    Integer delete(Integer deptId);
}
