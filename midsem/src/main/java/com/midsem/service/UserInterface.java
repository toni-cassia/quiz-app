package com.midsem.service;

import com.midsem.model.User;

import java.util.Optional;

public interface UserInterface {
    public Boolean createUser(User user);
    public Optional<User> findByUsername(String username);
}
