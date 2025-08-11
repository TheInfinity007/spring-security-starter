package com.github.theinfinity007.spring_boot_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@Configuration
public class DemoSecurityConfig {

    // Inmemory users
    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails john = User.builder()
                .username("john").password("{noop}test123")
                .roles("EMPLOYEE").build();

        UserDetails mary = User.builder()
                .username("mary").password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER").build();

        UserDetails susan = User.builder()
                .username("susan").password("{noop}test123")
                .roles("EMPLOYEE", "MANAGER", "ADMIN").build();

        return new InMemoryUserDetailsManager(john, mary, susan);
    }

}
