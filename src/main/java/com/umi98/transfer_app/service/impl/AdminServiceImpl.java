package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.dto.response.ProfileResponse;
import com.umi98.transfer_app.entity.Admin;
import com.umi98.transfer_app.repository.AdminRepository;
import com.umi98.transfer_app.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository adminRepository;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Admin addAdmin(Admin admin) {
        return adminRepository.save(admin);
    }

}
