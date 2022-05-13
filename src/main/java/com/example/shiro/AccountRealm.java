package com.example.shiro;

import cn.hutool.core.bean.BeanUtil;
import com.example.entity.User;
import com.example.service.IUserService;
import com.example.service.impl.UserServiceImpl;
import com.example.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * AccountRealm class
 *
 * @author kejiwang
 * @date 12/5/22
 */
@Component
public class AccountRealm extends AuthorizingRealm {
    
    @Autowired
    JwtUtils jwtUtils;
    
    @Autowired
    IUserService userService;

    @Override
    public boolean supports(AuthenticationToken token) {
        return  token != null && token instanceof JwtToken;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        Claims claims = jwtUtils.getClaimByToken((String) jwtToken.getPrincipal());
        Long userId = (Long) Long.valueOf(claims.getSubject());
        User user = userService.getById(userId);
        // 验证用户被锁定或者不存在，则抛出异常
        if(user == null) {
            throw new UnknownAccountException("no account");
        }
        if(user.getStatus() == -1) {
            throw new LockedAccountException("account locked");
        }
        AccountProfile profile = new AccountProfile();
        BeanUtil.copyProperties(user, profile);
        return new SimpleAuthenticationInfo(profile, jwtToken.getCredentials(), getName());
    }
}
