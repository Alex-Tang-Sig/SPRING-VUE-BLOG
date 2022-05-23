package com.example.common.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * LoginDTO class
 *
 * @author kejiwang
 * @date 23/5/22
 */
@Data
public class LoginDto implements Serializable {

    @NotBlank(message = "Username shouldn't be blank")
    private String username;

    @NotBlank(message = "Password shouldn't be blank")
    private String password;

}
