package com.travel.aitravel.dao;

import jakarta.transaction.Transactional;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @SQL("select * from user where username = :username")
    User findByUsername(@Param("username") String username);

    //修改phone
    @Modifying
    @Query("update User set phone = :phone where id = :id")
    void updatePhone(@Param("phone") String phone, @Param("id") long id);

    //修改密码
    @Modifying
    @Query("update User set password = :password where id = :id")
    void updatePassword(@Param("password") String password, @Param("id") long id);
}
