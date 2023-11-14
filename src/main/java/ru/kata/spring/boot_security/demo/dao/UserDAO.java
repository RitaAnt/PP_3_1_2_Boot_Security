package ru.kata.spring.boot_security.demo.dao;

import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserDAO {
    List<User> getAllUsers();
    List<Role> getAllRoles();
    User getUserById(Long id);

    User getUserByUsername(String username);

    void saveUser(User user);

    void deleteUser(Long id);
}
