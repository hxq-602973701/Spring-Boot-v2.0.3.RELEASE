package com.example.demo.service.user;

import com.example.demo.dal.entity.main.user.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    void save(User user);

    User findByName(String name);
}