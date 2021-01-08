package com.cjw.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.system.model.Menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cjw.system.model.Dept;
import com.cjw.system.mapper.DeptMapper;
import com.cjw.system.service.DeptService;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DeptServiceImpl extends ServiceImpl<DeptMapper, Dept> implements DeptService{

    @Autowired
    private DeptMapper deptMapper;

    @Override
    public List<Dept> selectAllDeptTree() {
        return deptMapper.selectAllDeptTree();
    }


    /**
     * 将树形结构添加到一个根节点下.
     */
    private List<Dept> addRootNode(String rootName, Integer rootId, List<Dept> children) {
        Dept root = new Dept();
        root.setDeptId(rootId);
        root.setDeptName(rootName);
        root.setChildren(children);
        List<Dept> rootList = new ArrayList<>();
        rootList.add(root);
        return rootList;
    }

    @Override
    public List<Dept> getAllDeptTreeAndRoot() {
        List<Dept> tree = deptMapper.selectAllDeptTree();
        return  this.addRootNode("导航目录",0,tree);
    }

    @Override
    @Transactional
    public Integer add(Dept dept) {
        Integer orderNum = deptMapper.selectMaxOrderNum();
        dept.setOrderNum(orderNum+1);
        return deptMapper.insert(dept);
    }

    @Override
    @Transactional
    public Integer updateDept(Dept dept) {
        return deptMapper.updateById(dept);
    }

    @Override
    public Integer swap(Integer currentId, Integer swapId) {
        return deptMapper.swap(currentId,swapId);
    }

    @Override
    public List<Dept> selectByParentId(Integer parentId) {
        return deptMapper.selectByParentId(parentId);
    }

    @Override
    @Transactional
    public Integer delete(Integer deptId) {
        deptMapper.deleteDept(deptId);
        return null;
    }
}
