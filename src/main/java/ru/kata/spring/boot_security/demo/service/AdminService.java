package ru.kata.spring.boot_security.demo.service;


import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface AdminService extends UserDetailsService {
    User getUserById(long id);

    BCryptPasswordEncoder getBCryptPasswordEncoder();

    User getUserByName(String name);

    List<User> getUsers();

    Role getRoleByName(String roleName);

    void addUser(User user);

    void deleteUser(long userId);

    void updateUser(long id, User update);

    @Transactional
    void saveRole(Role role);

    @Transactional
    void saveUser(User user);

    List<Role> getListRoles(long id);
}