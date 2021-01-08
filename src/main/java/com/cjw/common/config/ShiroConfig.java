package com.cjw.common.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.cjw.common.shiro.realm.UserRealm;
import com.cjw.system.service.impl.ShiroService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.eis.MemorySessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {
    @Bean("userRealm")
    public UserRealm getUserRealm(HashedCredentialsMatcher credentialsMatcher){
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher);
        return userRealm;
    }

    @Bean
    public SessionDAO sessionDao(){
       return new MemorySessionDAO();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        // 使用md5 算法进行加密
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        // 设置散列次数： 意为加密几次
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }

    @Bean("securityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(UserRealm userRealm,DefaultWebSessionManager sessionManager){
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setSessionManager(sessionManager);
        return securityManager;
    }

    @Bean
    public DefaultWebSessionManager getDefaultWebSessionManager(){
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionIdUrlRewritingEnabled(false);
        return sessionManager;
    }

    @Autowired
    private ShiroService shiroService;

    @Bean
    public ShiroFilterFactoryBean getFilterFactoryBean(DefaultWebSecurityManager securityManager){
//        RestShiroFilterFactoryBean shiroFilterFactoryBean = new RestShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();
//        filters.put("authc", new RestFormAuthenticationFilter());
//        filters.put("perms", new RestAuthorizationFilter());
//
        Map<String, String> urlPermsMap = shiroService.getUrlPermsMap();
//
//        System.out.println("=======================测试=========================");
//        for (Map.Entry<String, String> set:
//             urlPermsMap.entrySet()) {
//            System.out.println("set.getKey() = " + set.getKey() + ", set.getValue() = " + set.getValue());
//        }
//        System.out.println("=======================测试=========================");
//
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(urlPermsMap);
//        return shiroFilterFactoryBean;

        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
//        设置登录url
        shiroFilterFactoryBean.setLoginUrl("/login");
//        没有权限的url
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        shiroFilterFactoryBean.setUnauthorizedUrl("/user/noAuth");
//        成功的url
//        shiroFilterFactoryBean.setSuccessUrl("/user/toIndex");

//        Map<String,String> filterChainDefinitionMap = new LinkedHashMap<>();
//        // 系统默认过滤器
//        filterChainDefinitionMap.put("/favicon.ico", "anon");
//        filterChainDefinitionMap.put("/css/**", "anon");
//        filterChainDefinitionMap.put("/fonts/**", "anon");
//        filterChainDefinitionMap.put("/images/**", "anon");
//        filterChainDefinitionMap.put("/js/**", "anon");
//        filterChainDefinitionMap.put("/lib/**", "anon");
//        filterChainDefinitionMap.put("/active/**", "anon");
//        filterChainDefinitionMap.put("/login", "anon");
//        filterChainDefinitionMap.put("/register", "anon");
//        filterChainDefinitionMap.put("/403", "anon");
//        filterChainDefinitionMap.put("/404", "anon");
//        filterChainDefinitionMap.put("/500", "anon");
//        filterChainDefinitionMap.put("/error", "anon");
//
//        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(urlPermsMap);

        return shiroFilterFactoryBean;
    }

    @Bean
    public ShiroDialect getShiroDialect(){
        return new ShiroDialect();
    }

    /**
     * 开启aop注解支持
     * 即在controller中使用 @RequiresPermissions("user:add")
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(DefaultWebSecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor attributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //设置安全管理器
        attributeSourceAdvisor.setSecurityManager(securityManager);
        return attributeSourceAdvisor;
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAAP = new DefaultAdvisorAutoProxyCreator();
        defaultAAP.setProxyTargetClass(true);
        return defaultAAP;
    }

    @Bean
    public SimpleMappingExceptionResolver simpleMappingExceptionResolver() {
        SimpleMappingExceptionResolver resolver = new SimpleMappingExceptionResolver();
        Properties properties = new Properties();

        /*未授权处理页,注意这里直接跳转页面,不需要在经过Controller*/
        properties.setProperty("org.apache.shiro.authz.UnauthorizedException", "noAuth");

        resolver.setExceptionMappings(properties);
        return resolver;
    }
}
