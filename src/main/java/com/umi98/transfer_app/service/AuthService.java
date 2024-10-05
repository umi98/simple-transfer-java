package com.umi98.transfer_app.service;

import com.umi98.transfer_app.dto.request.LoginRequest;
import com.umi98.transfer_app.dto.request.RegisterRequest;
import com.umi98.transfer_app.dto.response.AuthResponse;
import com.umi98.transfer_app.dto.response.RegisterResponse;

public interface AuthService {
    RegisterResponse registerAdmin(RegisterRequest request);
    RegisterResponse registerCustomer(RegisterRequest request);
    AuthResponse login(LoginRequest request);
}
