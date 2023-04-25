package com.genspark.ECommerceFullStack.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;
import java.util.Set;

public class User {

    private String username;
    private String password;
    private List<GrantedAuthority> role;

    public User() {

    }

    public User(String username, String password, List<GrantedAuthority> role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<GrantedAuthority> getRole() {
        return role;
    }

    public void setRole(List<GrantedAuthority> role) {
        this.role = role;
    }
}
