package com.yang.test.config;

// Exception을 낚는다.

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice // 모든 Exception을 관리할 수 있다.
public class MyExceptionHandler {

    @ExceptionHandler(value = IllegalArgumentException.class)
    public String argsException(IllegalArgumentException e){
        return "오류" + e.getMessage();
    }


}
