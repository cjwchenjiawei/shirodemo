package com.cjw.system.service;

import com.cjw.system.model.Menu;
import com.cjw.system.model.Operator;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface OperatorService extends IService<Operator>{

    List<Menu> getAllMenuAndOperatorTree();

    Integer add(Operator operator);

    Integer updateOperator(Operator operator);

    Integer delete(Integer operatorId);
}
