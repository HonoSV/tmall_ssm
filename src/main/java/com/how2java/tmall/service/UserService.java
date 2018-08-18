package com.how2java.tmall.service;

import com.how2java.tmall.pojo.User;

import java.util.List;

public interface UserService {
    void add(User user);
    void delte(int id);
    User get(int id);
    void update(User user);
    List<User> list();
}
