package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.UserDAO;
import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class UserService {
    private final UserDAO userRepository;

    public UserService(UserDAO userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(String username, String password) {
        User newUser = new User(username, password);
        newUser.generateToken();

        userRepository.save(newUser);
        return newUser;
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
