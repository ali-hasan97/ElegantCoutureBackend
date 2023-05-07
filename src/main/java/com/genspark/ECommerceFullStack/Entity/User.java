package com.genspark.ECommerceFullStack.Entity;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class User {

    private String username;
    private String password;
    private List<Integer> productID;
    private List<GrantedAuthority> role;

    public User() {

    }

    public User(String username, String password, List<Integer> productID, List<GrantedAuthority> role) {
        this.username = username;
        this.password = password;
        this.productID = productID;
        this.role = role;
    }

    public List<Integer> getProductID() {
        return productID;
    }

    public void setProductID(List<Integer> productID) {
        this.productID = productID;
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
