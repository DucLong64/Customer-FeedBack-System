package com.longld.feedback_system.Controller;

import com.longld.feedback_system.DTO.request.LoginDto;
import com.longld.feedback_system.DTO.request.RegisterDto;
import com.longld.feedback_system.DTO.response.ApiResponse;
import com.longld.feedback_system.DTO.response.JwtResponse;
import com.longld.feedback_system.Service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Object>> register(@RequestBody RegisterDto registerDto) {
        return authService.register(registerDto);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<JwtResponse>> login(@RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

}
