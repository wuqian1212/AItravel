package com.travel.aitravel.controller;

import com.travel.aitravel.R;
import com.travel.aitravel.converter.UserConverter;
import com.travel.aitravel.dao.User;
import com.travel.aitravel.dto.UserDTO;
import com.travel.aitravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/{id}")
    public R<User> getUserById(@PathVariable Long id){
        User userById = userService.getUserById(id);
        if (userById != null){
            return R.success(userById);
        }else {
            return R.error("用户不存在");
        }
    }

    //账号登陆判断
    @PostMapping("/login")
    public R<User> login(@RequestBody User user){
        User userById = userService.getUserByUsername(user.getUsername());
        if (userById != null){
            if (userById.getPassword().equals(user.getPassword())){
                return R.success(userById);
            }else {
                return R.error("密码错误");
            }
        }else {
            return R.error("用户不存在");
        }
    }

    @PostMapping("/register")
    public R<User> register(@RequestBody UserDTO userDTO){
        User userByUsername = userService.getUserByUsername(userDTO.getUsername());
        if (userByUsername != null){
            return R.error("用户已存在");
        }else {
            User user = UserConverter.toUser(userDTO);
            userService.addNewUser(user);
            return R.success(user);
        }
    }

    //修改电话
    @PutMapping("/updatePhone")
    public R<User> updatePhone(@RequestBody User user){
            user.setPhone(user.getPhone());
            userService.updatePhone(user);
            return R.success(user);
    }
    //修改密码
    @PutMapping("/updatePassword")
    public R<User> updatePassword(@RequestBody User user){
        System.out.println(user.getId() );
        user.setPassword(user.getPassword());
        userService.updatePassword(user);
        return R.success(user);

    }

    //校验密码
    @PostMapping("/checkPassword")
    public R<User> checkPassword(@RequestBody User user){
        System.out.println(user.getId());
        User userById = userService.getUserById(user.getId());
        if (userById != null){
            if (userById.getPassword().equals(user.getPassword())){
                return R.success(userById);
            }else {
                return R.error("密码错误");
            }
        }else {
            return R.error("用户不存在");
        }
    }

    //注销账号
    @DeleteMapping("/delete/{id}")
    public R<User> delete(@PathVariable Long id){
        User user = userService.getUserById(id);
        if (user != null){
            userService.delete(id);
            return R.success(user);
        }else {
            return R.error("用户不存在");
        }
    }
}
