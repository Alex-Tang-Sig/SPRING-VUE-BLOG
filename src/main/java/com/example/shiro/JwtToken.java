package com.example.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * JwtToken class
 *
 * @author kejiwang
 * @date 12/5/22
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt) {
        this.token = jwt;
    }
    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
