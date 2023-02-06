package com.spring.security.web.dto;

import lombok.Data;

@Data
public class PasswordDTO {

    private String email;
    private String oldPassword;
    private String newPassword;
    private String token;

}
