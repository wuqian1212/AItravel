package com.travel.aitravel.controller;

import com.travel.aitravel.R;
import com.travel.aitravel.converter.UserConverter;
import com.travel.aitravel.dao.User;
import com.travel.aitravel.dto.UserDTO;
import com.travel.aitravel.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器，负责处理与用户相关的HTTP请求。
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户ID获取用户信息。
     *
     * @param id 用户ID
     * @return 用户信息，如果用户不存在则返回错误信息。
     */
    @GetMapping("/{id}")
    public R<User> getUserById(@PathVariable Long id){
        User userById = userService.getUserById(id);
        if (userById != null){
            return R.success(userById);
        }else {
            return R.error("用户不存在");
        }
    }

    /**
     * 用户登录验证。
     *
     * @param user 包含用户名和密码的用户对象
     * @return 登录成功返回用户信息，否则返回错误信息。
     */
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

    /**
     * 用户注册。
     *
     * @param userDTO 用户详情的DTO对象，包含用户名等信息。
     * @return 注册成功返回用户信息，否则返回错误信息。
     */
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

    /**
     * 更新用户电话号码。
     *
     * @param user 包含新电话号码的用户对象。
     * @return 更新成功返回用户信息。
     */
    //修改电话
    @PutMapping("/updatePhone")
    public R<User> updatePhone(@RequestBody User user){
        user.setPhone(user.getPhone());
        userService.updatePhone(user);
        return R.success(user);
    }

    /**
     * 更新用户密码。
     *
     * @param user 包含新密码的用户对象。
     * @return 更新成功返回用户信息。
     */
    //修改密码
    @PutMapping("/updatePassword")
    public R<User> updatePassword(@RequestBody User user){
        System.out.println(user.getId() );
        user.setPassword(user.getPassword());
        userService.updatePassword(user);
        return R.success(user);

    }

    /**
     * 验证用户密码是否正确。
     *
     * @param user 包含用户ID和密码的对象。
     * @return 密码正确返回用户信息，否则返回错误信息。
     */
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

    /**
     * 删除指定ID的用户。
     *
     * @param id 用户ID。
     * @return 删除成功返回用户信息，否则返回错误信息。
     */
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
