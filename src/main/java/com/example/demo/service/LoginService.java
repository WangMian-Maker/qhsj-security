package com.example.demo.service;

import com.example.demo.entity.user.Login;

public interface LoginService {
    public String getToken(Login login);
}
