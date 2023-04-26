package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(int UserID);
    User addUser(User user);
    User updateUser(User user);
    String deleteUser();
}
