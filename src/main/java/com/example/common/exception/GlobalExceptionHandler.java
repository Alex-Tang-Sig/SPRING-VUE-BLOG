package com.example.common.exception;

import com.example.common.lang.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Scanner;

/**
 * GlobalExceptionHandler class
 *
 * @author kejiwang
 * @date 13/5/22
 */

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public Result handler(ShiroException e) {
        log.error("Runtime exception: ------ {}", e);
        return Result.fail(401, e.getMessage(),null);
    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public Result handler(RuntimeException e) {
        log.error("Runtime exception: ------ {}", e);
        return Result.fail(e.getMessage());
    }
}
