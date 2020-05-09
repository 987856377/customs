package com.gov.customs.service;

import com.gov.customs.model.User;

import java.util.List;

public interface UserService {
    boolean create(User user);
    boolean validate(User user);
    boolean resetPassword(User user,String oldPwd, String newPwd);
    boolean resetPassword(User user, String password);
    boolean update(User user);
    User findUserById(Long id);
    User findUserByUsername(String username);
    void deleteUser(User user);
    void deleteUserById(long id);
    List<User> findAll();

    boolean updateUserById(String username, long ur_r_id, long u_id);
}
