package com.akromo.dao;

import com.akromo.models.User;

import java.util.List;

public interface UserDao {
    public void add(User user);

    public User getUser(Long id);

    public User getUserByName(String name);

    public void updateUser(User user);

    public void remove(long id);

    public List<User> listUsers();

}
