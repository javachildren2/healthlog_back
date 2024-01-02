package com.app.health_log.service;

import com.app.health_log.domain.User;
import com.app.health_log.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public User authenticateAndGetUser(String id, String pwd) {
        User user = userDao.getUserById(id);

        if (user != null && user.getPwd().equals(pwd)) {
            return user;
        }

        return null;
    }
}
