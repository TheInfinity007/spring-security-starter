package com.github.theinfinity007.spring_boot_security.dao;

import com.github.theinfinity007.spring_boot_security.entity.User;

public interface UserDao {
    User findByUsername(String username);
}
