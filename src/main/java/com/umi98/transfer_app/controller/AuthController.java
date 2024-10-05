package com.umi98.transfer_app.controller;

import com.umi98.transfer_app.dto.request.LoginRequest;
import com.umi98.transfer_app.dto.request.RegisterRequest;
import com.umi98.transfer_app.dto.response.AuthResponse;
import com.umi98.transfer_app.dto.response.RegisterResponse;
import com.umi98.transfer_app.security.AuthTokenFilter;
import com.umi98.transfer_app.service.AppUserService;
import com.umi98.transfer_app.service.AuthService;
import com.umi98.transfer_app.utils.ResponseBuilderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;
    private final ResponseBuilderUtil responseBuilderUtil;
    private final AppUserService appUserService;
    private final AuthTokenFilter filter;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/admin/register")
    public ResponseEntity<?> registerAdmin(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.registerAdmin(request);
        return responseBuilderUtil.buildResponse(response, "Register Success", HttpStatus.OK);
    }

    @PostMapping("/customer/register")
    public ResponseEntity<?> registerCustomer(@RequestBody RegisterRequest request) {
        RegisterResponse response = authService.registerCustomer(request);
        return responseBuilderUtil.buildResponse(response, "Register Success", HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<?> getAllUsers() {
        List<RegisterResponse> responses = appUserService.getAllUser();
        return responseBuilderUtil.buildResponse(responses, "Data fetched", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return responseBuilderUtil.buildResponse(response, "Register Success", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        String jwtToken = token.substring(7);
        filter.blacklistToken(jwtToken);
        return ResponseEntity.ok("Logged out");
    }
}
