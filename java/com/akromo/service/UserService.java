package com.akromo.service;

import com.akromo.models.User;

import java.util.List;

public interface UserService {
    public void add(User user);

    public User getUser(Long id);

    public void updateUser(User user);

    public void remove(long id);

    public List<User> listUsers();
}
