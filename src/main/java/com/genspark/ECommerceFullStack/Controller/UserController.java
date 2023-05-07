package com.genspark.ECommerceFullStack.Controller;

import com.genspark.ECommerceFullStack.Entity.Listing;
import com.genspark.ECommerceFullStack.Entity.User;
import com.genspark.ECommerceFullStack.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
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

    @GetMapping("/{username}/checkout")
    public Map<Listing, Integer> getUserProducts(@PathVariable String username) {
        User user = this.userService.getUserById(username);
        return this.userService.getUserProducts(user);
    }

    @PostMapping
    public User addUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }

    @DeleteMapping("/{username}")
    public String deleteUser(@PathVariable String username) {
        return this.userService.deleteUser(username);
    }

    @DeleteMapping("/{username}/checkout")
    public String deleteUserProducts(@PathVariable String username) {
        return this.userService.deleteUserProducts(username);
    }

    @PutMapping("/{username}/{productID}")
    public User updateUser(@PathVariable String username, @PathVariable String productID) { return this.userService.updateUser(username, Integer.parseInt(productID)); }
}
