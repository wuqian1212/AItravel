package com.travel.aitravel.service;

import com.travel.aitravel.dao.User;
import com.travel.aitravel.dao.UserRepository;
import jakarta.persistence.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Override
    public void addNewUser(User user) {
        userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("id:" + id +" not found"));
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void updatePhone(User user) {
        userRepository.updatePhone(user.getPhone(), user.getId());
    }
//
    @Override
    public void updatePassword(User user) {
        userRepository.updatePassword(user.getPassword(), user.getId());
    }

    @Override
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
