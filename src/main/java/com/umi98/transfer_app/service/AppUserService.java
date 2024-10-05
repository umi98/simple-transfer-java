package com.umi98.transfer_app.service;

import com.umi98.transfer_app.dto.response.RegisterResponse;
import com.umi98.transfer_app.entity.AppUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public interface AppUserService extends UserDetailsService {
    AppUser loadUserByUserId(String id);
    AppUser getById(String id);
    List<RegisterResponse> getAllUser();
}
