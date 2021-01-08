package com.cjw.common.shiro.realm;

import com.cjw.system.model.User;
import com.cjw.system.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.util.Collection;
import java.util.Set;

public class UserRealm extends AuthorizingRealm {
    private static final Logger log = LoggerFactory.getLogger(UserRealm.class);

    /**
     * 因为shiro默认没有事务，没有事务的调用有事务的会关闭有事务的
     * 懒加载防止shiro关闭userService的事物
     */
    @Lazy
    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("从数据库获取权限信息");
        User user = (User) principalCollection.getPrimaryPrincipal();

        String username = user.getUsername();
        //获取该用户的所有角色
        Set<String> roles = userService.selectRoleNameByUserName(username);
        //获取该用户的所有权限
        Set<String> perms = userService.selectPermsByUserName(username);

        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(roles);
        authorizationInfo.setStringPermissions(perms);
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("从数据库获取认证信息");
        //1、判断用户名，token中的用户信息时登录的时候传进来的
        UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;

        User user = userService.selectUserByUsername(token.getUsername());

        System.out.println("user = " + user);

        //返回null，shiro底层会抛出UnknownAccountException，证明没有这个用户名
        if(user == null){
            return null;
        }

        //盐值
        ByteSource salt = ByteSource.Util.bytes(user.getSalt());
        //String saltPassword = ShiroPasswordSaltUtil.md5Salt(token.getPassword(),salt.toString());


        SimpleAuthenticationInfo authcInfo = new SimpleAuthenticationInfo(user, user.getPassword(), salt, this.getName());

        System.out.println("authcInfo = " + authcInfo);
        
        //清缓存中的授权信息，保证每次登陆 都可以重新授权。因为AuthorizingRealm会先检查缓存有没有 授权信息，再调用授权方法
        //super.clearCachedAuthorizationInfo(authcInfo.getPrincipals());
        //从Subject中获取到Session把对象保存到session中
        //SecurityUtils.getSubject().getSession().setAttribute("user",user);

        return authcInfo;
    }

    @Autowired
    private SessionDAO sessionDAO;

    public void clearAllAuthCache() {
        // 获取所有 session
        Collection<Session> sessions = sessionDAO.getActiveSessions();
        for (Session session : sessions) {
            // 获取 session 登录信息。
            Object obj = session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY);
            if (obj instanceof SimplePrincipalCollection) {
                // 强转
                SimplePrincipalCollection spc = (SimplePrincipalCollection) obj;
                User user = new User();
                BeanUtils.copyProperties(spc.getPrimaryPrincipal(), user);
                this.doClearCache(spc);
            }
        }
    }
}
