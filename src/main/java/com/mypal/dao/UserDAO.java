package com.mypal.dao;

import com.mypal.entity.User;

import java.util.List;

public interface UserDAO {

    public void save(User user);
    public User getById(Integer id);
    public User getByEmail(String email);
    public double getBalance(Integer id);
    public List<User> list();
}

