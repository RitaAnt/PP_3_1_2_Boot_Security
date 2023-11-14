package ru.kata.spring.boot_security.demo.service;

import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService {
    User getUserById(Long id);

    List<User> getAllUsers();
    List<Role> getAllRoles();

    @Transactional
    User getUserByUsername(String username);

    @Transactional
    void saveUser(User user);

    @Transactional
    void deleteUser(Long id);

    void saveRole(Role role);
}
