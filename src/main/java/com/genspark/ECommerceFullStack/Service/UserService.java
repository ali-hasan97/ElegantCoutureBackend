package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.Listing;
import com.genspark.ECommerceFullStack.Entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(String username);
    Map<Listing, Integer> getUserProducts(User user);
    User addUser(User user);
    User updateUser(String username, int listingId);
    String deleteUser(String username);
    String deleteUserProducts(String username);
}
