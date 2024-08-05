package com.travel.aitravel.service;

import com.travel.aitravel.dao.UserInfo;

public interface UserInfoService {
    public void addNewUserInfo(UserInfo userInfo);

    UserInfo getUserInfoById(int id);

    void updateUserInfo(UserInfo userInfo);
}
