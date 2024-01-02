package com.app.health_log.controller;

import com.app.health_log.domain.User;
import com.app.health_log.dao.UserDao;
import com.app.health_log.domain.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserDao userDao;

    @Autowired
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }
    @PostMapping("/insert")
    public UserDto insertUser(@RequestBody User user) throws Exception {
        int result = userDao.insertUser(user);

        return UserDto.builder()
                .id(user.getId())
                .pwd(user.getPwd())
                .user_id(user.getUser_id())
                .build();


    }



//    @PostMapping("/insert")
//    public String insertUser(@RequestBody User user) {
//        try {
//            int result = userDao.insertUser(user);
//            if (result > 0) {
//                return "User inserted successfully";
//            } else {
//                return "Failed to insert user";
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return "Error occurred while inserting user";
//        }
//    }

}