package com.wupao.oneclickrelease.shiro;

import com.wupao.oneclickrelease.entity.ActiveUser;
import com.wupao.oneclickrelease.pojo.Menu;
import com.wupao.oneclickrelease.pojo.Role;
import com.wupao.oneclickrelease.pojo.User;
import com.wupao.oneclickrelease.service.UserService;
import com.wupao.oneclickrelease.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuxianglong
 */
@Slf4j
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        ActiveUser activeUser = (ActiveUser) SecurityUtils.getSubject().getPrincipal();

        if (activeUser.getUser().getType() == 0) {
            authorizationInfo.addStringPermission("*:*");
        } else {
            List<String> permissions = new ArrayList<>(activeUser.getPermissions());
            List<Role> roleList = activeUser.getRoles();
            //授权角色
            if (!CollectionUtils.isEmpty(roleList)) {
                for (Role role : roleList) {
                    authorizationInfo.addRole(role.getRoleName());
                }
            }
            //授权权限
            if (!CollectionUtils.isEmpty(permissions)) {
                for (String permission : permissions) {
                    if (permission != null && !"".equals(permission)) {
                        authorizationInfo.addStringPermission(permission);
                    }
                }
            }
        }
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        log.debug("doGetAuthenticationInfo认证token:{}", token);
        // 解密获得username，用于和数据库进行对比
        String mobile = JwtUtils.getUsername(token);
        log.debug("doGetAuthenticationInfo认证mobile:{}", mobile);
        if (mobile == null) {
            log.error("doGetAuthenticationInfo认证，手机号为空！");
            throw new AuthenticationException("token错误，请重新登入！");
        }

        User userBean = userService.findUserByMobile(mobile);

        if (userBean == null) {
            log.error("doGetAuthenticationInfo认证，用户信息为空！");
            throw new AccountException("账号不存在!");
        }
        if (JwtUtils.isExpire(token)) {
            log.error("doGetAuthenticationInfo认证，用户登录jwt超时！");
            throw new AuthenticationException("token过期，请重新登入！");
        }

        if (!JwtUtils.verify(token, mobile, userBean.getPassword())) {
            log.error("doGetAuthenticationInfo认证，账号或密码错误！");
            throw new CredentialsException("账号或密码错误!");
        }

        if (userBean.getStatus() == 0) {
            log.error("doGetAuthenticationInfo认证，账号被锁定！");
            throw new LockedAccountException("账号已被锁定!");
        }

        //如果验证通过，获取用户的角色
        List<Role> roles = userService.findRolesById(userBean.getId());
        //查询用户的所有菜单(包括了菜单和按钮)
        List<Menu> menus = userService.findMenuByRoles(roles);

        Set<String> urls = new HashSet<>();
        Set<String> perms = new HashSet<>();
        if (!CollectionUtils.isEmpty(menus)) {
            for (Menu menu : menus) {
                String url = menu.getUrl();
                String per = menu.getPerms();
                if (menu.getType() == 0 && !StringUtils.isBlank(url)) {
                    urls.add(menu.getUrl());
                }
                if (!StringUtils.isBlank(per)) {
                    perms.add(menu.getPerms());
                }
            }
        }
        perms.add("user:modifyAvatarAndPwd");
        // 过滤出url,和用户的权限
        ActiveUser activeUser = new ActiveUser();
        activeUser.setRoles(roles);
        activeUser.setUser(userBean);
        activeUser.setMenus(menus);
        activeUser.setUrls(urls);
        activeUser.setPermissions(perms);
        return new SimpleAuthenticationInfo(activeUser, token, getName());
    }
}
