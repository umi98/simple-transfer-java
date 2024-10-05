package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.constant.ERole;
import com.umi98.transfer_app.dto.request.LoginRequest;
import com.umi98.transfer_app.dto.request.RegisterRequest;
import com.umi98.transfer_app.dto.response.AuthResponse;
import com.umi98.transfer_app.dto.response.RegisterResponse;
import com.umi98.transfer_app.entity.*;
import com.umi98.transfer_app.repository.AppUserRepository;
import com.umi98.transfer_app.security.JwtUtil;
import com.umi98.transfer_app.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final PasswordEncoder passwordEncoder;
    private final AppUserRepository appUserRepository;
    private final AdminService adminService;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final HistoryService historyService;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerAdmin(RegisterRequest request) {
        try {
            Role role = roleService.getOrSave(Role.builder()
                    .role(ERole.ROLE_ADMIN)
                    .build());
            AppUser appUser = AppUser.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            Admin admin = Admin.builder()
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .appUser(appUser)
                    .build();
            adminService.addAdmin(admin);
            History history = History.builder()
                    .appUser(appUser)
                    .description("User "+appUser.getId()+" just registered as admin")
                    .activityTime(LocalDateTime.now())
                    .build();
            historyService.addHistory(history);
            return RegisterResponse.builder()
                    .username(appUser.getUsername())
                    .role(appUser.getRole().getRole().name())
                    .fullName(admin.getFullName())
                    .phoneNumber(admin.getPhone())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public RegisterResponse registerCustomer(RegisterRequest request) {
        try {
            Role role = roleService.getOrSave(Role.builder()
                    .role(ERole.ROLE_CUSTOMER)
                    .build());
            AppUser appUser = AppUser.builder()
                    .username(request.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .role(role)
                    .build();
            appUserRepository.saveAndFlush(appUser);
            Customer customer = Customer.builder()
                    .fullName(request.getFullName())
                    .phone(request.getPhone())
                    .appUser(appUser)
                    .build();
            customerService.addCustomer(customer);
            History history = History.builder()
                    .appUser(appUser)
                    .description("User "+appUser.getId()+" just registered as customer")
                    .activityTime(LocalDateTime.now())
                    .build();
            historyService.addHistory(history);
            return RegisterResponse.builder()
                    .username(appUser.getUsername())
                    .role(appUser.getRole().getRole().name())
                    .fullName(customer.getFullName())
                    .phoneNumber(customer.getPhone())
                    .build();
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exist");
        }
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                request.getUsername().toLowerCase(), request.getPassword()
        ));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        AppUser appUser = (AppUser) authentication.getPrincipal();
        AppUser user = appUserRepository.findById(appUser.getId()).orElseThrow();
        String token = jwtUtil.generateToken(appUser);
        History history = History.builder()
                .appUser(user)
                .description("User "+appUser.getId()+" just login")
                .activityTime(LocalDateTime.now())
                .build();
        historyService.addHistory(history);
        return AuthResponse.builder()
                .token(token)
                .username(request.getUsername())
                .role(appUser.getRole().getRole().name())
                .build();
    }
}
