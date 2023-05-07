package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

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

    @Override
    public User addUser(User user) {
        JSONArray users = JsonReader();

        JSONObject userObj = new JSONObject();
        userObj.put("username", user.getUsername());
        userObj.put("password",passwordEncoder.encode(user.getPassword()));
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
            }
        }
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
}
