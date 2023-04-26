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
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void JsonReader(User user) {
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
            JSONObject userObj = new JSONObject();

            userObj.put("username", user.getUsername());
            userObj.put("password",passwordEncoder.encode(user.getPassword()));
            userObj.put("role", "normal");

            users.put(userObj);

            JSONObject newJson = new JSONObject();
            newJson.put("users", users);

            JsonWriter(newJson);
        } catch (Exception e) {
            System.out.println("File unable to be read");
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

            for (int i = 0; i < users.length(); i++) {
                JSONObject userJson = users.getJSONObject(i);
                String username = userJson.getString("username");
                String password = userJson.getString("password");
                String role = userJson.getString("role");
                List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + role);
                User user = new User(username, password, authorities);
                userList.add(user);
            }
        } catch (Exception e) {
            System.out.println("Could not retrieve users array from file.");
        }
        return userList;
    }

    @Override
    public User getUserById(int UserID) {
        return new User();
    }

    @Override
    public User addUser(User user) {
        JsonReader(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        return null;
    }

    @Override
    public String deleteUser() {
        return null;
    }
}
