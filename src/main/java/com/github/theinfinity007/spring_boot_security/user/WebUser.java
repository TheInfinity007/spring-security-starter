package com.github.theinfinity007.spring_boot_security.user;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class WebUser {
    @NotNull(message="is required")
    @Size(min = 1, message = "is required")
    private String username;

    @NotNull(message="is required")
    @Size(min = 1, message = "is required")
    private String password;

    @NotNull(message="is required")
    @Size(min = 1, message = "is required")
    private String firstName;

    @NotNull(message="is required")
    @Size(min = 1, message = "is required")
    private String lastName;

    @NotNull(message="is required")
    @Size(min = 5, message = "is required")
    private String email;

    public WebUser(){}

    public WebUser(String username, String password, String firstName, String lastName, String email) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getUsername() {
        return username;
    }

    public void setUsername(@NotNull(message = "is required") @Size(min = 1, message = "is required") String username) {
        this.username = username;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getPassword() {
        return password;
    }

    public void setPassword(@NotNull(message = "is required") @Size(min = 1, message = "is required") String password) {
        this.password = password;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getFirstName() {
        return firstName;
    }

    public void setFirstName(@NotNull(message = "is required") @Size(min = 1, message = "is required") String firstName) {
        this.firstName = firstName;
    }

    public @NotNull(message = "is required") @Size(min = 1, message = "is required") String getLastName() {
        return lastName;
    }

    public void setLastName(@NotNull(message = "is required") @Size(min = 1, message = "is required") String lastName) {
        this.lastName = lastName;
    }

    public @NotNull(message = "is required") @Size(min = 5, message = "is required") String getEmail() {
        return email;
    }

    public void setEmail(@NotNull(message = "is required") @Size(min = 5, message = "is required") String email) {
        this.email = email;
    }
}
