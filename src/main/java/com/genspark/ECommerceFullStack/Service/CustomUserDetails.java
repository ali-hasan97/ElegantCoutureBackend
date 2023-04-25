package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// Step2.2 retrieve credentials from database
// Overriding UserDetails interface because we don't have authorities
public class CustomUserDetails implements UserDetails {

    // User from my entity
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CustomUserDetails(User user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRole();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }


    // MAKE SURE THESE ARE ALWAYS TRUE. NO IDEA WHY THEY HAVE THEM DEFAULT TO FALSE IN THE FIRST PLACE
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
