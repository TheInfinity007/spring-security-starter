package com.github.theinfinity007.spring_boot_security.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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

    // Configure spring boot security to use the custom login form
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(configurer -> configurer
                        .requestMatchers("/").hasRole("EMPLOYEE")
                        .requestMatchers("/leaders/**").hasRole("MANAGER")
                        .requestMatchers("/systems/**").hasRole("ADMIN")
                        .requestMatchers(".well-known/**").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                                .loginPage("/login")
                                .loginProcessingUrl("/authenticate")    // No need to create controller mapping for this endpoint, spring will handle itself
                        .permitAll())
                .logout(logout -> logout.permitAll())  // Enable logout support and allow anyone (authenticated or not) to access the logout endpoint.
        ;


        return http.build();
    }

}
