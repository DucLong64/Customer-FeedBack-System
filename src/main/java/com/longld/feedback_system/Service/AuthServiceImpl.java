package com.longld.feedback_system.Service;

import com.longld.feedback_system.DTO.request.LoginDto;
import com.longld.feedback_system.DTO.request.RegisterDto;
import com.longld.feedback_system.DTO.response.ApiResponse;
import com.longld.feedback_system.DTO.response.JwtResponse;
import com.longld.feedback_system.Entity.Role;
import com.longld.feedback_system.Entity.User;
import com.longld.feedback_system.Repository.RoleRepository;
import com.longld.feedback_system.Repository.UserRepository;
import com.longld.feedback_system.Util.JwtUtil;
import com.longld.feedback_system.Util.RoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // Register
    @Override
    public ResponseEntity<ApiResponse<Object>> register(RegisterDto registerDto) {
        if(userRepository.findByUsername(registerDto.getUsername()).isPresent()){
            ApiResponse<Object> response = new ApiResponse<>(false,"Username is already in use", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        Role userRole = roleRepository.findByName(RoleEnum.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Set.of(userRole));
        user.setCreated_at(Timestamp.valueOf(LocalDateTime.now()));
        userRepository.save(user);
        ApiResponse<Object> response = new ApiResponse<>(true, "User registered successfully", null);
        return ResponseEntity.ok(response);
    }
    // Login
    @Override
    public ResponseEntity<ApiResponse<JwtResponse>> login(LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
//            // Lưu Authentication vào SecurityContextHolder
//            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String jwt = jwtUtil.generateToken(userDetails);
            ApiResponse<JwtResponse> response = new ApiResponse<>(true, "Login successful", new JwtResponse(jwt));
            return ResponseEntity.ok(response);
        }catch (BadCredentialsException e){
            ApiResponse<JwtResponse> response = new ApiResponse<>(false, "Invalid username or password", null);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }
}
