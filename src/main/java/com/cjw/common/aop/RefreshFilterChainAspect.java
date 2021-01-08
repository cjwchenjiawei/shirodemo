package com.cjw.common.aop;

import com.cjw.system.service.impl.ShiroService;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 更新过滤器链
 */
@Aspect
@Component
public class RefreshFilterChainAspect {
    @Autowired
    private ShiroService shiroService;

    @Pointcut("@annotation(com.cjw.common.annotation.RefreshFilterChain)")
    public void updateFilterChain() {}

    @AfterReturning("updateFilterChain()")
    public void doAfter() {
        shiroService.updateFilterChain();
    }
}
