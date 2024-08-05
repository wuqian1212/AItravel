package com.travel.aitravel.dao;

import jakarta.persistence.*;

@Entity
@Table(name = "user_info")
public class UserInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "sex")
    private String sex;

    @Column(name = "likes", length = 10)
    private String likes;

    @Column(name = "age")
    private int age;

    @Column(name = "career")
    private String career;

    // 构造函数
    public UserInfo() {}

    public UserInfo(int id, String sex, String likes, int age, String career) {
        this.id = id;
        this.sex = sex;
        this.likes = likes;
        this.age = age;
        this.career = career;
    }

    // Getter 和 Setter 方法

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "id=" + id +
                ", sex='" + sex + '\'' +
                ", likes='" + likes + '\'' +
                ", age=" + age +
                ", career='" + career + '\'' +
                '}';
    }
}
