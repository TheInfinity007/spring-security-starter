package com.github.theinfinity007.spring_boot_security.dao;

import com.github.theinfinity007.spring_boot_security.entity.Role;

public interface RoleDao {
    public Role findByRoleName(String name);
}
