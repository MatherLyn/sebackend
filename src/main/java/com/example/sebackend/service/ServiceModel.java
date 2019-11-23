package com.example.sebackend.service;

import com.alibaba.fastjson.JSONObject;
import com.example.sebackend.entity.User;

import java.util.List;
import java.util.Map;

public interface ServiceModel {
    /**
     * 获取用户总数
     * @return
     */
    int getUsersCount();

    /**
     * 获取用户列表
     * @return
     */
    List<User> getAll();

    /**
     * 根据用户名查找用户
     * @return
     */
    User getUserByName(String name);

    /**
     * 根据用户ID查找用户
     * @return
     */
    User getUserById(int id);

    /**
     * 根据用户名更改用户信息
     * @return
     */
    User alterUserByName(String name, User newUser);

    /**
     * 根据用户ID更改用户信息
     * @return
     */
    User alterUserById(int id, User newUser);

    /**
     * 判断是否有该用户ID
     * @return
     */
    boolean userExistsByID(int id);

    /**
     * 判断是否有该用户名
     * @param name
     * @return
     */
    boolean userExistsByName(String name);

    /**
     * 注册（增加）一个用户，返回注册成功或失败信息
     * @param user
     * @return
     */
    int addUser(User user);
}
