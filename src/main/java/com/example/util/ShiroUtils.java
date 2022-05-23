package com.example.util;

import com.example.shiro.AccountProfile;
import org.apache.shiro.SecurityUtils;

/**
 * ShiroUtils class
 *
 * @author kejiwang
 * @date 23/5/22
 */
public class ShiroUtils {

    public static AccountProfile getProfile() {
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }
}
