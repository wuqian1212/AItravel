package com.travel.aitravel.controller;

import com.travel.aitravel.R;
import com.travel.aitravel.dao.UserInfo;
import com.travel.aitravel.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户信息控制器，负责处理与用户信息相关的HTTP请求。
 */
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {

    /**
     * 自动注入用户信息服务，用于处理用户信息的增删改查操作。
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 添加用户信息。
     * 通过@RequestBody注解，将请求体中的数据绑定到UserInfo对象上。
     *
     * @param userInfo 用户信息对象，包含要添加的用户信息。
     * @return 返回操作结果，包含添加成功的用户信息。
     */
    @PostMapping("/set")
    public R<UserInfo> addUserInfo(@RequestBody UserInfo userInfo) {
        // 打印传入的用户信息，用于调试和日志记录。
        // 打印传来的数据
        System.out.println(userInfo.getSex() + " " + userInfo.getLikes() + " " + userInfo.getAge() + " " + userInfo.getCareer());
        userInfoService.addNewUserInfo(userInfo);
        return R.success(userInfo);
    }

    /**
     * 根据ID获取用户信息。
     * 使用@RequestBody注解来接收请求参数，这里可能是Spring Boot的使用不当，通常GET请求的参数不应该使用@RequestBody注解。
     *
     * @param id 用户的ID，用于查询特定用户的详细信息。
     * @return 返回操作结果，包含查询到的用户信息。
     */
    @GetMapping("/get")
    public R<UserInfo> getUserInfo(@RequestBody int id) {
        return R.success(userInfoService.getUserInfoById(id));
    }

    /**
     * 更新用户信息。
     * 通过@RequestBody注解，将请求体中的数据绑定到UserInfo对象上，用于更新已存在的用户信息。
     *
     * @param userInfo 用户信息对象，包含要更新的用户信息。
     * @return 返回操作结果，包含更新成功的用户信息。
     */
    @PutMapping("/update")
    public R<UserInfo> updateUserInfo(@RequestBody UserInfo userInfo) {
        userInfoService.updateUserInfo(userInfo);
        return R.success(userInfo);
    }

}
