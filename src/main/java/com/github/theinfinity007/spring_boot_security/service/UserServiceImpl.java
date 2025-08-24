package com.github.theinfinity007.spring_boot_security.service;

import com.github.theinfinity007.spring_boot_security.dao.RoleDao;
import com.github.theinfinity007.spring_boot_security.dao.UserDao;
import com.github.theinfinity007.spring_boot_security.entity.Role;
import com.github.theinfinity007.spring_boot_security.entity.User;
import com.github.theinfinity007.spring_boot_security.user.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    UserDao userDao;
    RoleDao roleDao;
    PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDao userDao, RoleDao roleDao, BCryptPasswordEncoder passwordEncoder) {
        this.userDao = userDao;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDao.findByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("Invalid username or password");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles
                .stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }

    @Override
    public User findByUserName(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public void save(WebUser webUser) {
        User user = new User();

        user.setUsername(webUser.getUsername());
        user.setPassword(passwordEncoder.encode(webUser.getPassword()));

        user.setEmail(webUser.getEmail());
        user.setFirstName(webUser.getFirstName());
        user.setLastName(webUser.getLastName());

        user.setEnabled(true);

        // add the default role of employee
        user.setRoles(Arrays.asList(roleDao.findByRoleName("ROLE_EMPLOYEE")));

        // save in db
        userDao.save(user);
    }
}
