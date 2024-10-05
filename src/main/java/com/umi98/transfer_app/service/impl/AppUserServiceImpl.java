package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.dto.response.RegisterResponse;
import com.umi98.transfer_app.entity.AppUser;
import com.umi98.transfer_app.repository.AppUserRepository;
import com.umi98.transfer_app.service.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {
    private final AppUserRepository appUserRepository;

    @Override
    public AppUser loadUserByUserId(String id) {
        AppUser result = appUserRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid credential"));
        return AppUser.builder()
                .id(id)
                .username(result.getUsername())
                .password(result.getPassword())
                .role(result.getRole())
                .build();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser result = appUserRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("Invalid Credential"));
        return AppUser.builder()
                .id(result.getId())
                .username(result.getUsername())
                .password(result.getPassword())
                .role(result.getRole())
                .build();
    }


    @Override
    public AppUser getById(String id) {
        return appUserRepository.findById(id).orElseThrow();
    }

    @Override
    public List<RegisterResponse> getAllUser() {
        List<AppUser> appUsers = appUserRepository.findAll();
        List<RegisterResponse> responses = new ArrayList<>();
        for (AppUser appUser : appUsers) {
            RegisterResponse user = RegisterResponse.builder()
                    .id(appUser.getId())
                    .username(appUser.getUsername())
                    .role(appUser.getRole().getRole().name())
                    .build();
            responses.add(user);
        }
        return responses;
    }
}
