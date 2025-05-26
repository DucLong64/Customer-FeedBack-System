package com.longld.feedback_system.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterDto {
    private String username;
    private String password;
    private String email;
}
