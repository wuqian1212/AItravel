package com.travel.aitravel.converter;

import com.travel.aitravel.dao.User;
import com.travel.aitravel.dto.UserDTO;

public class UserConverter {
    public static User toUser(UserDTO userDTO) {
        return new User(userDTO.getUsername(), userDTO.getPassword());
    }

    public static UserDTO toUserDTO(User user) {
        return new UserDTO(user.getUsername(), user.getPassword());
    }
}
