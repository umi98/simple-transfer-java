package com.umi98.transfer_app.service.impl;

import com.umi98.transfer_app.entity.Role;
import com.umi98.transfer_app.repository.RoleRepository;
import com.umi98.transfer_app.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository repository;

    @Override
    public Role getOrSave(Role name) {
        Optional<Role> role = repository.findByRole(name.getRole());
        if (!role.isEmpty()) {
            return role.get();
        }
        return repository.save(name);
    }
}
