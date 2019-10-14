package com.deltamate.demo.service;

import com.deltamate.demo.model.User;

import java.util.List;

public interface UserService {
    void createUser(User user);
    List<User> viewAllUser();
    void editUser(User user);
    void removeUser(String userID);
    User findUserByID(String userID);
}
