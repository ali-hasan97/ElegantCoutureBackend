package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String username);
    User addUser(User user);
    User updateUser(String username, int listingId);
    String deleteUser(String username);
}
