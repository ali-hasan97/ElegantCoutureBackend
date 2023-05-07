package com.genspark.ECommerceFullStack.Service;

import com.genspark.ECommerceFullStack.Entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            System.out.println("check");
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
                JSONObject user = users.getJSONObject(i);
                if (username.equals(user.getString("username"))) {
                    String password = user.getString("password");
                    JSONArray productIDJson = user.getJSONArray("productID");
                    List<Integer> newProductIDList = new ArrayList<>();
                    for (int j = 0; j < productIDJson.length(); j++) {
                        newProductIDList.add(productIDJson.getInt(j));
                    }
                    String role = user.getString("role");
                    List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_" + role);
                    System.out.println(username + password + authorities);
                    return new CustomUserDetails(new User(username, password, newProductIDList, authorities));
                }
            }

        } catch (IOException e) {
            System.out.println("catch");
            throw new RuntimeException("User details' loading failed");
        }
        throw new UsernameNotFoundException("User Not Found");
    }
}
