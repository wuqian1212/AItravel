package com.travel.aitravel.service;

import com.travel.aitravel.dao.UserInfo;
import com.travel.aitravel.dao.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService{
    @Autowired
    private UserInfoRepository userInfoRepository;
    @Override
    public void addNewUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public UserInfo getUserInfoById(int id) {
        return userInfoRepository.getUserInfoById(id);
    }

    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }
}
