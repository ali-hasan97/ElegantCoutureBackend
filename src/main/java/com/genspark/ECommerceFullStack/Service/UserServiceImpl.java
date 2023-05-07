package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.Listing;
import com.genspark.ECommerceFullStack.Entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private ListingService listingService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public JSONArray JsonReader() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/resources/auth.json"));
            System.out.println("file found");
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();

            JSONObject json = new JSONObject(new JSONTokener(sb.toString()));
            JSONArray users = json.getJSONArray("users");

            return users;
        } catch (Exception e) {
            System.out.println("File unable to be read");
            return new JSONArray();
        }
    }

    public void JsonWriter(JSONObject newJson) {
        try {
            FileWriter fw = new FileWriter("src/main/resources/auth.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(newJson.toString());
            bw.close();
        } catch (Exception e) {
            System.out.println("File not found");
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        JSONArray users = JsonReader();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            String username = userJson.getString("username");
            String password = userJson.getString("password");
            JSONArray productIDJson = userJson.getJSONArray("productID");
            List<Integer> newProductIDList = new ArrayList<>();
            for (int j = 0; j < productIDJson.length(); j++) {
                newProductIDList.add(productIDJson.getInt(j));
            }
            String role = userJson.getString("role");
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + role);
            User user = new User(username, password, newProductIDList, authorities);
            userList.add(user);
        }
        return userList;
    }

    @Override
    public User getUserById(String username) {
        JSONArray users = JsonReader();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("username").equals(username)) {
                JSONArray productIDJson = userJson.getJSONArray("productID");
                List<Integer> newProductIDList = new ArrayList<>();
                for (int j = 0; j < productIDJson.length(); j++) {
                    newProductIDList.add(productIDJson.getInt(j));
                }
                return new User(userJson.getString("username"), userJson.getString("password"), newProductIDList, AuthorityUtils.createAuthorityList("ROLE_" + userJson.getString("role")));
            }
        }
        return new User();
    }

    public Map<Listing, Integer> getUserProducts(User user) {
        Map<Integer, Integer> productMap = new HashMap<>();
        for (int product : user.getProductID()) {
            if (productMap.containsKey(product)) {
                productMap.put(product, productMap.get(product) + 1);
            } else {
                productMap.put(product, 1);
            }
        }

        Map<Listing, Integer> listingMap = new HashMap<>();
        for (Map.Entry<Integer, Integer> entry : productMap.entrySet()) {
            listingMap.put(this.listingService.getListingByID(entry.getKey()), entry.getValue());
        }

        return listingMap;
    }

    @Override
    public User addUser(User user) {
        JSONArray users = JsonReader();

        JSONObject userObj = new JSONObject();

        userObj.put("username", user.getUsername());
        userObj.put("password",passwordEncoder.encode(user.getPassword()));
        userObj.put("productID", new JSONArray());
        userObj.put("role", "normal");

        users.put(userObj);

        JSONObject newJson = new JSONObject();
        newJson.put("users", users);

        JsonWriter(newJson);

        return user;
    }

    @Override
    public User updateUser(String username, int productID) {
        JSONArray users = JsonReader();
        JSONArray newUsers = new JSONArray();
        User modifiedUser = new User();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("username").equals(username)) {
                JSONArray productIDJson = userJson.getJSONArray("productID");
                List<Integer> newProductIDList = new ArrayList<>();
                for (int j = 0; j < productIDJson.length(); j++) {
                    newProductIDList.add(productIDJson.getInt(j));
                }
                newProductIDList.add(productID);
                modifiedUser = new User(userJson.getString("username"), userJson.getString("password"), newProductIDList, AuthorityUtils.createAuthorityList("ROLE_" + userJson.getString("role")));

                // will this append the productID to the existing productIDJson array?
                productIDJson.put(productID);

                // will this replace the value stored in the productID with the productIDJson?
                userJson.put("productID", productIDJson);

                newUsers.put(userJson);
            } else {
                newUsers.put(userJson);
            }
        }
        JSONObject newJson = new JSONObject();
        newJson.put("users", newUsers);
        JsonWriter(newJson);
        return modifiedUser;
    }

    @Override
    public String deleteUser(String username) {
        String message = "User not found.";

        JSONArray users = JsonReader();
        JSONArray newUsers = new JSONArray();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (!userJson.getString("username").equals(username)) {
                newUsers.put(userJson);
            } else {
                message = "Successfully deleted!";
            }
        }

        JSONObject newJson = new JSONObject();
        newJson.put("users", newUsers);
        JsonWriter(newJson);

        return message;
    }

    @Override
    public String deleteUserProducts(String username) {
        String message = "Delete unsuccessful.";
        // copy from update

        JSONArray users = JsonReader();
        JSONArray newUsers = new JSONArray();
        for (int i = 0; i < users.length(); i++) {
            JSONObject userJson = users.getJSONObject(i);
            if (userJson.getString("username").equals(username)) {
                // replace the value stored in the productID with a blank JSONArray
                userJson.put("productID", new JSONArray());
                message = "Products successfully cleared!";
            }
            newUsers.put(userJson);
        }
        JSONObject newJson = new JSONObject();
        newJson.put("users", newUsers);
        JsonWriter(newJson);
        return message;
    }
}
