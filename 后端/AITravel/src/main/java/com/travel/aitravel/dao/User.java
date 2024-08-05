package com.travel.aitravel.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "header")
    private String header;

    @Column(name = "phone")
    private String phone;

    public User() {
    }

    public User(long id, String username, String password, String header, String phone) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.header = header;
        this.phone = phone;
    }

    public User(String username, String password,String phone) {
        this.username = username;
        this.password = password;
        this.phone = phone;
    }

//    public User(Long id, String phone){
//        this.id = id;
//        this.phone = phone;
//    }
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
