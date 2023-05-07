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

    @GetMapping("/{username}")
    public User getUserByID(@PathVariable String username) {
        return this.userService.getUserById(username);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        return this.userService.deleteUser(username);
    }

    @PutMapping("/{username}/{productID}")
    public User updateUser(@PathVariable String username, @PathVariable String productID) { return this.userService.updateUser(username, Integer.parseInt(productID)); }
}
