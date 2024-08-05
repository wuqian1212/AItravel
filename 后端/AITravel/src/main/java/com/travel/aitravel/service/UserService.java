package com.travel.aitravel.service;

import com.travel.aitravel.dao.User;

public interface UserService {
    public void addNewUser(User user);

    public User getUserById(Long id);

    User getUserByUsername(String username);

    void updatePhone(User user);

    void updatePassword(User user);

    void delete(Long userId);
}
