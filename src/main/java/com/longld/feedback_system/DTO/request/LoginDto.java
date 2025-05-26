package com.longld.feedback_system.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LoginDto {
    private String username;
    private String password;

}
