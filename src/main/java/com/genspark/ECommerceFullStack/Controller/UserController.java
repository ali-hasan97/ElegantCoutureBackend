package com.genspark.ECommerceFullStack.Controller;

import com.genspark.ECommerceFullStack.Entity.User;
import com.genspark.ECommerceFullStack.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getUsers() {
        return this.userService.getAllUsers();
    }

    @GetMapping("/")
    public User getUserByID(int userId) {
        return this.userService.getUserById(userId);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }
}
