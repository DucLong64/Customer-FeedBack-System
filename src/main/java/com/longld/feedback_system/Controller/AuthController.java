package com.longld.feedback_system.Controller;

import com.longld.feedback_system.DTO.request.LoginDto;
import com.longld.feedback_system.DTO.request.RegisterDto;
import com.longld.feedback_system.DTO.response.ApiResponse;
import com.longld.feedback_system.DTO.response.JwtResponse;
import com.longld.feedback_system.Service.AuthService;
import com.longld.feedback_system.Util.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final JwtUtil jwtUtil;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("registerDto", new RegisterDto());
        return "register";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute RegisterDto registerDto, Model model) {
        try {
            ResponseEntity<ApiResponse<Object>> responseEntity = authService.register(registerDto);
            ApiResponse<Object> body = responseEntity.getBody();
            if (body != null && body.isSuccess()) {
                model.addAttribute("message", "Registration successful! You can now login.");
                model.addAttribute("loginDto", new LoginDto()); // Chuẩn bị form đăng nhập
                return "login"; // Chuyển đến trang login
            } else {
                model.addAttribute("message", body != null ? body.getMessage() : "Registration failed");
                model.addAttribute("registerDto", registerDto);
                return "register";
            }
        } catch (Exception e) {
            model.addAttribute("message", "An error occurred during registration: " + e.getMessage());
            model.addAttribute("registerDto", registerDto);
            return "register";
        }
    }
    @GetMapping("/login")
    public String showLoginForm(Model model, @RequestParam(value = "logout", required = false) String logout) {
        model.addAttribute("loginDto", new LoginDto());
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute LoginDto loginDto, HttpServletResponse response, Model model) {
        ResponseEntity<ApiResponse<JwtResponse>> responseEntity = authService.login(loginDto);
        ApiResponse<JwtResponse> body = responseEntity.getBody();

        if (body != null && body.isSuccess()) {
            String jwt = body.getData().getToken();
            // Luu token vao cookie
            Cookie cookie = new Cookie("JWT_TOKEN", jwt);
            cookie.setPath("/");
            cookie.setHttpOnly(true);
            cookie.setMaxAge(36000); // 10 gio
            response.addCookie(cookie);
            //Kiem tra vai tro nguoi dung
            List<String> roles = jwtUtil.getRoles(jwt);
            boolean isAdmin = roles.contains("ROLE_ADMIN");
            // Chuyển hướng theo vai trò
            if (isAdmin) {
                return "redirect:/admin/dashboard";
            } else {
                return "redirect:/home";
            }
        } else {
            model.addAttribute("message", body != null ? body.getMessage() : "Login failed");
            model.addAttribute("loginDto", loginDto);
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        // Xoa cookie JWT
        Cookie cookie = new Cookie("JWT_TOKEN", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/auth/login?logout";
    }

}
