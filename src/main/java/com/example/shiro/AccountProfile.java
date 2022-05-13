package com.example.shiro;

import lombok.Data;

import java.io.Serializable;

/**
 * AccountProfile class
 *
 * @author kejiwang
 * @date 13/5/22
 */
@Data
public class AccountProfile implements Serializable {

    private Long id;

    private String username;

    private String avatar;

    private String email;

}
