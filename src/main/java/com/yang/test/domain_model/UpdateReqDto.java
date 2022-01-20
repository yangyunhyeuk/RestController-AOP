package com.yang.test.domain_model;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UpdateReqDto {
    @NotBlank(message = "password가 입력되지 않았습니다")
    private String password;
    private String phone;
}

