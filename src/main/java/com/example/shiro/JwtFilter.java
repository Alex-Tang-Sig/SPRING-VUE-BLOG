package com.example.shiro;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.example.common.lang.Result;
import com.example.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JwtFilter class
 *
 * @author kejiwang
 * @date 12/5/22
 */
@Component
public class JwtFilter extends AuthenticatingFilter {



    @Autowired
    JwtUtils jwtUtils;

    private final String AUTHORIZATION = "Authorization";

    @Override
    protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(AUTHORIZATION);
        if(StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        String jwt = req.getHeader(AUTHORIZATION);
        if(StringUtils.isEmpty(jwt)) {
            return true;
        } else {
            // verify jwt
            Claims claims = jwtUtils.getClaimByToken(jwt);
            if(claims == null || jwtUtils.isExpired(claims.getExpiration())) {
                throw new ExpiredCredentialsException("expired, please login again");
            }
            // execute login
            return executeLogin(request, response);
        }
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(throwable.getMessage());
        HttpServletResponse resp = (HttpServletResponse) response;
        String json = JSONUtil.toJsonStr(result);
        try {
            resp.getWriter().println(json);
        } catch (IOException ex) {
            // to do
        }
        return false;
    }
}
