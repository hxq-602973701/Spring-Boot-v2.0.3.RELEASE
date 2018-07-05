package com.example.demo.service.user.impl;

import com.example.demo.dal.entity.main.user.User;
import com.example.demo.dal.entity.main.user.UserRepository;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void save(User user) {

        userRepository.save(user);

    }

    @Override
    public User findByName(String name) {
        return this.findByName(name);
    }

}