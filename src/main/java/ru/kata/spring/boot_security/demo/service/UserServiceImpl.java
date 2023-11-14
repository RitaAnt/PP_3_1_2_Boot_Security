package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dao.UserDAO;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final RoleService roleService;  // Добавить RoleService
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userDAO = userDAO;
        this.roleService = roleService;  // Инициализировать RoleService
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<Role> getAllRoles() {
        // Вернуть все роли, например, из базы данных или другого источника
        return userDAO.getAllRoles();
    }
    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userDAO.getAllUsers();
    }

    @Override
    @Transactional
    public User getUserById(Long id) {
        return userDAO.getUserById(id);
    }

    @Override
    @Transactional
    public User getUserByUsername(String username) {
        return userDAO.getUserByUsername(username);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        // Перед сохранением пользователя в базу данных, кодируем его пароль
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userDAO.saveUser(user);
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        userDAO.deleteUser(id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {
        roleService.saveRole(role);
    }
}
