package com.example.wendaoneversion.service;

import com.example.wendaoneversion.model.User;

import java.util.Map;

public interface UserService {

    User getUserbyId(int id);

    Map<String,String> register(String username,String password);

    void logout(String ticket);
}
