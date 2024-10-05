package com.umi98.transfer_app.service;

import com.umi98.transfer_app.entity.Role;

public interface RoleService {
    Role getOrSave(Role name);
}
