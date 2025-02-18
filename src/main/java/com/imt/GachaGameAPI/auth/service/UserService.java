package com.imt.GachaGameAPI.auth.service;

import com.imt.GachaGameAPI.auth.dao.UserDAO;
import com.imt.GachaGameAPI.auth.dto.UserDTO;
import com.imt.GachaGameAPI.auth.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserDAO userDAO;

    public UserDTO createUser(String username, String password) {
        User user = new User(username, password, null);
        User savedUser = userDAO.save(user);
        return new UserDTO(savedUser.getUsername(), savedUser.getPassword(), savedUser.getToken());
    }

    public Optional<UserDTO> getUserByUsername(String username) {
        return userDAO.findByUsername(username)
                .map(user -> new UserDTO(user.getUsername(), user.getPassword(), user.getToken()));
    }

    public void deleteUser(String username) {
        userDAO.deleteByUsername(username);
    }
}
