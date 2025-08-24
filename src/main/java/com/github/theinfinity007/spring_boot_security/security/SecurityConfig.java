package com.github.theinfinity007.spring_boot_security.security;

import com.github.theinfinity007.spring_boot_security.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(UserService userService){
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    /*
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource){

        // automatically uses the users and authorities table by default
        // return new JdbcUserDetailsManager(dataSource);


        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        // query to retrieve a user by username
        jdbcUserDetailsManager.setUsersByUsernameQuery("select user_id, pwd, enabled from members where user_id=?");

        // query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery("select user_id, role from roles where user_id=?");

        return jdbcUserDetailsManager;
    }
    */

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
                .exceptionHandling(configurer -> configurer.accessDeniedPage("/access-denied"))
        ;


        return http.build();
    }


    /*
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

     */

}
