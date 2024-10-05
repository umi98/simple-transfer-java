package com.umi98.transfer_app.repository;

import com.umi98.transfer_app.constant.ERole;
import com.umi98.transfer_app.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {
    Optional<Role> findByRole(ERole role);
}
