package com.app.health_log.controller;

import com.app.health_log.domain.User;
import com.app.health_log.dao.UserDao;
import com.app.health_log.service.SecurityService;
import com.app.health_log.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/api")
public class LoginController {
    private final UserService userService;

    private final SecurityService securityService;

    public LoginController(UserService userService, SecurityService securityService) {
        this.userService = userService;
        this.securityService = securityService;
    }




        @PostMapping("/login")
        public ResponseEntity<Map<String, String>> login(@RequestBody User user) {
            boolean isAuthenticated = userService.authenticateUser(user.getId(), user.getPwd());
            User userObject = userService.getUser_id(user.getId(), user.getPwd());

            if (isAuthenticated) {
                String token = securityService.createToken(userObject.getUser_id(), 3600000); // 토큰 만료 시간을 밀리초로 설정합니다.
                Map<String, String> response = new HashMap<>();
                response.put("token", token);
                response.put("user_id", userObject.getUser_id());

                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }
        }
    }

