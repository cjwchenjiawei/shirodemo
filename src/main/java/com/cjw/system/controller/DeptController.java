package com.cjw.system.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.cjw.common.util.ResultVO;
import com.cjw.system.model.Dept;
import com.cjw.system.service.DeptService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author yingside
 * @since 2020-12-30
 */
@Controller
@RequestMapping("/dept")
public class DeptController {

    @Autowired
    private DeptService deptService;

    @RequestMapping("/tree")
    @ResponseBody
    public ResultVO deptTree(){
        ResultVO success = ResultVO.success(deptService.selectAllDeptTree());
        System.out.println("success = " + success);
        return success;
    }

    @GetMapping("/index")
    public String index(){
        return "dept/dept-list";
    }

    @GetMapping("/tree/root")
    @ResponseBody
    public ResultVO treeAndRoot(){
        return ResultVO.success(deptService.getAllDeptTreeAndRoot());
    }

    @GetMapping("/list")
    @ResponseBody
    public ResultVO list(@RequestParam(required = false) Integer parentId){
        return ResultVO.success(deptService.selectByParentId(parentId));
    }

    @GetMapping()
    public String add(){
        return "dept/dept-add";
    }

    @PostMapping()
    @ResponseBody
    public ResultVO add(Dept dept){
        return ResultVO.success(deptService.add(dept));
    }

    @GetMapping("/{deptId}")
    public String update(@PathVariable("deptId") Integer deptId, Model model){
        model.addAttribute("dept",deptService.getById(deptId));
        return "dept/dept-add";
    }

    @PutMapping()
    @ResponseBody
    public ResultVO update(Dept dept){
        return ResultVO.success(deptService.updateDept(dept));
    }

    @DeleteMapping("/{deptId}")
    @ResponseBody
    public ResultVO delete(@PathVariable("deptId") Integer deptId) {
        return ResultVO.success(deptService.delete(deptId));
    }

    @PostMapping("/swap")
    @ResponseBody
    public ResultVO swap(Integer currentId, Integer swapId) {
        return ResultVO.success(deptService.swap(currentId,swapId));
    }
}
