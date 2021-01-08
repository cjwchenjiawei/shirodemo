package com.cjw.system.service.impl;

import cn.hutool.core.util.ReflectUtil;
import com.cjw.common.shiro.realm.UserRealm;
import com.cjw.system.model.Menu;
import com.cjw.system.model.Operator;
import com.cjw.system.service.MenuService;
import com.cjw.system.service.OperatorService;
import org.apache.shiro.ShiroException;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.servlet.Filter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShiroService {

    @Autowired
    private MenuService menuService;

    @Autowired
    private OperatorService operatorService;

    @Lazy
    @Autowired
    private ShiroFilterFactoryBean shiroFilterFactoryBean;

    @Autowired
    private UserRealm userRealm;

    /**
     * 从数据库加载用户拥有的菜单权限和 API 权限.
     */
    public Map<String, String> getUrlPermsMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();

        // 系统默认过滤器
        filterChainDefinitionMap.put("/favicon.ico", "anon");
        filterChainDefinitionMap.put("/css/**", "anon");
        filterChainDefinitionMap.put("/fonts/**", "anon");
        filterChainDefinitionMap.put("/images/**", "anon");
        filterChainDefinitionMap.put("/js/**", "anon");
        filterChainDefinitionMap.put("/lib/**", "anon");
        filterChainDefinitionMap.put("/active/**", "anon");
        filterChainDefinitionMap.put("/login", "anon");
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/403", "anon");
        filterChainDefinitionMap.put("/404", "anon");
        filterChainDefinitionMap.put("/500", "anon");
        filterChainDefinitionMap.put("/error", "anon");

        // 获取所有子集菜单
        List<Menu> menuList = menuService.getLeafNodeMenu();
        for (Menu menu : menuList) {
            String url = menu.getUrl();
            if (url != null) {
                String perms = "perms[" + menu.getPerms() + "]";
                //讲子集菜单放入到过滤中
                filterChainDefinitionMap.put(url, perms);
            }
        }

        // 获取所有操作
        //mybatis-plus自带list方法
        List<Operator> operatorList = operatorService.list();
        for (Operator operator : operatorList) {
            String url = operator.getUrl();
            if (url != null) {
                if (operator.getHttpMethod() != null
                        && !"".equals(operator.getHttpMethod())) {
                    url += ("==" + operator.getHttpMethod());
                }
                String perms = "perms[" + operator.getPerms() + "]";
                filterChainDefinitionMap.put(url, perms);
            }
        }

        filterChainDefinitionMap.put("/**", "authc");

        return filterChainDefinitionMap;
    }

    /**
     * 更新过滤器链
     */
    public void updateFilterChain(){
        synchronized (shiroFilterFactoryBean) {
            AbstractShiroFilter shiroFilter;
            try {
                shiroFilter = (AbstractShiroFilter) shiroFilterFactoryBean
                        .getObject();
            } catch (Exception e) {
                throw new ShiroException(
                        "get ShiroFilter from shiroFilterFactoryBean error!");
            }
            PathMatchingFilterChainResolver filterChainResolver = (PathMatchingFilterChainResolver) shiroFilter
                    .getFilterChainResolver();
            DefaultFilterChainManager manager = (DefaultFilterChainManager) filterChainResolver
                    .getFilterChainManager();
            // 清空老的权限控制
            manager.getFilterChains().clear();
            shiroFilterFactoryBean.getFilterChainDefinitionMap().clear();
            shiroFilterFactoryBean.setFilterChainDefinitionMap(getUrlPermsMap());
            userRealm.clearAllAuthCache();

            // 清除每个 Filter 中的 appliedPaths 信息
            for (Map.Entry<String, Filter> filterEntry : manager.getFilters().entrySet()) {
                if (filterEntry.getValue() instanceof PathMatchingFilter) {
                    PathMatchingFilter filter = (PathMatchingFilter) filterEntry.getValue();
                    Map<String, Object> appliedPaths = (Map<String, Object>) ReflectUtil.getFieldValue(filter, "appliedPaths");
                    synchronized (appliedPaths) {
                        appliedPaths.clear();
                    }
                }
            }

            // 重新构建生成
            Map<String, String> chains = shiroFilterFactoryBean
                    .getFilterChainDefinitionMap();
            for (Map.Entry<String, String> entry : chains.entrySet()) {
                String url = entry.getKey();
                String chainDefinition = entry.getValue().trim().replace(" ", "");
                manager.createChain(url, chainDefinition);
            }
        }
    }
}
