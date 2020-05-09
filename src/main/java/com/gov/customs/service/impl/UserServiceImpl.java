package com.gov.customs.service.impl;

import com.gov.customs.model.User;
import com.gov.customs.repository.UserRepository;
import com.gov.customs.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Resource
    private UserRepository userRepository;

    @Override
    public boolean create(User user) {
        if (userRepository.findUserByUsername(user.getUsername())==null){
            if (user.getPassword()!=null){
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean validate(User user) {
        User user1 = userRepository.findUserByUsername(user.getUsername());
        if (new BCryptPasswordEncoder().matches(user.getPassword(),user1.getPassword())){
            return true;
        }
        return false;
    }

    @Override
    public boolean resetPassword(User user, String oldPwd, String newPwd) {
        if (!new BCryptPasswordEncoder().matches(oldPwd, user.getPassword())) {
            return false;
        }
        user.setPassword(passwordEncoder.encode(newPwd));
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public boolean resetPassword(User user, String password) {
        if (password!=null&&password!=""){
            user.setPassword(passwordEncoder.encode(password));
            userRepository.saveAndFlush(user);
            return true;
        }
        return false;
    }

    @Override
    public boolean update(User user) {
        userRepository.saveAndFlush(user);
        return true;
    }

    @Override
    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public void deleteUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(User user) {
        userRepository.delete(user);
    }
    @Override
    public boolean updateUserById(String username, long ur_r_id, long u_id) {
        userRepository.updateUserById(username,ur_r_id,u_id);
        return true;
    }
}
