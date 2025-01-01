package com.acon.weatherapp.service;

import com.acon.weatherapp.entity.User;
import com.acon.weatherapp.repository.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    void getAllUsers() {
    }

    @Test
    void register() {
    }

    @Test
    void login() {
    }

    @Test
    void getUserInfo() {


    }
}