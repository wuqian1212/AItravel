package com.travel.aitravel.dao;

import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {
    //根据id查询userInfo
    @SQL("select * from user_info where id = :id")
    UserInfo getUserInfoById(@Param("id") int id);
}
