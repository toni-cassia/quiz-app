package com.midsem.service;

import com.midsem.model.User;
import com.midsem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class UserService implements UserInterface {
    @Autowired
    UserRepository repo;

    @Override
    public Boolean createUser(User user) {
        try {
            repo.save(user);
            return true;


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }

    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            Optional<User> userFound = repo.findByUsername(username);

            return userFound;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}