package com.longld.feedback_system.Service;

import com.longld.feedback_system.DTO.request.LoginDto;
import com.longld.feedback_system.DTO.request.RegisterDto;
import com.longld.feedback_system.DTO.response.ApiResponse;
import com.longld.feedback_system.DTO.response.JwtResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<ApiResponse<Object>> register(RegisterDto registerDto);
    ResponseEntity<ApiResponse<JwtResponse>> login(LoginDto loginDto);
}
