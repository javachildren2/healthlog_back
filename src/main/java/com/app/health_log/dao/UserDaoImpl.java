package com.app.health_log.dao;

import com.app.health_log.domain.User;
import org.apache.ibatis.session.SqlSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private SqlSession session;
    private static String namespace = "com.app.health_log.dao.UserDao.";
    @Override
    public int insertUser(User user) throws Exception {
        return session.insert(namespace+"insertUser",user);
    }
    @Override
    public User getUserById(String id) {
        return session.selectOne(namespace+"getUserById", id);
    }

}



