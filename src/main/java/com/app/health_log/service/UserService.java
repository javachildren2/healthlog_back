package com.app.health_log.service;

import com.app.health_log.domain.User;
import com.app.health_log.dao.UserDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserDao userDao;

    @Autowired
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public boolean authenticateUser(String id, String pwd) {
        User user = userDao.getUserById(id);

        if (user != null && user.getPwd().equals(pwd)) {
            return true;
        }

        return false;
    }
    public User getUser_id(String id, String pwd){
        User user = userDao.getUserById(id);

        if (user != null && user.getPwd().equals(pwd)) {
            return user;
        }

        return null;
    }
}