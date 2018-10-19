package com.eraop.common.shiro;

import com.eraop.vadmin.entity.SysPermission;
import com.eraop.vadmin.entity.SysRole;
import com.eraop.vadmin.entity.SysUser;
import com.eraop.vadmin.service.ISysPermissionService;
import com.eraop.vadmin.service.ISysRoleService;
import com.eraop.vadmin.service.ISysUserService;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * shiro的认证最终是交给了Realm进行执行
 * 所以我们需要自己重新实现一个Realm，此Realm继承AuthorizingRealm
 *
 * @author sun
 * @date 2017-4-2
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private ISysPermissionService sysPermissionService;
    @Autowired
    private ISysUserService sysUserService;
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 登录认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("————身份认证方法————");
        // UsernamePasswordToken用于存放提交的登录信息
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        System.out.println("验证当前Subject时获取到token为：" + ReflectionToStringBuilder.toString(token, ToStringStyle.MULTI_LINE_STYLE));
        SysUser user = sysUserService.getUser(token.getUsername());
        if (user != null) {
            System.out.println("用户: " + user.getEmail());
            if (user.getStatus() == 0) {
                System.out.println("用户被禁用");
                throw new DisabledAccountException();
            }
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            return new SimpleAuthenticationInfo(user.getEmail(), user.getPswd(), getName());
        }
        return null;
    }

    /**
     * 权限认证（为当前登录的Subject授予角色和权限）
     * <p>
     * 该方法的调用时机为需授权资源被访问时，并且每次访问需授权资源都会执行该方法中的逻辑，这表明本例中并未启用AuthorizationCache，
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），
     * 超过这个时间间隔再刷新页面，该方法会被执行
     * </p>
     * <p>
     * doGetAuthorizationInfo()是权限控制，
     * 当访问到页面的时候，使用了相应的注解或者shiro标签才会执行此方法否则不会执行，
     * 所以如果只是简单的身份认证没有权限的控制的话，那么这个方法可以不进行实现，直接返回null即可
     * </p>
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String loginName = (String) super.getAvailablePrincipal(principals);
        SysUser user = sysUserService.getUser(loginName);
        System.out.println("权限认证!");
        if (user != null) {
            // 权限信息对象info，用来存放查出的用户的所有的角色及权限
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            //用户的角色集合
            List<SysRole> roleList = sysRoleService.getRoles(user.getId());
            for (SysRole role : roleList) {
                //用户的角色对应的所有权限
                System.out.println("角色: " + role.getName());
                info.addRole(role.getName());
                List<SysPermission> permissions = sysPermissionService.getPermissionsByRid(role.getId());
                info.addStringPermissions(permissions.stream().map(SysPermission::getName).collect(Collectors.toList()));
            }
            return info;
        }
        // 返回null将会导致用户访问任何被拦截的请求时都会自动跳转到unauthorizedUrl指定的地址
        return null;
    }
}