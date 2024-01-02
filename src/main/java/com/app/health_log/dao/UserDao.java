package com.app.health_log.dao;
import com.app.health_log.domain.User;

import org.apache.ibatis.annotations.Param;

import java.util.Optional;

public interface UserDao {
    int insertUser(User user) throws Exception;
    User getUserById(@Param("id") String id);
}
